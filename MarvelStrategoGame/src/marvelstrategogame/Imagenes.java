/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

// CLASE NUEVA: carga las imágenes de la carpeta "imagenes", que está dentro del paquete.
// Al estar en src, NetBeans las copia al compilar y también quedan dentro del .jar.
public class Imagenes {
    // Guarda cada imagen ya cargada para no volver a leer el archivo cada vez que se pinta.
    private static HashMap<String, Image> cache = new HashMap<>();

    // Recibe una ruta como "heroes/black_widow.png".
    // Si la imagen falta devuelve null, y la casilla muestra texto en vez de hacer caer el programa.
    public static Image cargar(String ruta) {
        if (cache.containsKey(ruta)) {
            return cache.get(ruta);
        }

        Image imagen = null;
        URL url = Imagenes.class.getResource("imagenes/" + ruta);
        if (url != null) {
            try {
                imagen = ImageIO.read(url);
            } catch (IOException ex) {
                imagen = null;
            }
        }
        cache.put(ruta, imagen);
        return imagen;
    }}
