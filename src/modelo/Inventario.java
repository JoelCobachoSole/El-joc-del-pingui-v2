package modelo;

import java.util.ArrayList;

public class Inventario {
    private ArrayList<Item> lista;

    public Inventario() {
        this.lista = new ArrayList<>();
    }

    public ArrayList<Item> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Item> lista) {
        this.lista = lista;
    }

    public void añadirItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser null");
        }
        if (contieneItem(item.getNombre())) {
            System.out.println("El item ya está en el inventario.");
            return;
        }
        lista.add(item);
    }

    public void quitarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser null");
        }
        lista.remove(item);
    }

    public boolean contieneItem(String nombreItem) {
        return lista.stream().anyMatch(item -> item.getNombre().equalsIgnoreCase(nombreItem));
    }

    public int contarItems() {
        return lista.size();
    }

    public void mostrarInventario() {
        if (lista.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            System.out.println("Inventario:");
            for (Item item : lista) {
                System.out.println("- " + item.getNombre() + " (Cantidad: " + item.getCantidad() + ")");
            }
        }
    }

    public static void main(String[] args) {
        Pinguino jugador1 = new Pinguino(0, "Jugador1", "Azul", new Inventario());
    }
}
