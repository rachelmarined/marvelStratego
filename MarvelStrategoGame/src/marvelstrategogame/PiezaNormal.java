package marvelstrategogame;

public class PiezaNormal extends Pieza {
    public PiezaNormal(String nombre, int rango, Equipo equipo) {
        super(nombre, rango, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        int diffF = Math.abs(fDes - fOri);
        int diffC = Math.abs(cDes - cOri);
        return (diffF + diffC == 1);
    }

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        if (this.rango == 10 && atacante.derrotaAlRango10()) {
            return ResultadoCombate.GANA_ATACANTE;
        }
        if (atacante.getRango() > this.rango) {
            return ResultadoCombate.GANA_ATACANTE;
        } else if (atacante.getRango() < this.rango) {
            return ResultadoCombate.GANA_DEFENSOR;
        } else {
            return ResultadoCombate.EMPATE;
        }
    }
}
