package marvelstrategogame;

public class Tierra extends Pieza {
    public Tierra(Equipo equipo) {
        super("Tierra", 0, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        return false;
    }

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        return ResultadoCombate.VICTORIA_TIERRA;
    }

    @Override
    public String getEtiquetaRango() {
        return "Objetivo";
    }

    @Override
    public String getInsignia() {
        return "T";
    }
}
