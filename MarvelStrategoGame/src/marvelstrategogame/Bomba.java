package marvelstrategogame;

public class Bomba extends Pieza {
    public Bomba(Equipo equipo) {
        super(equipo == Equipo.HEROES ? "Nova Blast" : "Pumpkin Bomb", 11, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        return false;
    }

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        if (atacante.puedeDesactivarBombas()) {
            return ResultadoCombate.GANA_ATACANTE;
        }
        return ResultadoCombate.GANA_DEFENSOR;
    }

    @Override
    public String getEtiquetaRango() {
        return "Bomba";
    }

    @Override
    public String getInsignia() {
        return "B";
    }
}
