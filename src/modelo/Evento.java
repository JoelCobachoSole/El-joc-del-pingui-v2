package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Evento extends Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipo;

<<<<<<< Updated upstream
	@Override
	public void realizarAccion() {
		for (Jugador j : jugadoresActuales) {
			if (j instanceof Pinguino) {
				Random ran = new Random();
				int pos = ran.nextInt(6);

				if (eventos[pos].equals("pez")) {

				} else if (eventos[pos].equals("bolas")) {

				} else if (eventos[pos].equals("rapido")) {

				} else if (eventos[pos].equals("lento")) {

				} else if (eventos[pos].equals("pierdeTurno")) {

				} else if (eventos[pos].equals("pierdeItem")) {

				} else if (eventos[pos].equals("motos")) {

				}
			}
		}
	}
=======
    public Evento(int posicion, ArrayList<Jugador> jugadoresActuales, String tipo) {
        super(posicion, jugadoresActuales);
        this.tipo = tipo;
    }
>>>>>>> Stashed changes

    @Override
    public void realizarAccion() {
        for (Jugador j : jugadoresActuales) {
            switch (tipo) {
                case "Pez":
                    j.getInv().añadirItem(new Item("Pez", 1));
                    break;
                case "Nieve":
                    j.getInv().añadirItem(new Item("Nieve", 1));
                    break;
                case "Rapido":
                    j.getInv().añadirItem(new Item("Rapido", 1));
                    break;
                case "Lento":
                    j.getInv().añadirItem(new Item("Lento", 1));
                    break;
            }
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
