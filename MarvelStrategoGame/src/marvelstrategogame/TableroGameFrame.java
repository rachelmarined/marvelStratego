package marvelstrategogame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TableroGameFrame extends JFrame {
    private Tablero tableroLogico = new Tablero();
    private ControladorJuego controlador = new ControladorJuego();

    private Jugador jugadorHeroes;
    private Jugador jugadorVillanos;

    private boolean ocultarTodo = false;

    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;

    private CeldaTablero[][] botonesTablero = new CeldaTablero[10][10];
    private JLabel lblTurno;

    private JLabel lblMensaje;
    private JButton btnVerPiezas;

    private DefaultListModel<String> vencidasHeroes = new DefaultListModel<>();
    private DefaultListModel<String> vencidasVillanos = new DefaultListModel<>();

    public TableroGameFrame(Jugador jugadorHeroes, Jugador jugadorVillanos) {
        this.jugadorHeroes = jugadorHeroes;
        this.jugadorVillanos = jugadorVillanos;

        setTitle("Marvel Stratego - Partida en Curso");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MenuPrincipal().setVisible(true);
            }
        });

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 8));
        panelSuperior.setBackground(Estilo.AZUL_OSCURO);
        lblTurno = Estilo.crearTitulo("", 24);
        actualizarIndicadorTurno();

        JButton btnRetiro = Estilo.crearBoton("RETIRO", Estilo.ROJO);
        btnRetiro.setPreferredSize(new Dimension(140, 42));
        btnRetiro.addActionListener(e -> retirarse());

        panelSuperior.add(lblTurno);
        panelSuperior.add(btnRetiro);
        add(panelSuperior, BorderLayout.NORTH);

        PanelImagen panelTablero = new PanelImagen("tablero.png");
        panelTablero.setLayout(new GridLayout(10, 10));
        panelTablero.setPreferredSize(new Dimension(620, 580));

        for (int fila = 0; fila < 10; fila++) {
            for (int col = 0; col < 10; col++) {
                CeldaTablero celda = new CeldaTablero();
                botonesTablero[fila][col] = celda;

                final int f = fila;
                final int c = col;
                celda.addActionListener(e -> manejarClickCasilla(f, c));

                if (tableroLogico.esZonaProhibida(fila, col)) {
                    celda.setEnabled(false);
                }
                panelTablero.add(celda);
            }
        }
        JPanel marco = new JPanel(new BorderLayout());
        marco.setBackground(Estilo.AZUL_OSCURO);
        marco.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        marco.add(panelTablero);
        add(marco, BorderLayout.CENTER);

        add(crearPanelVencidas("Héroes vencidos", vencidasHeroes, Estilo.AZUL), BorderLayout.WEST);
        add(crearPanelVencidas("Villanos vencidos", vencidasVillanos, Estilo.ROJO), BorderLayout.EAST);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 0));
        panelInferior.setBackground(Estilo.AZUL_OSCURO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        lblMensaje = Estilo.crearEtiqueta("", 15);
        lblMensaje.setPreferredSize(new Dimension(700, 50));
        btnVerPiezas = Estilo.crearBoton("VER MIS PIEZAS", Estilo.AZUL);
        btnVerPiezas.setPreferredSize(new Dimension(190, 42));
        btnVerPiezas.setVisible(false);
        btnVerPiezas.addActionListener(e -> verMisPiezas());
        panelInferior.add(lblMensaje, BorderLayout.CENTER);
        panelInferior.add(btnVerPiezas, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        tableroLogico.colocarPiezasIniciales();
        actualizarTableroVisual();
        mostrarMensaje("Empieza el equipo HÉROES de " + jugadorHeroes.username + ". Elige una de tus piezas", false);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelVencidas(String titulo, DefaultListModel<String> modelo, Color color) {
        JList<String> lista = new JList<>(modelo);
        lista.setFont(new Font("Arial", Font.BOLD, 12));
        lista.setBackground(color);
        lista.setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.getViewport().setBackground(color);

        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Estilo.AZUL_OSCURO);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 8, 8, 8));
        panel.add(Estilo.crearEtiqueta(titulo, 14), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(180, 0));
        return panel;
    }

    private void mostrarMensaje(String texto, boolean esError) {
        lblMensaje.setText("<html>" + texto + "</html>");
        if (esError) {
            lblMensaje.setForeground(new Color(255, 120, 120));
        } else {
            lblMensaje.setForeground(Color.WHITE);
        }
    }

    private void manejarClickCasilla(int fila, int col) {
        if (ocultarTodo) {
            mostrarMensaje("Primero presiona VER MIS PIEZAS", true);
            return;
        }

        if (filaSeleccionada == -1) {
            seleccionarPieza(fila, col);
            return;
        }

        if (filaSeleccionada == fila && colSeleccionada == col) {
            limpiarSeleccion();
            actualizarTableroVisual();
            mostrarMensaje("Elige una de tus piezas", false);
            return;
        }

        Pieza piezaEnCelda = tableroLogico.getPieza(fila, col);
        if (piezaEnCelda != null && piezaEnCelda.getEquipo() == controlador.getTurnoActual()) {
            seleccionarPieza(fila, col);
            return;
        }

        try {
            ejecutarJugada(fila, col);
        } catch (MovimientoInvalidoException ex) {
            mostrarMensaje(ex.getMessage(), true);
            limpiarSeleccion();
            actualizarTableroVisual();
        }
    }

    private void seleccionarPieza(int fila, int col) {
        Pieza piezaEnCelda = tableroLogico.getPieza(fila, col);

        if (piezaEnCelda == null) {
            mostrarMensaje("Esa casilla está vacía. Elige una de tus piezas", true);
            return;
        }
        if (piezaEnCelda.getEquipo() != controlador.getTurnoActual()) {
            mostrarMensaje("Esa pieza no es tuya. Ahora juega el equipo " + nombreEquipo(controlador.getTurnoActual()), true);
            return;
        }
        if (!tableroLogico.piezaPuedeActuar(fila, col)) {
            mostrarMensaje("Esa pieza no se puede mover ahorita", true);
            return;
        }

        filaSeleccionada = fila;
        colSeleccionada = col;
        actualizarTableroVisual();
        mostrarMensaje("Elegiste a " + piezaEnCelda.getNombre() + ". Ahora elige a dónde moverla o a quién atacar", false);
    }

    private void ejecutarJugada(int fila, int col) throws MovimientoInvalidoException {
        Pieza atacante = tableroLogico.getPieza(filaSeleccionada, colSeleccionada);
        Pieza defensor = tableroLogico.getPieza(fila, col);
        String resumen;
        boolean tierraCapturada = false;

        if (defensor == null) {
            tableroLogico.moverPieza(filaSeleccionada, colSeleccionada, fila, col);
            resumen = atacante.getNombre() + " se movió";
        } else {
            tableroLogico.validarAtaque(filaSeleccionada, colSeleccionada, fila, col);

            atacante.setRevelada(true);
            defensor.setRevelada(true);
            resumen = atacante.getNombre() + " atacó a " + defensor.getNombre();

            ResultadoCombate resultado = controlador.resolverCombate(atacante, defensor);
            switch (resultado) {
                case VICTORIA_TIERRA:
                    tierraCapturada = true;
                    tableroLogico.setPieza(fila, col, atacante);
                    tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                    registrarVencida(defensor);
                    resumen += " y capturó la Tierra";
                    break;
                case GANA_ATACANTE:
                    tableroLogico.setPieza(fila, col, atacante);
                    tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                    registrarVencida(defensor);
                    resumen += " y ganó " + atacante.getNombre();
                    break;
                case GANA_DEFENSOR:
                    tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                    registrarVencida(atacante);
                    resumen += " y ganó " + defensor.getNombre();
                    break;
                case EMPATE:
                    tableroLogico.setPieza(fila, col, null);
                    tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                    registrarVencida(atacante);
                    registrarVencida(defensor);
                    resumen += " y empataron así que las dos salen del tablero";
                    break;
            }
        }

        limpiarSeleccion();
        actualizarTableroVisual();

        Equipo equipoActual = controlador.getTurnoActual();
        Equipo equipoRival = equipoContrario(equipoActual);

        if (tierraCapturada) {
            String accion = (equipoActual == Equipo.HEROES) ? "SALVADO" : "CAPTURADO";
            terminarPartida(equipoActual, jugadorDe(equipoActual).username + " usando " + nombreEquipo(equipoActual)
                    + " ha " + accion + " la TIERRA! Venciendo a " + jugadorDe(equipoRival).username);
        } else if (!tableroLogico.tieneMovimientos(equipoRival)) {
            terminarPartida(equipoActual, jugadorDe(equipoRival).username + " usando " + nombreEquipo(equipoRival)
                    + " ha perdido por no tener movimientos válidos disponibles ante " + jugadorDe(equipoActual).username);
        } else {
            pasarTurno(resumen);
        }
    }

    private void pasarTurno(String resumen) {
        controlador.cambiarTurno();
        actualizarIndicadorTurno();
        ocultarTodo = true;
        actualizarTableroVisual();
        btnVerPiezas.setVisible(true);
        Equipo turno = controlador.getTurnoActual();
        mostrarMensaje(resumen + "<br>Ahora le toca al equipo " + nombreEquipo(turno) + " de " + jugadorDe(turno).username
                + ". Cuando el otro jugador no esté viendo presiona VER MIS PIEZAS", false);
    }

    private void verMisPiezas() {
        ocultarTodo = false;
        btnVerPiezas.setVisible(false);
        actualizarTableroVisual();
        mostrarMensaje("Elige una de tus piezas", false);
    }

    private void retirarse() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de rendirte? Esto otorgará 3 puntos al oponente.",
                "Confirmar Retiro", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Equipo perdedor = controlador.getTurnoActual();
            Equipo ganador = equipoContrario(perdedor);
            terminarPartida(ganador, jugadorDe(ganador).username + " usando " + nombreEquipo(ganador)
                    + " ha ganado ya que " + jugadorDe(perdedor).username + " usando " + nombreEquipo(perdedor)
                    + " se ha retirado del juego");
        }
    }

    private void terminarPartida(Equipo ganador, String textoLog) {
        controlador.setJuegoTerminado(true);

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String log = textoLog + " — " + fecha;

        jugadorDe(ganador).puntos += 3;
        jugadorHeroes.partidasHeroes++;
        jugadorVillanos.partidasVillanos++;

        jugadorHeroes.logs.add(0, log);
        jugadorVillanos.logs.add(0, log);
        DatosGlobales.ganadoresPartidas.add(ganador);

        JOptionPane.showMessageDialog(this, jugadorDe(ganador).username + " ha ganado a " + jugadorDe(equipoContrario(ganador)).username,
                "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

    private void registrarVencida(Pieza pieza) {
        if (pieza.getEquipo() == Equipo.HEROES) {
            vencidasHeroes.addElement(pieza.getNombre() + " - " + pieza.getEtiquetaRango());
        } else {
            vencidasVillanos.addElement(pieza.getNombre() + " - " + pieza.getEtiquetaRango());
        }
    }

    private void limpiarSeleccion() {
        filaSeleccionada = -1;
        colSeleccionada = -1;
    }

    private void actualizarIndicadorTurno() {
        Equipo turno = controlador.getTurnoActual();
        lblTurno.setText("Turno del equipo " + nombreEquipo(turno) + " de " + jugadorDe(turno).username);
    }

    private Jugador jugadorDe(Equipo equipo) {
        if (equipo == Equipo.HEROES) {
            return jugadorHeroes;
        }
        return jugadorVillanos;
    }

    private Equipo equipoContrario(Equipo equipo) {
        if (equipo == Equipo.HEROES) {
            return Equipo.VILLANOS;
        }
        return Equipo.HEROES;
    }

    private String nombreEquipo(Equipo equipo) {
        if (equipo == Equipo.HEROES) {
            return "HÉROES";
        }
        return "VILLANOS";
    }

    private boolean piezaVisible(Pieza pieza) {
        if (pieza.isRevelada()) {
            return true;
        }
        return !ocultarTodo && pieza.getEquipo() == controlador.getTurnoActual();
    }

    private void actualizarTableroVisual() {
        for (int fila = 0; fila < 10; fila++) {
            for (int col = 0; col < 10; col++) {
                CeldaTablero celda = botonesTablero[fila][col];
                Pieza p = tableroLogico.getPieza(fila, col);

                if (p == null) {
                    celda.setImagen(null, "");
                    celda.setText("");
                    celda.setOpaque(false);
                    celda.setContentAreaFilled(false);
                    celda.setBorder(BorderFactory.createEmptyBorder());
                } else {
                    Color colorEquipo = (p.getEquipo() == Equipo.HEROES) ? Estilo.AZUL : Estilo.ROJO;
                    if (piezaVisible(p)) {
                        celda.setImagen(Imagenes.cargar(p.getRutaImagen()), p.getInsignia());
                        celda.setText("");
                        celda.setOpaque(false);
                        celda.setContentAreaFilled(false);
                    } else {
                        celda.setImagen(null, "");
                        celda.setText("?");
                        celda.setOpaque(true);
                        celda.setContentAreaFilled(true);
                        celda.setBackground(colorEquipo);
                    }
                    celda.setBorder(BorderFactory.createLineBorder(colorEquipo, 2));
                }

                if (fila == filaSeleccionada && col == colSeleccionada) {
                    celda.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
                }
            }
        }
    }
}
