package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Trineo extends Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

	public Trineo(int posicion, ArrayList<Jugador> jugadoresActuales) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public void realizarAccion() {
		// TODO Auto-generated method stub

	}

<<<<<<< Updated upstream
=======
    @Override
    public void realizarAccion() {
        for (Jugador j : jugadoresActuales) {
            int posActual = j.getPosicion();
            int posSeguentTrineo = -1;

            // Buscar el siguiente trineo hacia adelante
            for (int i = posActual + 1; i < j.getTablero().getCasillas().size(); i++) {
                if (j.getTablero().getCasillas().get(i) instanceof Trineo) {
                    posSeguentTrineo = i;
                    break;
                }
            }

            // Si no hay siguiente, busca uno desde el inicio (evita posición 0)
            if (posSeguentTrineo == -1) {
                for (int i = 1; i < posActual; i++) {
                    if (j.getTablero().getCasillas().get(i) instanceof Trineo) {
                        posSeguentTrineo = i;
                        break;
                    }
                }
            }

            // Si encontró otro trineo diferente, transporta
            if (posSeguentTrineo != -1 && posSeguentTrineo != posActual) {
                j.setPosicion(posSeguentTrineo);
            }
        }
    }
>>>>>>> Stashed changes
}

