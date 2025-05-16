package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class CasillaNormal extends Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

    public CasillaNormal(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }

    @Override
    public void realizarAccion() {
        // No realiza ninguna acción especial TODO revisar
    }
}
