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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// CLASE NUEVA: guarda los colores y crea los componentes con el estilo Marvel.
// Así todas las ventanas se ven iguales y, para cambiar un color, basta hacerlo aquí.
public class Estilo {
    // Colores de Marvel: predominan el rojo y el azul.
    public static final Color ROJO = new Color(200, 20, 30);
    public static final Color ROJO_OSCURO = new Color(105, 8, 18);
    public static final Color AZUL = new Color(25, 85, 200);
    public static final Color AZUL_OSCURO = new Color(12, 28, 75);
    public static final Color GRIS = new Color(85, 85, 95);

    // Título grande con letra Impact y un contorno negro alrededor para que se lea sobre cualquier fondo.
    public static JLabel crearTitulo(String texto, int tamano, Color color) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                // El contorno se logra dibujando el texto en negro, un poco movido hacia cada lado.
                g2.setColor(Color.BLACK);
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dy = -2; dy <= 2; dy++) {
                        g2.drawString(getText(), x + dx, y + dy);
                    }
                }
                g2.setColor(getForeground());
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        titulo.setFont(new Font("Impact", Font.PLAIN, tamano));
        titulo.setForeground(color);
        return titulo;
    }

    // Texto en blanco y negrita, para usar sobre fondos oscuros.
    public static JLabel crearEtiqueta(String texto, int tamano) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, tamano));
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
    }

    // Caja de texto casi negra con borde blanco y letra grande.
    // Funciona también con JPasswordField, porque JPasswordField hereda de JTextField.
    public static void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.BOLD, 16));
        campo.setBackground(new Color(15, 15, 25));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }

    // Franja de un solo color; se usa como línea divisoria entre la imagen y el formulario.
    public static JPanel crearFranja(Color color) {
        JPanel franja = new JPanel();
        franja.setBackground(color);
        return franja;
    }
}
