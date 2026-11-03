/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

public abstract class Pieza {
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
        String archivo = nombre.toLowerCase().replaceAll("[^a-z0-9]+", "_").replaceAll("_$", "");
        return carpeta + "/" + archivo + ".png";
    }

   
    public abstract boolean esMovimientoValido(int fOri, int cOri, int fDes, int cDes);
}
