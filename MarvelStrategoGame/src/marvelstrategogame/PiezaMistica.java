package marvelstrategogame;

public class PiezaMistica extends PiezaNormal {
    public PiezaMistica(String nombre, Equipo equipo) {
        super(nombre, 2, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        boolean mismaFila = (fOri == fDes) && (cOri != cDes);
        boolean mismaColumna = (cOri == cDes) && (fOri != fDes);
        return mismaFila || mismaColumna;
    }
}
