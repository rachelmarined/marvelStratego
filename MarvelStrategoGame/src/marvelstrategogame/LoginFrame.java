package marvelstrategogame;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class LoginFrame extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;

    boolean entro = false;

    public LoginFrame() {
        setTitle("Log In");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenido = new JPanel(null);
        contenido.setBackground(Estilo.AZUL_OSCURO);
        contenido.setPreferredSize(new Dimension(760, 470));
        setContentPane(contenido);

        PanelImagen portada = new PanelImagen("deadpool_login.jpg");
        portada.setBounds(0, 0, 312, 470);
        add(portada);

        JLabel titulo = Estilo.crearTitulo("LOG IN", 60);
        titulo.setBounds(312, 30, 448, 80);
        add(titulo);

        JLabel lbl1 = Estilo.crearEtiqueta("USUARIO", 14);
        lbl1.setBounds(366, 150, 340, 20);
        add(lbl1);

        txtUser = new JTextField();
        Estilo.estilizarCampo(txtUser);
        txtUser.setBounds(366, 173, 340, 42);
        add(txtUser);

        JLabel lbl2 = Estilo.crearEtiqueta("PASSWORD", 14);
        lbl2.setBounds(366, 231, 340, 20);
        add(lbl2);

        txtPass = new JPasswordField();
        Estilo.estilizarCampo(txtPass);
        txtPass.setBounds(366, 254, 340, 42);
        add(txtPass);

        JCheckBox mostrar = Estilo.crearMostrarPassword(txtPass);
        mostrar.setBounds(366, 302, 340, 24);
        add(mostrar);

        JButton btnEntrar = Estilo.crearBoton("ENTRAR", Estilo.ROJO);
        btnEntrar.setBounds(366, 350, 165, 60);
        add(btnEntrar);

        JButton btnRegresar = Estilo.crearBoton("REGRESAR", Estilo.GRIS);
        btnRegresar.setBounds(541, 350, 165, 60);
        btnRegresar.addActionListener(e -> this.dispose());
        add(btnRegresar);

        getRootPane().setDefaultButton(btnEntrar);

        btnEntrar.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            try {
                DatosGlobales.jugadorActual = GestorJugadores.iniciarSesion(user, pass);
                JOptionPane.showMessageDialog(this, "¡Bienvenido!");
                entro = true;
                new MenuPrincipal().setVisible(true);
                this.dispose();
            } catch (CredencialesInvalidasException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!entro) {
                    new MarvelStrategoGame().setVisible(true);
                }
            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
