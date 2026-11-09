package marvelstrategogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JButton;

public class CeldaTablero extends JButton {
    private Image imagen;
    private String rango;

    public CeldaTablero() {
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 20));
        setForeground(Color.WHITE);
    }

    public void setImagen(Image imagen, String rango) {
        this.imagen = imagen;
        this.rango = rango;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 22, 18);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(rango, 3, 14);
        }
    }
}
