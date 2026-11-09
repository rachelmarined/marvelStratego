package marvelstrategogame;

public class Jugador {
    String username;
    String password;
    int puntos;
    int partidasHeroes;
    int partidasVillanos;

    java.util.ArrayList<String> logs = new java.util.ArrayList<>();

    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.partidasHeroes = 0;
        this.partidasVillanos = 0;
    }

    @Override
    public String toString() {
        return username;
    }
}
