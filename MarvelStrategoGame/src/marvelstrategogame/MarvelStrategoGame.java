package marvelstrategogame;

import java.awt.Dimension;
import javax.swing.*;

public class MarvelStrategoGame extends JFrame {
    public MarvelStrategoGame() {
        setTitle("Marvel Stratego - Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PanelImagen fondo = new PanelImagen("caja_stratego.jpg");
        fondo.setPreferredSize(new Dimension(640, 525));
        setContentPane(fondo);

        JButton btnLogin = Estilo.crearBoton("LOG IN", Estilo.ROJO);
        btnLogin.setBounds(50, 445, 255, 60);
        add(btnLogin);

        JButton btnCrear = Estilo.crearBoton("CREAR PLAYER", Estilo.AZUL);
        btnCrear.setBounds(335, 445, 255, 60);
        add(btnCrear);

        btnLogin.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        btnCrear.addActionListener(e -> {
            new CrearPlayerFrame().setVisible(true);
            this.dispose();
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new MarvelStrategoGame().setVisible(true);
    }
}
