package modelo;

import java.util.ArrayList;

public class Agujero extends Casilla {

	public Agujero(int posicion, ArrayList<Jugador> jugadoresActuales) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public String realizarAccion(Jugador jugador) {
		int posActual = jugador.getPosicion();
		int posAnterior = -1;
		for (int i = posActual - 1; i >= 0; i--) {
			if (this.getTablero() != null && this.getTablero().getCasillas().get(i) instanceof Agujero) {
				posAnterior = i;
				break;
			}
		}
		if (posAnterior != -1) {
			jugador.setPosicion(posAnterior);
			return "¡Has caído en un Agujero! Retrocedes al agujero anterior.";
		} else {
			return "¡Has caído en un Agujero! Pero no hay agujero anterior, te quedas aquí.";
		}
	}

}
