package modelo;

import java.util.ArrayList;

public class Oso extends Casilla {

	public Oso(int posicion, ArrayList<Jugador> jugadoresActuales) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public String realizarAccion(Jugador jugador) {
		jugador.setPosicion(0);
		return "¡Has caído en un Oso! Vuelves al inicio.";
	}

}
