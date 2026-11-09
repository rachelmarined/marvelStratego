package marvelstrategogame;

public class PiezaAgente extends PiezaNormal {
    public PiezaAgente(String nombre, Equipo equipo) {
        super(nombre, 1, equipo);
    }

    @Override
    public boolean derrotaAlRango10() {
        return true;
    }
}
