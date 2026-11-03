/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JPanel;

// CLASE NUEVA: panel que pinta una imagen que lo cubre completo. Se puede usar como
// fondo de una ventana (y agregarle botones encima) o como un recuadro con una imagen.
public class PanelFondo extends JPanel {
    private Image imagen;
    // true: pinta un degradado negro abajo para que los botones encima resalten.
    private boolean degradado;

    public PanelFondo(Image imagen, boolean degradado) {
        this.imagen = imagen;
        this.degradado = degradado;
        // Sin layout se puede ubicar cada componente con setBounds, como en el resto del proyecto.
        setLayout(null);
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // Máxima calidad al cambiar el tamaño, para que la imagen no se vea borrosa ni pixelada.
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Si la imagen no se encontró queda el fondo negro, y la ventana sigue funcionando.
        if (imagen != null) {
            // La imagen se agranda o achica hasta cubrir todo el panel sin deformarse; lo que sobra queda afuera.
            double escala = Math.max((double) getWidth() / imagen.getWidth(null),
                    (double) getHeight() / imagen.getHeight(null));
            int ancho = (int) Math.ceil(imagen.getWidth(null) * escala);
            int alto = (int) Math.ceil(imagen.getHeight(null) * escala);
            g2.drawImage(imagen, (getWidth() - ancho) / 2, (getHeight() - alto) / 2, ancho, alto, this);
        }

        if (degradado) {
            // Va de transparente a casi negro en el último 40% del panel.
            int inicio = getHeight() * 3 / 5;
            g2.setPaint(new GradientPaint(0, inicio, new Color(0, 0, 0, 0), 0, getHeight(), new Color(0, 0, 0, 220)));
            g2.fillRect(0, inicio, getWidth(), getHeight() - inicio);
        }
        g2.dispose();
    }
}
