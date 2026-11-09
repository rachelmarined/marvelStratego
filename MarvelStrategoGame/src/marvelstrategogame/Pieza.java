package marvelstrategogame;

public abstract class Pieza implements Movible, Atacable {
    protected String nombre;
    protected int rango;
    protected Equipo equipo;
    protected boolean revelada;

    public Pieza(String nombre, int rango, Equipo equipo) {
        this.nombre = nombre;
        this.rango = rango;
        this.equipo = equipo;
        this.revelada = false;
    }

    public String getNombre() { return nombre; }
    public int getRango() { return rango; }
    public Equipo getEquipo() { return equipo; }
    public boolean isRevelada() { return revelada; }
    public void setRevelada(boolean revelada) { this.revelada = revelada; }

    public String getEtiquetaRango() { return "Rango " + rango; }

    public String getInsignia() { return String.valueOf(rango); }

    public String getRutaImagen() {
        String carpeta = (equipo == Equipo.HEROES) ? "heroes" : "villanos";

        String archivo = nombre.toLowerCase().replace(".", "").replace("-", "_").replace(" ", "_");
        return carpeta + "/" + archivo + ".png";
    }

    @Override
    public abstract boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes);

    @Override
    public abstract ResultadoCombate recibirAtaque(Pieza atacante);

    public boolean puedeDesactivarBombas() { return false; }

    public boolean derrotaAlRango10() { return false; }
}
