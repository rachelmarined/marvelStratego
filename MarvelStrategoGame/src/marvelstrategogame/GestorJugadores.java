package marvelstrategogame;

import java.util.ArrayList;

public class GestorJugadores {
    public static void validarPassword(String password) throws PasswordInvalidoException {
        if (password.length() != 5) {
            throw new PasswordInvalidoException("Error: El password debe tener EXACTAMENTE 5 caracteres.");
        }

        boolean tieneLetra = false;
        boolean tieneNumero = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                tieneLetra = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            } else {
                throw new PasswordInvalidoException("Error: El password solo puede tener letras y números (sin espacios ni símbolos).");
            }
        }
        if (!tieneLetra || !tieneNumero) {
            throw new PasswordInvalidoException("Error: El password debe ser alfanumérico: al menos una letra y al menos un número.");
        }
    }

    public static Jugador buscar(String username) {
        for (Jugador j : DatosGlobales.listaJugadores) {
            if (j.username.equalsIgnoreCase(username.trim())) {
                return j;
            }
        }
        return null;
    }

    public static Jugador registrar(String username, String password)
            throws UsuarioDuplicadoException, PasswordInvalidoException {
        validarPassword(password);
        if (buscar(username) != null) {
            throw new UsuarioDuplicadoException("Error: Ese username ya existe. Ingrese otro.");
        }
        Jugador nuevo = new Jugador(username.trim(), password);
        DatosGlobales.listaJugadores.add(nuevo);

        DatosGlobales.usuariosHistoricos++;
        return nuevo;
    }

    public static Jugador iniciarSesion(String username, String password) throws CredencialesInvalidasException {
        Jugador j = buscar(username);
        if (j == null || !j.password.equals(password)) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
        return j;
    }

    public static void cambiarPassword(Jugador jugador, String actual, String nuevo)
            throws CredencialesInvalidasException, PasswordInvalidoException {
        if (!jugador.password.equals(actual)) {
            throw new CredencialesInvalidasException("El password actual no es correcto.");
        }
        validarPassword(nuevo);
        jugador.password = nuevo;
    }

    public static void eliminarCuenta(Jugador jugador, String password) throws CredencialesInvalidasException {
        if (!jugador.password.equals(password)) {
            throw new CredencialesInvalidasException("El password no es correcto. La cuenta no se eliminó.");
        }
        DatosGlobales.listaJugadores.remove(jugador);
        if (DatosGlobales.jugadorActual == jugador) {
            DatosGlobales.jugadorActual = null;
        }
    }

    public static ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ordenados = new ArrayList<>();
        for (Jugador j : DatosGlobales.listaJugadores) {
            insertarOrdenado(ordenados, j, 0);
        }
        return ordenados;
    }

    private static void insertarOrdenado(ArrayList<Jugador> lista, Jugador jugador, int posicion) {
        if (posicion == lista.size()) {
            lista.add(jugador);
            return;
        }
        if (jugador.puntos > lista.get(posicion).puntos) {
            lista.add(posicion, jugador);
            return;
        }
        insertarOrdenado(lista, jugador, posicion + 1);
    }

    public static int contarVictorias(ArrayList<Equipo> ganadores, Equipo bando, int posicion) {
        if (posicion == ganadores.size()) {
            return 0;
        }
        int esta = (ganadores.get(posicion) == bando) ? 1 : 0;
        return esta + contarVictorias(ganadores, bando, posicion + 1);
    }
}
