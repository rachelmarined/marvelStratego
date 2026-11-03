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

        PanelFondo portada = new PanelFondo(Imagenes.cargar("spiderman_crear.jpg"), false);
        portada.setBounds(0, 0, 306, 470);
        add(portada);

       
        JPanel franja = Estilo.crearFranja(Estilo.AZUL);
        franja.setBounds(306, 0, 6, 470);
        add(franja);

        
        JLabel titulo = Estilo.crearTitulo("CREAR PLAYER", 54, Color.WHITE);
        titulo.setBounds(312, 30, 448, 80);
        add(titulo);

        JLabel subtitulo = Estilo.crearEtiqueta("Únete al universo Marvel", 15);
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        subtitulo.setBounds(312, 108, 448, 25);
        add(subtitulo);

        JLabel lbl1 = Estilo.crearEtiqueta("USUARIO", 14);
        lbl1.setBounds(366, 160, 340, 20);
        add(lbl1);

        txtUser = new JTextField();
        Estilo.estilizarCampo(txtUser);
        txtUser.setBounds(366, 183, 340, 42);
        add(txtUser);

        JLabel lbl2 = Estilo.crearEtiqueta("PASSWORD", 14);
        lbl2.setBounds(366, 238, 340, 20);
        add(lbl2);

        txtPass = new JPasswordField();
        Estilo.estilizarCampo(txtPass);
        txtPass.setBounds(366, 261, 340, 42);
        add(txtPass);

        
        JLabel lblAyuda = Estilo.crearEtiqueta("Debe tener exactamente 5 caracteres", 12);
        lblAyuda.setForeground(new Color(225, 225, 225));
        lblAyuda.setBounds(366, 308, 340, 18);
        add(lblAyuda);

        JButton btnGuardar = new BotonMarvel("Guardar", Estilo.AZUL);
        btnGuardar.setBounds(366, 350, 165, 66);
        add(btnGuardar);

        JButton btnCancelar = new BotonMarvel("Cancelar", Estilo.GRIS);
        btnCancelar.setBounds(541, 350, 165, 66);
        add(btnCancelar);

        
        getRootPane().setDefaultButton(btnGuardar);


        btnGuardar.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

         
            if (user.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Debes ingresar un username.");
                return;
            }


            if (pass.length() != 5) {
                JOptionPane.showMessageDialog(this, "Error: El password debe tener EXACTAMENTE 5 caracteres.");
                return;
            }

            for (Jugador j : DatosGlobales.listaJugadores) {
                if (j.username.equals(user)) {
                    JOptionPane.showMessageDialog(this, "Error: Ese username ya existe. Ingrese otro.");
                    return;
                }
            }


            Jugador nuevoJugador = new Jugador(user, pass);
            DatosGlobales.listaJugadores.add(nuevoJugador);

            JOptionPane.showMessageDialog(this, "¡Jugador creado con éxito!");
            this.dispose();
        });


        btnCancelar.addActionListener(e -> this.dispose());


        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
