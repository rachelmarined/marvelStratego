/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

// CLASE NUEVA: TableroGameFrame ya usaba esta excepción, pero la clase no existía.
// Esa era una de las razones por las que el proyecto no compilaba.
// Se lanza (throw) cuando una jugada rompe las reglas y se atrapa (catch) en
// TableroGameFrame, que muestra el mensaje en vez de dejar que el programa se caiga.
public class MovimientoInvalidoException extends Exception {

    public MovimientoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
