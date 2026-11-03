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
import java.awt.Font;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Marvel Stratego - Menu Principal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JMenuBar barra = new JMenuBar();


        JMenu mStratego = new JMenu("STRATEGO – MARVEL HEROES");
        JMenuItem itemPartidaNueva = new JMenuItem("Partida Nueva");

        
        itemPartidaNueva.addActionListener(e -> iniciarPartidaNueva());

        mStratego.add(itemPartidaNueva);

        JMenu mPerfil = new JMenu("MI PERFIL");
        mPerfil.add(new JMenuItem("Log de Mis Últimos Juegos"));
        mPerfil.add(new JMenuItem("Cambiar mi Password"));
        mPerfil.add(new JMenuItem("Eliminar mi Cuenta"));

        JMenu mUniverso = new JMenu("UNIVERSO MARVEL");
        mUniverso.add(new JMenuItem("Ranking"));
        mUniverso.add(new JMenuItem("Batallas"));

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
        for (int i = 0; i < barra.getMenuCount(); i++) {
            barra.getMenu(i).setForeground(Color.WHITE);
            barra.getMenu(i).setFont(new Font("Arial", Font.BOLD, 13));
        }

        setJMenuBar(barra);


        JPanel contenido = new JPanel(null);
        contenido.setBackground(Estilo.AZUL_OSCURO);
        contenido.setPreferredSize(new Dimension(800, 480));
        setContentPane(contenido);

        
        PanelFondo portada = new PanelFondo(Imagenes.cargar("venom.jpg"), false);
        portada.setBounds(488, 0, 312, 480);
        add(portada);

        
        JPanel franja = Estilo.crearFranja(Estilo.ROJO);
        franja.setBounds(482, 0, 6, 480);
        add(franja);

        Jugador yo = DatosGlobales.jugadorActual;

        
        JLabel titulo = Estilo.crearTitulo("BIENVENIDO", 52, Color.WHITE);
        titulo.setBounds(0, 35, 482, 70);
        add(titulo);

        JLabel nombre = Estilo.crearTitulo(yo.username.toUpperCase(), 42, Estilo.ROJO);
        nombre.setBounds(0, 100, 482, 60);
        add(nombre);

        JLabel puntos = Estilo.crearEtiqueta("Puntos: " + yo.puntos, 18);
        puntos.setHorizontalAlignment(SwingConstants.CENTER);
        puntos.setBounds(0, 168, 482, 26);
        add(puntos);

        JLabel partidas = Estilo.crearEtiqueta("Partidas con Héroes: " + yo.partidasHeroes
                + "     |     Partidas con Villanos: " + yo.partidasVillanos, 14);
        partidas.setHorizontalAlignment(SwingConstants.CENTER);
        partidas.setBounds(0, 196, 482, 22);
        add(partidas);

        JButton btnPartida = new BotonMarvel("PARTIDA NUEVA", Estilo.ROJO);
        btnPartida.setFont(new Font("Impact", Font.PLAIN, 28));
        btnPartida.addActionListener(e -> iniciarPartidaNueva());
        btnPartida.setBounds(66, 250, 350, 76);
        add(btnPartida);

        JButton btnCerrar = new BotonMarvel("CERRAR SESIÓN", Estilo.AZUL);
        btnCerrar.addActionListener(e -> cerrarSesion());
        btnCerrar.setBounds(66, 340, 350, 60);
        add(btnCerrar);

        JLabel ayuda = Estilo.crearEtiqueta("Las demás opciones están en la barra de menú de arriba", 12);
        ayuda.setForeground(new Color(200, 210, 235));
        ayuda.setHorizontalAlignment(SwingConstants.CENTER);
        ayuda.setBounds(0, 425, 482, 20);
        add(ayuda);

        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    
    private void iniciarPartidaNueva() {
        if (DatosGlobales.listaJugadores.size() < 2) {
            JOptionPane.showMessageDialog(this, "Se necesitan al menos 2 jugadores registrados para iniciar una partida.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mostrarPartidaNueva();
    }

    
    private void cerrarSesion() {
        int op = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION) {
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
        JComboBox<Equipo> comboBando = new JComboBox<>(Equipo.values());
        JComboBox<ModoJuego> comboModo = new JComboBox<>(ModoJuego.values());

        JPanel panel = new JPanel(new java.awt.GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Oponente:"));
        panel.add(comboOponente);
        panel.add(new JLabel("Tu bando:"));
        panel.add(comboBando);
        panel.add(new JLabel("Modo:"));
        panel.add(comboModo);

        int op = JOptionPane.showConfirmDialog(this, panel, "Partida Nueva", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (op != JOptionPane.OK_OPTION) {
            return;
        }

        Jugador yo = DatosGlobales.jugadorActual;
        Jugador oponente = (Jugador) comboOponente.getSelectedItem();
        boolean soyHeroes = comboBando.getSelectedItem() == Equipo.HEROES;
        ModoJuego modo = (ModoJuego) comboModo.getSelectedItem();

        new TableroGameFrame(soyHeroes ? yo : oponente, soyHeroes ? oponente : yo, modo).setVisible(true);
        this.dispose();
    }
}
