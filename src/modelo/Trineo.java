package modelo;

import java.util.ArrayList;

public class Trineo extends Casilla {

    public Trineo(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }

    @Override
    public void realizarAccion() {
        for (Jugador j : jugadoresActuales) {
            int posActual = j.getPosicion();
            int posSeguentTrineo = -1;
            // Buscar el siguiente trineo
            for (int i = posActual + 1; i < j.getTablero().getCasillas().size(); i++) {
                if (j.getTablero().getCasillas().get(i) instanceof Trineo) {
                    posSeguentTrineo = i;
                    break;
                }
            }
            // Si no hay siguiente, busca el primero (ciclo)
            if (posSeguentTrineo == -1) {
                for (int i = 1; i < posActual; i++) { // Empieza en 1 para evitar la casilla 0
                    if (j.getTablero().getCasillas().get(i) instanceof Trineo) {
                        posSeguentTrineo = i;
                        break;
                    }
                }
            }
            // Si hay otro trineo, transporta
            if (posSeguentTrineo != -1 && posSeguentTrineo != posActual) {
                j.setPosicion(posSeguentTrineo);
            }
        }
    }
}
