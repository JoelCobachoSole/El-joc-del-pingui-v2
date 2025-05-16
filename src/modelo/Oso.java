package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Oso extends Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

<<<<<<< Updated upstream
	public Oso(int posicion, ArrayList<Jugador> jugadoresActuales) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public void realizarAccion() {
		for (Jugador j : this.jugadoresActuales) {
			if (j instanceof Pinguino) {
				j.moverPosicion(0);
			}
		}
	}
=======
    public Oso(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }
>>>>>>> Stashed changes

    @Override
    public void realizarAccion() {
        for (Jugador j : jugadoresActuales) {
            int nuevaPos = j.getPosicion() - 2;
            if (nuevaPos < 0) nuevaPos = 0;
            j.setPosicion(nuevaPos);
        }
    }
}
