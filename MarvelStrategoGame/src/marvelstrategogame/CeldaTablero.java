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
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;

// CLASE NUEVA: es un botón del tablero que además sabe dibujar la carta de un personaje.
// Hereda de JButton, así que sigue recibiendo clics como cualquier otro botón.
public class CeldaTablero extends JButton {
    private static final Color COLOR_HEROES = new Color(40, 90, 200);
    private static final Color COLOR_VILLANOS = new Color(190, 40, 40);

    // Datos de la carta visible; imagen queda en null si la casilla no muestra un personaje.
    private Image imagen;
    private String nombre;
    private String insignia;

    public CeldaTablero() {
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 10));
        // Quita el margen del botón para aprovechar toda la casilla.
        setMargin(new java.awt.Insets(1, 1, 1, 1));
        mostrarVacia();
    }

    // Casilla sin pieza: el botón se vuelve transparente para que se vea el tablero de fondo.
    public final void mostrarVacia() {
        imagen = null;
        setText("");
        setToolTipText(null);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
    }

    // Pieza enemiga oculta: solo se ve de qué bando es, por el color. No se guarda su
    // nombre aquí ni en el tooltip, para que el rival no pueda descubrirla.
    public void mostrarOculta(Equipo equipo) {
        imagen = null;
        setText("?");
        setToolTipText(null);
        setOpaque(true);
        setContentAreaFilled(true);
        setBackground(colorDe(equipo));
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 20));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }

    // Pieza visible: guarda los datos de la carta y paintComponent se encarga de dibujarla.
    public void mostrarPieza(Pieza pieza) {
        imagen = Imagenes.cargar(pieza.getRutaImagen());
        nombre = pieza.getNombre();
        insignia = pieza.getInsignia();
        setToolTipText(pieza.getNombre() + " - " + pieza.getEtiquetaRango());
        setBorder(BorderFactory.createLineBorder(colorDe(pieza.getEquipo()), 2));

        if (imagen != null) {
            setText("");
            setOpaque(false);
            setContentAreaFilled(false);
        } else {
            // Si la imagen no se encontró, el juego sigue funcionando y muestra el nombre.
            setText("<html><center>" + pieza.getNombre() + "<br>" + pieza.getEtiquetaRango() + "</center></html>");
            setOpaque(true);
            setContentAreaFilled(true);
            setBackground(colorDe(pieza.getEquipo()));
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 10));
        }
    }

    // Marca la casilla seleccionada con un borde amarillo grueso.
    public void marcarSeleccionada() {
        setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
    }

    private Color colorDe(Equipo equipo) {
        return (equipo == Equipo.HEROES) ? COLOR_HEROES : COLOR_VILLANOS;
    }

    // Swing llama a este método cada vez que necesita pintar el botón.
    // Se dibuja aquí para que la imagen siempre llene la casilla, aunque cambie de tamaño.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        // Suaviza la imagen y el texto al cambiarles el tamaño.
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        dibujarNombre(g2);
        dibujarInsignia(g2);
        g2.dispose();
    }

    // Dibuja el nombre en una franja oscura abajo. Si no cabe en una línea, usa dos.
    private void dibujarNombre(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 9));
        FontMetrics fm = g2.getFontMetrics();

        String[] lineas = {nombre};
        int espacio = nombre.lastIndexOf(' ');
        if (fm.stringWidth(nombre) > getWidth() - 2 && espacio > 0) {
            lineas = new String[]{nombre.substring(0, espacio), nombre.substring(espacio + 1)};
        }

        int altoFranja = lineas.length * fm.getHeight();
        int y = getHeight() - altoFranja;
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, y, getWidth(), altoFranja);

        g2.setColor(Color.WHITE);
        for (String linea : lineas) {
            // Centra cada línea dentro de la casilla.
            int x = (getWidth() - fm.stringWidth(linea)) / 2;
            g2.drawString(linea, x, y + fm.getAscent());
            y += fm.getHeight();
        }
    }

    // Dibuja el rango en la esquina superior izquierda para leerlo rápido durante la partida.
    private void dibujarInsignia(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        int ancho = fm.stringWidth(insignia) + 6;
        int alto = fm.getHeight();

        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, ancho, alto);
        g2.setColor(Color.YELLOW);
        g2.drawString(insignia, 3, fm.getAscent());
    }
}
