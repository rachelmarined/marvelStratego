package marvelstrategogame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class VentanaInformacion extends JFrame {
    public VentanaInformacion(String titulo, String texto) {
        setTitle("Marvel Stratego - " + titulo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenido = new JPanel(new BorderLayout(0, 10));
        contenido.setBackground(Estilo.AZUL_OSCURO);
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setContentPane(contenido);

        contenido.add(Estilo.crearTitulo(titulo.toUpperCase(), 40), BorderLayout.NORTH);

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setBackground(new Color(20, 40, 100));
        area.setForeground(Color.WHITE);
        area.setMargin(new java.awt.Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(560, 330));
        scroll.setBorder(BorderFactory.createLineBorder(Estilo.ROJO, 3));
        contenido.add(scroll, BorderLayout.CENTER);

        JButton btnRegresar = Estilo.crearBoton("REGRESAR", Estilo.ROJO);
        btnRegresar.setPreferredSize(new Dimension(200, 50));
        btnRegresar.addActionListener(e -> this.dispose());
        JPanel abajo = new JPanel(new FlowLayout());
        abajo.setOpaque(false);
        abajo.add(btnRegresar);
        contenido.add(abajo, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MenuPrincipal().setVisible(true);
            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
