/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */


public class PiezaNormal extends Pieza {
    
    public PiezaNormal(String nombre, int rango, Equipo equipo) {
        super(nombre, rango, equipo);
    }

    @Override
    public boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes) {
        int diffF = Math.abs(fDes - fOri);
        int diffC = Math.abs(cDes - cOri);

        
        if (this.rango == 2) {
            if ((diffF == 0 && diffC > 0) || (diffC == 0 && diffF > 0)) {
                return true; 
            }
        }

        
        return (diffF + diffC == 1);
    }
}