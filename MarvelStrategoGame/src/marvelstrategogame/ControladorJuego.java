/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */


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

    
    public String resolverCombate(Pieza atacante, Pieza defensor) {
       
        if (defensor instanceof Tierra) {
            return "VICTORIA_TIERRA";
        }

        if (defensor instanceof Bomba) {
            if (atacante.getRango() == 3) {
                return "GANA_ATACANTE"; 
            } else {
                return "GANA_DEFENSOR"; 
            }
        }

     
        if (atacante.getRango() == 1 && defensor.getRango() == 10) {
            return "GANA_ATACANTE";
        }

      
        if (atacante.getRango() > defensor.getRango()) {
            return "GANA_ATACANTE";
        } else if (atacante.getRango() < defensor.getRango()) {
            return "GANA_DEFENSOR";
        } else {
            return "EMPATE"; 
        }
    }
}