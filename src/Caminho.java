import java.util.Stack;

public class Caminho implements Comparable {
    private Stack<Castelo> castelos;
    private int atacantes;

    public Caminho(Stack<Castelo> castelos, int atacantes){
        this.castelos = castelos;
        this.atacantes = atacantes;
    }

    public Stack<Castelo> getCastelos() {
        return castelos;
    }

    public void setCastelos(Stack<Castelo> castelos) {
        this.castelos = castelos;
    }

    public int getAtacantes() {
        return atacantes;
    }

    public void setAtacantes(int atacantes) {
        this.atacantes = atacantes;
    }

    @Override
    public String toString() {
        return "-----------------------------------------\n" + castelos + "\nAtacantes restantes: " + atacantes + "\n-----------------------------------------\n";
    }

    @Override
    public int compareTo(Object o) {
        Caminho outro = (Caminho) o;
        return this.atacantes - outro.atacantes;
    }
}
