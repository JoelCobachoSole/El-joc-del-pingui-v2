package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tablero implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Casilla> casillas;
    private ArrayList<Jugador> jugadores;
    private int turnos;
    private Jugador jugadorActual;

    // Constructor completo
    public Tablero(ArrayList<Casilla> casillas, ArrayList<Jugador> jugadores, int turnos, Jugador jugadorActual) {
        this.casillas = casillas;
        this.jugadores = jugadores;
        this.turnos = turnos;
        this.jugadorActual = jugadorActual;
    }

    // ✅ Constructor vacío (NECESARIO para partidas nuevas)
    public Tablero() {
        this.casillas = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.turnos = 0;
        this.jugadorActual = null;
    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    public void setCasillas(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(Jugador jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public void actualizarTablero() {
        // lógica de actualización del tablero visual o lógica
    }

    public void actualizarJugador(Jugador j) {
        // lógica para actualizar jugador si es necesario
    }

    public void generarTablero() {
        int totalCasillas = 50;
        Random rand = new Random();

        int numOso = 1;
        int numAgujero = rand.nextInt(3) + 4;
        int numTrineo = rand.nextInt(3) + 4;
        int numEvento = rand.nextInt(3) + 4;

        ArrayList<Casilla> nuevasCasillas = new ArrayList<>();

        // Primera casilla: inicio
        nuevasCasillas.add(new CasillaNormal(0, new ArrayList<>()));

        nuevasCasillas.add(new Oso(0, new ArrayList<>()));

        for (int i = 0; i < numAgujero; i++)
            nuevasCasillas.add(new Agujero(0, new ArrayList<>()));

        for (int i = 0; i < numTrineo; i++)
            nuevasCasillas.add(new Trineo(0, new ArrayList<>()));

        for (int i = 0; i < numEvento; i++)
            nuevasCasillas.add(new Evento(0, new ArrayList<>(), "random"));

        while (nuevasCasillas.size() < totalCasillas)
            nuevasCasillas.add(new CasillaNormal(0, new ArrayList<>()));

        // Mezclar (menos la primera)
        ArrayList<Casilla> mezcla = new ArrayList<>(nuevasCasillas.subList(1, nuevasCasillas.size()));
        Collections.shuffle(mezcla);

        nuevasCasillas = new ArrayList<>();
        nuevasCasillas.add(new CasillaNormal(0, new ArrayList<>()));
        nuevasCasillas.addAll(mezcla);

        // Establecer posiciones reales
        for (int i = 0; i < nuevasCasillas.size(); i++) {
            nuevasCasillas.get(i).setPosicion(i);
        }

        this.casillas = nuevasCasillas;
    }
}
