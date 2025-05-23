package modelo;

import java.util.ArrayList;

public class Agujero extends Casilla {

    public Agujero(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }

    @Override
    public void realizarAccion() {
        for (Jugador j : jugadoresActuales) {
            int posActual = j.getPosicion();
            int posForatAnterior = 0;
            // Buscar el agujero anterior
            for (int i = posActual - 1; i >= 0; i--) {
                if (j.getTablero().getCasillas().get(i) instanceof Agujero) {
                    posForatAnterior = i;
                    break;
                }
            }
            j.setPosicion(posForatAnterior);
        }
    }
}
