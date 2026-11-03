/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TableroGameFrame extends JFrame {
    // Instancias lógicas del juego
    private Tablero tableroLogico = new Tablero();
    private ControladorJuego controlador = new ControladorJuego();

    
    private Jugador jugadorHeroes;
    private Jugador jugadorVillanos;
    private ModoJuego modo;

   
    private boolean ocultarTodo = false;

    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;

  
    private CeldaTablero[][] botonesTablero = new CeldaTablero[10][10];
    private JLabel lblTurno;

   
    private DefaultListModel<String> vencidasHeroes = new DefaultListModel<>();
    private DefaultListModel<String> vencidasVillanos = new DefaultListModel<>();

 
    public TableroGameFrame(Jugador jugadorHeroes, Jugador jugadorVillanos, ModoJuego modo) {
        this.jugadorHeroes = jugadorHeroes;
        this.jugadorVillanos = jugadorVillanos;
        this.modo = modo;

        setTitle("Marvel Stratego - Partida en Curso");
     
        setSize(980, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MenuPrincipal().setVisible(true);
            }
        });

       
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(40, 40, 40));
        lblTurno = new JLabel();
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 16));
        actualizarIndicadorTurno();

        JButton btnRetiro = new JButton("RETIRO");
        btnRetiro.setBackground(Color.RED);
        btnRetiro.setForeground(Color.WHITE);

        btnRetiro.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de rendirte? Esto otorgará 3 puntos al oponente.",
                "Confirmar Retiro", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                
                Equipo perdedor = controlador.getTurnoActual();
                Equipo ganador = bandoContrario(perdedor);
                terminarPartida(ganador, jugadorDe(ganador).username + " usando " + nombreBando(ganador)
                        + " ha ganado ya que " + jugadorDe(perdedor).username + " usando " + nombreBando(perdedor)
                        + " se ha retirado del juego");
            }
        });

        panelSuperior.add(lblTurno);
        panelSuperior.add(Box.createHorizontalStrut(50));
        panelSuperior.add(btnRetiro);
        add(panelSuperior, BorderLayout.NORTH);

    
        JPanel panelTablero = new JPanel(new GridLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image fondo = Imagenes.cargar("tablero.png");
                if (fondo != null) {
                    Insets borde = getInsets();
                    g.drawImage(fondo, borde.left, borde.top,
                            getWidth() - borde.left - borde.right,
                            getHeight() - borde.top - borde.bottom, this);
                }
            }
        };
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTablero.setBackground(new Color(60, 60, 60));

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
        add(panelTablero, BorderLayout.CENTER);

    
        add(crearPanelVencidas("Héroes vencidos", vencidasHeroes), BorderLayout.WEST);
        add(crearPanelVencidas("Villanos vencidos", vencidasVillanos), BorderLayout.EAST);

    
        tableroLogico.colocarPiezasIniciales();

        actualizarTableroVisual();
    }

    
    private JScrollPane crearPanelVencidas(String titulo, DefaultListModel<String> modelo) {
        JScrollPane panel = new JScrollPane(new JList<>(modelo));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.setPreferredSize(new Dimension(160, 0));
        return panel;
    }

    private void manejarClickCasilla(int fila, int col) {
      
        if (filaSeleccionada == -1 && colSeleccionada == -1) {
            seleccionarPieza(fila, col);
        }
       
        else {
            if (filaSeleccionada == fila && colSeleccionada == col) {
              
                limpiarSeleccion();
                actualizarTableroVisual();
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
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Movimiento Inválido", JOptionPane.ERROR_MESSAGE);
                limpiarSeleccion();
                actualizarTableroVisual();
            }
        }
    }

  
    private void seleccionarPieza(int fila, int col) {
        Pieza piezaEnCelda = tableroLogico.getPieza(fila, col);

        if (piezaEnCelda == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una casilla que tenga una pieza.");
            return;
        }

        
        if (piezaEnCelda.getEquipo() != controlador.getTurnoActual()) {
            JOptionPane.showMessageDialog(this, "No es el turno de este equipo. Turno actual: " + nombreBando(controlador.getTurnoActual()));
            return;
        }

        
        if (!tableroLogico.piezaPuedeActuar(fila, col)) {
            JOptionPane.showMessageDialog(this, "Esa pieza no se puede mover en este momento.");
            return;
        }

        filaSeleccionada = fila;
        colSeleccionada = col;
        actualizarTableroVisual();
    }

    
    private void ejecutarJugada(int fila, int col) throws MovimientoInvalidoException {
        Pieza atacante = tableroLogico.getPieza(filaSeleccionada, colSeleccionada);
        Pieza defensor = tableroLogico.getPieza(fila, col);
        String mensajeCombate = null;
        boolean tierraCapturada = false;

        if (defensor == null) {
           
            tableroLogico.moverPieza(filaSeleccionada, colSeleccionada, fila, col);
        } else {
           
            tableroLogico.validarAtaque(filaSeleccionada, colSeleccionada, fila, col);

            
            atacante.setRevelada(true);
            defensor.setRevelada(true);
            mensajeCombate = atacante.getNombre() + " (" + atacante.getEtiquetaRango() + ") atacó a "
                    + defensor.getNombre() + " (" + defensor.getEtiquetaRango() + ").\n";

            String resultado = controlador.resolverCombate(atacante, defensor);

            if (resultado.equals("GANA_ATACANTE") || resultado.equals("VICTORIA_TIERRA")) {
                tableroLogico.setPieza(fila, col, atacante);
                tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                registrarVencida(defensor);
                mensajeCombate += "¡Combate ganado por el atacante!";
                tierraCapturada = resultado.equals("VICTORIA_TIERRA");
            } else if (resultado.equals("GANA_DEFENSOR")) {
                tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                registrarVencida(atacante);
                mensajeCombate += "¡El defensor ha destruido al atacante!";
            } else {
                // Empate
                tableroLogico.setPieza(fila, col, null);
                tableroLogico.setPieza(filaSeleccionada, colSeleccionada, null);
                registrarVencida(atacante);
                registrarVencida(defensor);
                mensajeCombate += "¡Empate! Ambas piezas fueron eliminadas.";
            }
        }

        
        limpiarSeleccion();
        actualizarTableroVisual();
        if (mensajeCombate != null) {
            JOptionPane.showMessageDialog(this, mensajeCombate, "Combate", JOptionPane.INFORMATION_MESSAGE);
        }

        Equipo bandoActual = controlador.getTurnoActual();
        Equipo bandoRival = bandoContrario(bandoActual);

        if (tierraCapturada) {
            
            String accion = (bandoActual == Equipo.HEROES) ? "SALVADO" : "CAPTURADO";
            terminarPartida(bandoActual, jugadorDe(bandoActual).username + " usando " + nombreBando(bandoActual)
                    + " ha " + accion + " la TIERRA! Venciendo a " + jugadorDe(bandoRival).username);
        } else if (!tableroLogico.tieneMovimientos(bandoRival)) {
            
            terminarPartida(bandoActual, jugadorDe(bandoRival).username + " usando " + nombreBando(bandoRival)
                    + " ha perdido por no tener movimientos válidos disponibles ante " + jugadorDe(bandoActual).username);
        } else {
            pasarTurno();
        }
    }

    
    private void pasarTurno() {
       
        ToolTipManager.sharedInstance().setEnabled(false);
        ToolTipManager.sharedInstance().setEnabled(true);

        controlador.cambiarTurno();
        actualizarIndicadorTurno();

        if (modo == ModoJuego.CLASICO) {
            ocultarTodo = true;
            actualizarTableroVisual();
            JOptionPane.showMessageDialog(this, "Turno de " + jugadorDe(controlador.getTurnoActual()).username
                    + " (" + nombreBando(controlador.getTurnoActual()) + ").\nPresiona Aceptar cuando el otro jugador no esté viendo.");
            ocultarTodo = false;
        }
        actualizarTableroVisual();
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

        JOptionPane.showMessageDialog(this, log, "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

    
    private void registrarVencida(Pieza pieza) {
        DefaultListModel<String> modelo = (pieza.getEquipo() == Equipo.HEROES) ? vencidasHeroes : vencidasVillanos;
        modelo.addElement(pieza.getNombre() + " (" + pieza.getEtiquetaRango() + ")");
    }

   
    private void limpiarSeleccion() {
        filaSeleccionada = -1;
        colSeleccionada = -1;
    }

   
    private void actualizarIndicadorTurno() {
        Equipo turno = controlador.getTurnoActual();
        lblTurno.setText("TURNO ACTUAL: " + nombreBando(turno) + " (" + jugadorDe(turno).username + ")");
    }

    
    private Jugador jugadorDe(Equipo equipo) {
        return (equipo == Equipo.HEROES) ? jugadorHeroes : jugadorVillanos;
    }

    private Equipo bandoContrario(Equipo equipo) {
        return (equipo == Equipo.HEROES) ? Equipo.VILLANOS : Equipo.HEROES;
    }

    private String nombreBando(Equipo equipo) {
        return (equipo == Equipo.HEROES) ? "HÉROES" : "VILLANOS";
    }

    
    private boolean piezaVisible(Pieza pieza) {
        if (modo == ModoJuego.TUTORIAL || pieza.isRevelada()) {
            return true;
        }
        return !ocultarTodo && pieza.getEquipo() == controlador.getTurnoActual();
    }

    
    private void actualizarTableroVisual() {
        for (int fila = 0; fila < 10; fila++) {
            for (int col = 0; col < 10; col++) {
                if (tableroLogico.esZonaProhibida(fila, col)) {
                    continue; 
                }

                Pieza p = tableroLogico.getPieza(fila, col);
                CeldaTablero celda = botonesTablero[fila][col];

                
                if (p == null) {
                    celda.mostrarVacia();
                } else if (piezaVisible(p)) {
                    celda.mostrarPieza(p);
                } else {
                    celda.mostrarOculta(p.getEquipo());
                }

                
                if (fila == filaSeleccionada && col == colSeleccionada) {
                    celda.marcarSeleccionada();
                }

                
                celda.repaint();
            }
        }
    }
}
