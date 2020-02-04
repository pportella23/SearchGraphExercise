import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Castelo {
    private String idCastelo;
    private int numeroDeGuerreiros;
    private List<Castelo> vizinhos;
    private boolean conquistado;

    public Castelo(String idCastelo, int numeroDeGuerreiros) {
        this.vizinhos = new ArrayList<>();
        this.conquistado = false;
        this.idCastelo = idCastelo;
        this.numeroDeGuerreiros = numeroDeGuerreiros;
    }

    public String getIdCastelo() {
        return idCastelo;
    }

    public void setIdCastelo(String idCastelo) {
        this.idCastelo = idCastelo;
    }

    public int getNumeroDeGuerreiros() {
        return numeroDeGuerreiros;
    }

    public void setNumeroDeGuerreiros(int numeroDeGuerreiros) {
        this.numeroDeGuerreiros = numeroDeGuerreiros;
    }

    public List<Castelo> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(List<Castelo> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public void pushVizinhos(Castelo... vizinhos){
        this.vizinhos.addAll(Arrays.asList(vizinhos));
    }

    public void pushVizinhosAmbosLados(Castelo... vizinhos){
        Arrays.asList(vizinhos).stream().forEach( (vizinho) -> {
            this.vizinhos.add(vizinho);
            vizinho.pushVizinhos(this);
        });
    }

    public boolean possoAtacar(int atacantes){
        return (atacantes > (2 * this.getNumeroDeGuerreiros()));
    }

    public int guerreirosRestantes(int atacantes){
        return atacantes - (2 * this.getNumeroDeGuerreiros()) - 50;
    }

    @Override
    public String toString() {
        return "Castelo: " + this.idCastelo;
    }
}
