package modelo;

public class Pinguino extends Jugador {
    private Inventario inv;

    public Pinguino(int posicion, String nombre, String color, Inventario inv) {
        super(posicion, nombre, color);
        this.inv = inv;
    }

    public Inventario getInv() {
        return inv;
    }

    public void setInv(Inventario inv) {
        this.inv = inv;
    }

    public boolean añadirDado(Item dado) {
        if (inv.contarItemsPorNombre("Dado") < 3) {
            inv.añadirItem(dado);
            return true;
        }
        return false;
    }

    public boolean añadirPez(Item pez) {
        if (inv.contarItemsPorNombre("Pez") < 2) {
            inv.añadirItem(pez);
            return true;
        }
        return false;
    }

    public boolean añadirBolaDeNieve(Item bolaDeNieve) {
        if (inv.contarItemsPorNombre("Bola de Nieve") < 6) {
            inv.añadirItem(bolaDeNieve);
            return true;
        }
        return false;
    }
    
    public void actualizarPosicion(int[] playerPositions, int playerIndex) {
        this.setPosicion(playerPositions[playerIndex]);
    }
}
