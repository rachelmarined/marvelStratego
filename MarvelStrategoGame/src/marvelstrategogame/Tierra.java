/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

public class Tierra extends Pieza {
    
    public Tierra(Equipo equipo) {
     
        super("Tierra", 0, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        return false;
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
