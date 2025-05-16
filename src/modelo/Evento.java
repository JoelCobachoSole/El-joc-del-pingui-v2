package modelo;

import java.util.ArrayList;
import java.util.Random;

public class Evento extends Casilla {
	private String[] eventos = { "pez", "bolas", "rapido", "lento", "pierdeTurno", "pierdeItem", "motos" };

	public Evento(int posicion, ArrayList<Jugador> jugadoresActuales, String tipoEvento) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public String realizarAccion(Jugador jugador) {
	    Random ran = new Random();
	    int pos = ran.nextInt(eventos.length);
	    String evento = eventos[pos];
	    // Aquí puedes poner efectos reales si quieres
	    return "¡Evento activado: " + evento + "!";
	}

}
