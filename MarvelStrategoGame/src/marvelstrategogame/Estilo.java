package marvelstrategogame;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Estilo {
    public static final Color ROJO = new Color(200, 20, 30);
    public static final Color ROJO_OSCURO = new Color(105, 8, 18);
    public static final Color AZUL = new Color(25, 85, 200);
    public static final Color AZUL_OSCURO = new Color(12, 28, 75);
    public static final Color GRIS = new Color(85, 85, 95);

    public static JLabel crearTitulo(String texto, int tamano) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(new Font("Impact", Font.PLAIN, tamano));
        titulo.setForeground(Color.WHITE);
        return titulo;
    }

    public static JLabel crearEtiqueta(String texto, int tamano) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, tamano));
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
    }

    public static JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Impact", Font.PLAIN, 20));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return boton;
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.BOLD, 16));
        campo.setBackground(new Color(15, 15, 25));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }

    public static JCheckBox crearMostrarPassword(JPasswordField campo) {
        char ocultar = campo.getEchoChar();
        JCheckBox mostrar = new JCheckBox("Mostrar password");
        mostrar.setOpaque(false);
        mostrar.setForeground(Color.WHITE);
        mostrar.setFont(new Font("Arial", Font.BOLD, 13));
        mostrar.setFocusPainted(false);
        mostrar.addActionListener(e -> {
            if (mostrar.isSelected()) {
                campo.setEchoChar((char) 0);
            } else {
                campo.setEchoChar(ocultar);
            }
        });
        return mostrar;
    }
}
