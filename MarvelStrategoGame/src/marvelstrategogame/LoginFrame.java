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
import java.awt.Dimension;
import javax.swing.*;

public class LoginFrame extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;
    JFrame menuInicial;

    public LoginFrame(JFrame menuInicial) {
        this.menuInicial = menuInicial;
        setTitle("Log In");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

       
        JPanel contenido = new JPanel(null);
        contenido.setBackground(Estilo.AZUL_OSCURO);
        contenido.setPreferredSize(new Dimension(760, 470));
        setContentPane(contenido);

       
        PanelFondo portada = new PanelFondo(Imagenes.cargar("spiderman_login.jpg"), false);
        portada.setBounds(0, 0, 306, 470);
        add(portada);

        
        JPanel franja = Estilo.crearFranja(Estilo.ROJO);
        franja.setBounds(306, 0, 6, 470);
        add(franja);

        
        JLabel titulo = Estilo.crearTitulo("LOG IN", 60, Color.WHITE);
        titulo.setBounds(312, 30, 448, 80);
        add(titulo);

        JLabel subtitulo = Estilo.crearEtiqueta("Entra y elige tu bando", 15);
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        subtitulo.setBounds(312, 108, 448, 25);
        add(subtitulo);

        JLabel lbl1 = Estilo.crearEtiqueta("USUARIO", 14);
        lbl1.setBounds(366, 165, 340, 20);
        add(lbl1);

        txtUser = new JTextField();
        Estilo.estilizarCampo(txtUser);
        txtUser.setBounds(366, 188, 340, 42);
        add(txtUser);

        JLabel lbl2 = Estilo.crearEtiqueta("PASSWORD", 14);
        lbl2.setBounds(366, 246, 340, 20);
        add(lbl2);

        txtPass = new JPasswordField();
        Estilo.estilizarCampo(txtPass);
        txtPass.setBounds(366, 269, 340, 42);
        add(txtPass);

        JButton btnEntrar = new BotonMarvel("Entrar", Estilo.ROJO);
        btnEntrar.setBounds(366, 345, 340, 68);
        add(btnEntrar);

     
        getRootPane().setDefaultButton(btnEntrar);

        btnEntrar.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            boolean encontrado = false;
            for (Jugador j : DatosGlobales.listaJugadores) {
                if (j.username.equals(user) && j.password.equals(pass)) {
                    encontrado = true;

                    DatosGlobales.jugadorActual = j;
                    break;
                }
            }

            if (encontrado) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido!");

                new MenuPrincipal().setVisible(true);

                this.dispose();
                menuInicial.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

     
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
