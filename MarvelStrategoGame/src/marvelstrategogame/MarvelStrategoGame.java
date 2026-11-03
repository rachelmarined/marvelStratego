/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */


import java.awt.Dimension;
import javax.swing.*;

public class MarvelStrategoGame extends JFrame {

    public MarvelStrategoGame() {
        setTitle("Marvel Stratego - Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        PanelFondo fondo = new PanelFondo(Imagenes.cargar("caja_stratego.jpg"), true);
        fondo.setPreferredSize(new Dimension(640, 525));
        setContentPane(fondo);
        JButton btnLogin = new BotonMarvel("LOG IN", Estilo.ROJO);
        btnLogin.setBounds(50, 440, 255, 66);
        add(btnLogin);

        JButton btnCrear = new BotonMarvel("CREAR PLAYER", Estilo.AZUL);
        btnCrear.setBounds(335, 440, 255, 66);
        add(btnCrear);

        btnLogin.addActionListener(e -> {
            LoginFrame login = new LoginFrame(this);
            login.setVisible(true);
        });


        btnCrear.addActionListener(e -> {
            CrearPlayerFrame crear = new CrearPlayerFrame();
            crear.setVisible(true);
        });

      
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        new MarvelStrategoGame().setVisible(true);
    }
}
