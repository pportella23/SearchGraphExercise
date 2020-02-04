import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Mapa {
    private Castelo raiz;
    private List<Caminho> caminhos = new ArrayList<>();
    private int tamanhoMaiorAtual = 0;
    private Graph graph = new Graph();

    public Mapa(String casoDeTeste){

        this.carregarTeste(casoDeTeste);

        long ti = System.currentTimeMillis();

        buscaEmProfundidade(this.raiz, this.raiz.getNumeroDeGuerreiros(), null);

        long tf = System.currentTimeMillis();

        System.out.println("");
        System.out.println("----- Caso " + casoDeTeste + " -----");
        System.out.println("");

        System.out.println("Total de castelos conquistaveis: " + (tamanhoMaiorAtual - 1));
        System.out.println("Caminhos encontrados: " + caminhos.size());
        System.out.println("Tempo de execucao: " + (tf-ti) + " ms");
        if(caminhos.size() > 20) {
            System.out.println(caminhos.get(0));
        }
        else {
            caminhos.forEach( c -> System.out.println(c));
        }
        System.out.println("Gerando Graph...");
        this.geraGraph(casoDeTeste);
        System.out.println("Pronto!");
    }

    public void carregarTeste(String caso) {
        Path path2 = Paths.get("casos/caso"+ caso +".txt");
        try (Scanner sc = new Scanner(Files.newBufferedReader(
                path2, Charset.forName("utf8"))).useDelimiter("\\s+")) {

            int nrGuerreirosSiberio = Integer.parseInt(sc.next());
            int nrDeCastelos = Integer.parseInt(sc.next());
            int nrDeEstradas = Integer.parseInt(sc.next());
            Castelo[] castelos = new Castelo[nrDeCastelos + 1];
            castelos[0] = new Castelo("0", nrGuerreirosSiberio);

            for(int i = 1; i <= nrDeCastelos; i++){
                castelos[i] = new Castelo(sc.next(), Integer.parseInt(sc.next()));
            }

            for(int i = 1; i <= nrDeEstradas; i++) {
                int de = Integer.parseInt(sc.next());
                int para = Integer.parseInt(sc.next());
                castelos[de].pushVizinhosAmbosLados(castelos[para]);
                graph.createConnection(String.valueOf(de), String.valueOf(para));
            }

            this.raiz = castelos[0];

        } catch (IOException e) {
            System.err.println("Erro de arquivos!");
        } catch (NumberFormatException number){
            System.err.println("Erro de formatação de números!");
        }
    }

    public void buscaEmProfundidade(Castelo alvo, int atacantes, Stack<Castelo> pilha) {
        if(pilha == null){
            pilha = new Stack<>();
        }
        if(alvo == null){
            return;
        }

        Stack<Castelo> clone = new Stack<>();
        clone.addAll(pilha);
        clone.add(alvo);

        if(clone.size() > this.tamanhoMaiorAtual){
            this.caminhos.clear();
            this.tamanhoMaiorAtual = clone.size();
            this.caminhos.add(new Caminho(clone, atacantes));
        } else if(clone.size() == this.tamanhoMaiorAtual){
            this.caminhos.add(new Caminho(clone, atacantes));
        }

        for(Castelo vizinho: alvo.getVizinhos()){
            if(!clone.contains(vizinho) && vizinho.possoAtacar(atacantes) && vizinho.guerreirosRestantes(atacantes) > 51 ){
                this.buscaEmProfundidade(vizinho, vizinho.guerreirosRestantes(atacantes), clone);
            }
        }
    }

    public void geraGraph(String caso){
        Castelo aux = null;
        ArrayList<String> castelosConquistados = new ArrayList<>();
        for(Castelo castelo: caminhos.get(0).getCastelos()) {
            castelosConquistados.add(castelo.getIdCastelo());
            if(aux != null) {
                graph.createColoredConnection(aux.getIdCastelo(), castelo.getIdCastelo(), "red");
            }
            aux = castelo;
        }
        graph.colorirCastelos(castelosConquistados);
        graph.geraArquivo(caso);

    }
}
