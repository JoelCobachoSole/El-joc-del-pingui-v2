package modelo;

import java.util.ArrayList;
import java.util.Random;

public class Evento extends Casilla {
	private String[] eventos = { "pez", "bolas", "rapido", "lento", "pierdeTurno", "pierdeItem", "motos" };

	public Evento(int posicion, ArrayList<Jugador> jugadoresActuales, String tipoEvento) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public void realizarAccion() {
		for (Jugador j : jugadoresActuales) {
			if (j instanceof Pinguino) {
				Random ran = new Random();
				int pos = ran.nextInt(eventos.length);
				String evento = eventos[pos];
				// Aquí puedes implementar la lógica de cada evento
				// Por ejemplo, mostrar un mensaje o modificar el inventario
				// Ejemplo: System.out.println("Evento activado: " + evento);
			}
		}
	}

}
