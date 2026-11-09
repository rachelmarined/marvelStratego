package marvelstrategogame;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class Imagenes {
    private static HashMap<String, Image> cargadas = new HashMap<>();

    public static Image cargar(String ruta) {
        if (cargadas.containsKey(ruta)) {
            return cargadas.get(ruta);
        }
        Image imagen = null;
        URL url = Imagenes.class.getResource("imagenes/" + ruta);
        if (url != null) {
            imagen = new ImageIcon(url).getImage();
        }
        cargadas.put(ruta, imagen);
        return imagen;
    }
}
