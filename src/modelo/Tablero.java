package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tablero {
	private ArrayList<Casilla> casillas;
	private ArrayList<Jugador> jugadores;
	private int turnos;
	private Jugador jugadorActual;
	
	public Tablero(ArrayList<Casilla> casillas, ArrayList<Jugador> jugadores, int turnos, Jugador jugadorActual) {
		super();
		this.casillas = casillas;
		this.jugadores = jugadores;
		this.turnos = turnos;
		this.jugadorActual = jugadorActual;
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
		
	}
	
	public void actualizarJugador(Jugador j) {
	
	}
	
	public void generarTablero() {
        int totalCasillas = 50;
        Random rand = new Random();

        int numOso = 1;
        int numAgujero = rand.nextInt(3) + 4; // 4-6
        int numTrineo = rand.nextInt(3) + 4;  // 4-6
        int numEvento = rand.nextInt(3) + 4;  // 4-6

        ArrayList<Casilla> nuevasCasillas = new ArrayList<>();

        // Primera casilla siempre normal (START)
        nuevasCasillas.add(new CasillaNormal(0, new ArrayList<>()));

        // Añadir Oso (no en la primera casilla)
        nuevasCasillas.add(new Oso(0, new ArrayList<>()));
        // Añadir Agujeros
        for (int i = 0; i < numAgujero; i++) {
            nuevasCasillas.add(new Agujero(0, new ArrayList<>()));
        }
        // Añadir Trineos
        for (int i = 0; i < numTrineo; i++) {
            nuevasCasillas.add(new Trineo(0, new ArrayList<>()));
        }
        // Añadir Eventos
        for (int i = 0; i < numEvento; i++) {
            nuevasCasillas.add(new Evento(0, new ArrayList<>(), "random"));
        }
        // Rellenar con casillas normales
        while (nuevasCasillas.size() < totalCasillas) {
            nuevasCasillas.add(new CasillaNormal(nuevasCasillas.size(), new ArrayList<>()));
        }

        // Mezclar todas menos la primera (que es START)
        ArrayList<Casilla> casillasAMezclar = new ArrayList<>(nuevasCasillas.subList(1, nuevasCasillas.size()));
        Collections.shuffle(casillasAMezclar);
        nuevasCasillas = new ArrayList<>();
        nuevasCasillas.add(new CasillaNormal(0, new ArrayList<>())); // START
        nuevasCasillas.addAll(casillasAMezclar);

        // Asignar posiciones
        for (int i = 0; i < nuevasCasillas.size(); i++) {
            nuevasCasillas.get(i).setPosicion(i);
        }
        this.casillas = nuevasCasillas;
    }
}
