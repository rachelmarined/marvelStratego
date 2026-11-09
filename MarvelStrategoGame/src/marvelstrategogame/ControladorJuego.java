package marvelstrategogame;

public class ControladorJuego {
    private Equipo turnoActual;
    private boolean juegoTerminado;

    public ControladorJuego() {
        this.turnoActual = Equipo.HEROES;
        this.juegoTerminado = false;
    }

    public Equipo getTurnoActual() {
        return turnoActual;
    }

    public void cambiarTurno() {
        turnoActual = (turnoActual == Equipo.HEROES) ? Equipo.VILLANOS : Equipo.HEROES;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public ResultadoCombate resolverCombate(Pieza atacante, Pieza defensor) {
        Atacable objetivo = defensor;
        return objetivo.recibirAtaque(atacante);
    }
}
