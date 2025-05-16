package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Foca extends Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

    public Foca(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }

    @Override
    public void realizarAccion() {
        for (Jugador j : jugadoresActuales) {
            int nuevaPos = j.getPosicion() + 1;
            if (nuevaPos < j.getTablero().getCasillas().size()) {
                j.setPosicion(nuevaPos);
            }
        }
    }
}