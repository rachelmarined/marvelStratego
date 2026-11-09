package marvelstrategogame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Marvel Stratego - Menu Principal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JMenuBar barra = new JMenuBar();

        JMenu mStratego = new JMenu("STRATEGO – MARVEL HEROES");
        JMenuItem itemPartidaNueva = new JMenuItem("Partida Nueva");
        itemPartidaNueva.addActionListener(e -> mostrarPartidaNueva());

        itemPartidaNueva.setEnabled(DatosGlobales.listaJugadores.size() >= 2);
        mStratego.add(itemPartidaNueva);

        JMenu mPerfil = new JMenu("MI PERFIL");
        JMenuItem itemLog = new JMenuItem("Log de Mis Últimos Juegos");
        itemLog.addActionListener(e -> mostrarPerfil());
        mPerfil.add(itemLog);
        JMenuItem itemPassword = new JMenuItem("Cambiar mi Password");
        itemPassword.addActionListener(e -> cambiarPassword());
        mPerfil.add(itemPassword);
        JMenuItem itemEliminar = new JMenuItem("Eliminar mi Cuenta");
        itemEliminar.addActionListener(e -> eliminarCuenta());
        mPerfil.add(itemEliminar);

        JMenu mUniverso = new JMenu("UNIVERSO MARVEL");
        JMenuItem itemRanking = new JMenuItem("Ranking");
        itemRanking.addActionListener(e -> mostrarRanking());
        mUniverso.add(itemRanking);
        JMenuItem itemBatallas = new JMenuItem("Batallas");
        itemBatallas.addActionListener(e -> mostrarBatallas());
        mUniverso.add(itemBatallas);

        JMenu mCerrar = new JMenu("CERRAR SESIÓN");
        JMenuItem salirItem = new JMenuItem("Cerrar Sesión");
        salirItem.addActionListener(e -> cerrarSesion());
        mCerrar.add(salirItem);

        barra.add(mStratego);
        barra.add(mPerfil);
        barra.add(mUniverso);
        barra.add(mCerrar);

        barra.setBackground(Estilo.AZUL_OSCURO);
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Estilo.ROJO));
        mStratego.setForeground(Color.WHITE);
        mPerfil.setForeground(Color.WHITE);
        mUniverso.setForeground(Color.WHITE);
        mCerrar.setForeground(Color.WHITE);

        setJMenuBar(barra);

        JPanel contenido = new JPanel(null);
        contenido.setBackground(Estilo.AZUL_OSCURO);
        contenido.setPreferredSize(new Dimension(800, 480));
        setContentPane(contenido);

        PanelImagen portada = new PanelImagen("venom.jpg");
        portada.setBounds(482, 0, 318, 480);
        add(portada);

        JLabel titulo = Estilo.crearTitulo("BIENVENIDO", 60);
        titulo.setBounds(0, 150, 482, 80);
        add(titulo);

        JLabel nombre = Estilo.crearTitulo(DatosGlobales.jugadorActual.username.toUpperCase(), 48);
        nombre.setForeground(Estilo.ROJO);
        nombre.setBounds(0, 228, 482, 70);
        add(nombre);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void mostrarPerfil() {
        Jugador yo = DatosGlobales.jugadorActual;
        String texto = "DATOS DEL JUGADOR\n"
                + "Username: " + yo.username + "\n"
                + "Puntos: " + yo.puntos + "\n"
                + "Partidas jugadas con Héroes: " + yo.partidasHeroes + "\n"
                + "Partidas jugadas con Villanos: " + yo.partidasVillanos + "\n\n"
                + "MIS ÚLTIMOS JUEGOS\n";
        if (yo.logs.isEmpty()) {
            texto += "Todavía no has jugado ninguna partida.";
        }
        for (int i = 0; i < yo.logs.size(); i++) {
            texto += (i + 1) + ". " + yo.logs.get(i) + "\n\n";
        }
        new VentanaInformacion("Mi Perfil", texto).setVisible(true);
        this.dispose();
    }

    private void cambiarPassword() {
        JPasswordField actual = new JPasswordField();
        JPasswordField nuevo = new JPasswordField();
        JCheckBox mostrar = new JCheckBox("Mostrar password");
        char ocultar = actual.getEchoChar();
        mostrar.addActionListener(e -> {
            char c = mostrar.isSelected() ? (char) 0 : ocultar;
            actual.setEchoChar(c);
            nuevo.setEchoChar(c);
        });

        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.add(new JLabel("Password actual:"));
        panel.add(actual);
        panel.add(new JLabel("Password nuevo:"));
        panel.add(nuevo);
        panel.add(mostrar);

        int op = JOptionPane.showConfirmDialog(this, panel, "Cambiar mi Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (op != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            GestorJugadores.cambiarPassword(DatosGlobales.jugadorActual,
                    new String(actual.getPassword()), new String(nuevo.getPassword()));
            JOptionPane.showMessageDialog(this, "¡Password cambiado con éxito!");
        } catch (CredencialesInvalidasException | PasswordInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCuenta() {
        JPasswordField password = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.add(new JLabel("Para eliminar tu cuenta escribe tu password:"));
        panel.add(password);
        JCheckBox mostrar = Estilo.crearMostrarPassword(password);
        mostrar.setForeground(Color.BLACK);
        panel.add(mostrar);

        int op = JOptionPane.showConfirmDialog(this, panel, "Eliminar mi Cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (op != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            GestorJugadores.eliminarCuenta(DatosGlobales.jugadorActual, new String(password.getPassword()));
            JOptionPane.showMessageDialog(this, "Tu cuenta fue eliminada.");
            this.dispose();
            new MarvelStrategoGame().setVisible(true);
        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarRanking() {
        ArrayList<Jugador> ranking = GestorJugadores.ranking();
        String texto = "";
        for (int i = 0; i < ranking.size(); i++) {
            texto += (i + 1) + ". " + ranking.get(i).username + " " + ranking.get(i).puntos + " pts\n";
        }
        new VentanaInformacion("Ranking", texto).setVisible(true);
        this.dispose();
    }

    private void mostrarBatallas() {
        int heroes = GestorJugadores.contarVictorias(DatosGlobales.ganadoresPartidas, Equipo.HEROES, 0);
        int villanos = GestorJugadores.contarVictorias(DatosGlobales.ganadoresPartidas, Equipo.VILLANOS, 0);
        String texto = "Usuarios activos: " + DatosGlobales.listaJugadores.size() + "\n"
                + "Usuarios históricos: " + DatosGlobales.usuariosHistoricos + "\n"
                + "Partidas jugadas: " + DatosGlobales.ganadoresPartidas.size() + "\n"
                + "     Veces que ganaron los HÉROES: " + heroes + "\n"
                + "     Veces que ganaron los VILLANOS: " + villanos;
        new VentanaInformacion("Batallas", texto).setVisible(true);
        this.dispose();
    }

    private void cerrarSesion() {
        int op = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            this.dispose();
            new MarvelStrategoGame().setVisible(true);
        }
    }

    private void mostrarPartidaNueva() {
        JComboBox<Jugador> comboOponente = new JComboBox<>();
        for (Jugador j : DatosGlobales.listaJugadores) {
            if (j != DatosGlobales.jugadorActual) {
                comboOponente.addItem(j);
            }
        }
        JComboBox<Equipo> comboEquipo = new JComboBox<>(Equipo.values());

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Oponente:"));
        panel.add(comboOponente);
        panel.add(new JLabel("Tu equipo:"));
        panel.add(comboEquipo);

        int op = JOptionPane.showConfirmDialog(this, panel, "Partida Nueva", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (op != JOptionPane.OK_OPTION) {
            return;
        }

        Jugador yo = DatosGlobales.jugadorActual;
        Jugador oponente = (Jugador) comboOponente.getSelectedItem();
        if (comboEquipo.getSelectedItem() == Equipo.HEROES) {
            new TableroGameFrame(yo, oponente).setVisible(true);
        } else {
            new TableroGameFrame(oponente, yo).setVisible(true);
        }
        this.dispose();
    }
}
