package marvelstrategogame;

public class PiezaExplorador extends PiezaNormal {
    public PiezaExplorador(String nombre, Equipo equipo) {
        super(nombre, 3, equipo);
    }

    @Override
    public boolean puedeDesactivarBombas() {
        return true;
    }
}
