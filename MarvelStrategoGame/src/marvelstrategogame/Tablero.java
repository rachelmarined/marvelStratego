/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marvelstrategogame;

/**
 *
 * @author XPS 13
 */

import java.util.ArrayList;
import java.util.Random;

public class Tablero {

    private static final String[][] HEROES = {
        {"Black Widow"},
        {"Gambit", "Spider-Girl", "Ice Man", "Storm", "Phoenix", "Dr. Strange", "Elektra", "Nightcrawler"},
        {"Emma Frost", "She-Hulk", "Giant Man", "Beast", "Colossus"},
        {"Ghost Rider", "Punisher", "Blade", "Thing"},
        {"Thor", "Human Torch", "Cyclops", "Invisible Woman"},
        {"Daredevil", "Silver Surfer", "Hulk", "Iron Man"},
        {"Spider-Man", "Wolverine", "Namor"},
        {"Professor X", "Nick Fury"},
        {"Captain America"},
        {"Mr. Fantastic"}
    };

    private static final String[][] VILLANOS = {
        {"Black Widow"},
        {"Mr. Sinister", "Sentinel 1", "Ultron", "Sandman", "Leader", "Viper", "Sentinel 2", "Electro"},
        {"Juggernaut", "Rhino", "Carnage", "Mole Man", "Lizard"},
        {"Abomination", "Thanos", "Black Cat", "Sabretooth"},
        {"Mystique", "Mysterio", "Dr. Octopus", "Deadpool"},
        {"Bullseye", "Omega Red", "Onslaught", "Red Skull"},
        {"Apocalypse", "Green Goblin", "Venom"},
        {"Kingpin", "Magneto"},
        {"Galactus"},
        {"Dr. Doom"}
    };

    private Pieza[][] matriz;
    
    private Random azar = new Random();

    public Tablero() {
        matriz = new Pieza[10][10];
        inicializarTablero();
    }

    private void inicializarTablero() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matriz[i][j] = null;
            }
        }
    }

  
    public void colocarPiezasIniciales() {
        colocarEquipo(Equipo.VILLANOS, VILLANOS, 0, 1, 2, 3);
        colocarEquipo(Equipo.HEROES, HEROES, 9, 8, 7, 6);
    }

 
    private void colocarEquipo(Equipo equipo, String[][] nombres, int filaFondo, int filaBombas, int filaMedia, int filaFrente) {
        
        int colTierra = 1 + azar.nextInt(8);
        matriz[filaFondo][colTierra] = new Tierra(equipo);

       
        matriz[filaFondo][colTierra - 1] = new Bomba(equipo);
        matriz[filaFondo][colTierra + 1] = new Bomba(equipo);
        matriz[filaBombas][colTierra] = new Bomba(equipo);


        ArrayList<Pieza> bombas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            bombas.add(new Bomba(equipo));
        }
        colocarAleatorio(bombas, celdasVacias(filaFondo, filaBombas));

        
        ArrayList<Pieza> rango2 = new ArrayList<>();
        ArrayList<Pieza> resto = new ArrayList<>();
        for (int i = 0; i < nombres.length; i++) {
            int rango = i + 1;
            for (String nombre : nombres[i]) {
                Pieza pieza = new PiezaNormal(nombre, rango, equipo);
                if (rango == 2) {
                    rango2.add(pieza);
                } else {
                    resto.add(pieza);
                }
            }
        }

       
        colocarAleatorio(rango2, celdasVacias(filaMedia, filaFrente));
       
        colocarAleatorio(resto, celdasVacias(filaFrente));
      
        colocarAleatorio(resto, celdasVacias(filaFondo, filaBombas, filaMedia, filaFrente));
    }

   
    private ArrayList<int[]> celdasVacias(int... filas) {
        ArrayList<int[]> celdas = new ArrayList<>();
        for (int fila : filas) {
            for (int col = 0; col < 10; col++) {
                if (matriz[fila][col] == null) {
                    celdas.add(new int[]{fila, col});
                }
            }
        }
        return celdas;
    }

    private void colocarAleatorio(ArrayList<Pieza> piezas, ArrayList<int[]> celdas) {
        if (piezas.isEmpty() || celdas.isEmpty()) {
            return;
        }
        Pieza pieza = piezas.remove(azar.nextInt(piezas.size()));
        int[] celda = celdas.remove(azar.nextInt(celdas.size()));
        matriz[celda[0]][celda[1]] = pieza;
        colocarAleatorio(piezas, celdas);
    }

    public Pieza getPieza(int fila, int col) {
        return matriz[fila][col];
    }

    public void setPieza(int fila, int col, Pieza pieza) {
        matriz[fila][col] = pieza;
    }


    public boolean esZonaProhibida(int fila, int col) {
        return (fila == 4 || fila == 5) && (col == 2 || col == 3 || col == 6 || col == 7);
    }

    
    public void moverPieza(int fOri, int cOri, int fDes, int cDes) throws MovimientoInvalidoException {
        Pieza pieza = matriz[fOri][cOri];

        if (esZonaProhibida(fDes, cDes)) {
            throw new MovimientoInvalidoException("No puedes moverte a una zona prohibida.");
        }
        if (matriz[fDes][cDes] != null) {
            throw new MovimientoInvalidoException("Esa casilla ya está ocupada.");
        }
        
        if (!pieza.esMovimientoValido(fOri, cOri, fDes, cDes)) {
            throw new MovimientoInvalidoException("Esta pieza no se puede mover a esa casilla.");
        }
        if (!caminoDespejado(fOri, cOri, fDes, cDes)) {
            throw new MovimientoInvalidoException("El camino está bloqueado.");
        }

        matriz[fDes][cDes] = pieza;
        matriz[fOri][cOri] = null;

        if (Math.abs(fDes - fOri) + Math.abs(cDes - cOri) > 1) {
            pieza.setRevelada(true);
        }
    }


    public void validarAtaque(int fOri, int cOri, int fDes, int cDes) throws MovimientoInvalidoException {
        if (Math.abs(fDes - fOri) + Math.abs(cDes - cOri) != 1) {
            throw new MovimientoInvalidoException("Solo puedes atacar a una pieza que esté al lado (sin diagonales).");
        }
    }

    private boolean caminoDespejado(int fila, int col, int fDes, int cDes) {

        int sigFila = fila + Integer.signum(fDes - fila);
        int sigCol = col + Integer.signum(cDes - col);

        if (sigFila == fDes && sigCol == cDes) {
            return true;
        }
        if (matriz[sigFila][sigCol] != null || esZonaProhibida(sigFila, sigCol)) {
            return false;
        }
        return caminoDespejado(sigFila, sigCol, fDes, cDes);
    }

   
    public boolean piezaPuedeActuar(int fila, int col) {
        Pieza pieza = matriz[fila][col];
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] d : direcciones) {
            int f = fila + d[0];
            int c = col + d[1];
            boolean dentroDelTablero = f >= 0 && f < 10 && c >= 0 && c < 10;

            if (dentroDelTablero && !esZonaProhibida(f, c) && pieza.esMovimientoValido(fila, col, f, c)) {
                Pieza vecina = matriz[f][c];
                if (vecina == null || vecina.getEquipo() != pieza.getEquipo()) {
                    return true;
                }
            }
        }
        return false;
    }

   
    public boolean tieneMovimientos(Equipo equipo) {
        return tieneMovimientos(equipo, 0, 0);
    }

   
    private boolean tieneMovimientos(Equipo equipo, int fila, int col) {
        if (fila == 10) {
            return false;
        }
        if (col == 10) {
            return tieneMovimientos(equipo, fila + 1, 0);
        }
        Pieza pieza = matriz[fila][col];
        if (pieza != null && pieza.getEquipo() == equipo && piezaPuedeActuar(fila, col)) {
            return true;
        }
        return tieneMovimientos(equipo, fila, col + 1);
    }
}
