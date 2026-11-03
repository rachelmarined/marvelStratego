/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

// CLASE NUEVA: botón llamativo con esquinas redondeadas, degradado y brillo.
// Hereda de JButton, así que se usa igual que cualquier botón (addActionListener, etc.).
public class BotonMarvel extends JButton {
    private Color color;

    public BotonMarvel(String texto, Color color) {
        super(texto);
        this.color = color;
        setFont(new Font("Impact", Font.PLAIN, 22));
        setForeground(Color.WHITE);
        // Se apaga el dibujo normal del botón porque paintComponent lo dibuja completo.
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        // Activa el efecto al pasar el mouse por encima y muestra la manita.
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Swing llama a este método cada vez que necesita pintar el botón.
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();
        int curva = alto / 2;

        // El color cambia según lo que hace el mouse: presionado es más oscuro y encima, más claro.
        Color base = color;
        if (!isEnabled()) {
            base = Color.GRAY;
        } else if (getModel().isPressed()) {
            base = color.darker();
        } else if (getModel().isRollover()) {
            base = color.brighter();
        }

        // Sombra debajo del botón para que parezca que "flota".
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(3, 5, ancho - 6, alto - 6, curva, curva);

        // Degradado: claro arriba y oscuro abajo.
        g2.setPaint(new GradientPaint(0, 0, base.brighter(), 0, alto, base.darker()));
        g2.fillRoundRect(0, 0, ancho - 4, alto - 6, curva, curva);

        // Brillo blanco en la mitad de arriba.
        g2.setColor(new Color(255, 255, 255, 60));
        g2.fillRoundRect(4, 3, ancho - 12, (alto - 6) / 2, curva, curva);

        // Borde blanco; cuando el mouse está encima se vuelve más grueso.
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(getModel().isRollover() ? 3f : 2f));
        g2.drawRoundRect(1, 1, ancho - 6, alto - 8, curva, curva);

        // Texto centrado en mayúsculas, con una sombra oscura detrás para que resalte.
        String texto = getText().toUpperCase();
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (ancho - 4 - fm.stringWidth(texto)) / 2;
        int y = (alto - 6 - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(new Color(0, 0, 0, 170));
        g2.drawString(texto, x + 2, y + 2);
        g2.setColor(getForeground());
        g2.drawString(texto, x, y);

        g2.dispose();
    }
}
