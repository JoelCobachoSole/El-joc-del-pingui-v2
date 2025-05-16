package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Evento extends Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipo;

    public Evento(int posicion, ArrayList<Jugador> jugadoresActuales, String tipo) {
        super(posicion, jugadoresActuales);
        this.tipo = tipo;
    }

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
