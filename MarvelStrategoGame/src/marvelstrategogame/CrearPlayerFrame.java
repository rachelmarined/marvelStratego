package marvelstrategogame;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class CrearPlayerFrame extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;

    public CrearPlayerFrame() {
        setTitle("Crear Player");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenido = new JPanel(null);
        contenido.setBackground(Estilo.ROJO_OSCURO);
        contenido.setPreferredSize(new Dimension(760, 470));
        setContentPane(contenido);

        PanelImagen portada = new PanelImagen("deadpool_crear.jpg");
        portada.setBounds(0, 0, 312, 470);
        add(portada);

        JLabel titulo = Estilo.crearTitulo("CREAR PLAYER", 54);
        titulo.setBounds(312, 30, 448, 80);
        add(titulo);

        JLabel lbl1 = Estilo.crearEtiqueta("USUARIO", 14);
        lbl1.setBounds(366, 140, 340, 20);
        add(lbl1);

        txtUser = new JTextField();
        Estilo.estilizarCampo(txtUser);
        txtUser.setBounds(366, 163, 340, 42);
        add(txtUser);

        JLabel lbl2 = Estilo.crearEtiqueta("PASSWORD", 14);
        lbl2.setBounds(366, 218, 340, 20);
        add(lbl2);

        txtPass = new JPasswordField();
        Estilo.estilizarCampo(txtPass);
        txtPass.setBounds(366, 241, 340, 42);
        add(txtPass);

        JCheckBox mostrar = Estilo.crearMostrarPassword(txtPass);
        mostrar.setBounds(366, 288, 340, 24);
        add(mostrar);

        JButton btnGuardar = Estilo.crearBoton("GUARDAR", Estilo.AZUL);
        btnGuardar.setBounds(366, 355, 165, 60);
        add(btnGuardar);

        JButton btnCancelar = Estilo.crearBoton("CANCELAR", Estilo.GRIS);
        btnCancelar.setBounds(541, 355, 165, 60);
        add(btnCancelar);

        getRootPane().setDefaultButton(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());

            if (user.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Debes ingresar un username.");
                return;
            }

            try {
                GestorJugadores.registrar(user, pass);
                JOptionPane.showMessageDialog(this, "¡Jugador creado con éxito!");
                this.dispose();
            } catch (PasswordInvalidoException | UsuarioDuplicadoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> this.dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MarvelStrategoGame().setVisible(true);
            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
