/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */


public class Bomba extends Pieza {
    
    public Bomba(Equipo equipo) {
        
        super(equipo == Equipo.HEROES ? "Nova Blast" : "Pumpkin Bomb", 11, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        return false;
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
