package modelo;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Item> items;

    public Inventario() {
        this.items = new ArrayList<>();
    }

    public List<Item> getLista() {
        return items;
    }

    public void setLista(List<Item> items) {
        this.items = items;
    }

    public void añadirItem(Item item) {
        for (Item i : items) {
            if (i.getNombre().equals(item.getNombre())) {
                i.setCantidad(i.getCantidad() + item.getCantidad());
                return;
            }
        }
        items.add(item);
    }

    public void quitarItem(Item item) {
        for (Item i : items) {
            if (i.getNombre().equals(item.getNombre())) {
                int nuevaCantidad = i.getCantidad() - item.getCantidad();
                if (nuevaCantidad <= 0) {
                    items.remove(i);
                } else {
                    i.setCantidad(nuevaCantidad);
                }
                return;
            }
        }
    }

    public int contarItemsPorNombre(String nombre) {
        for (Item i : items) {
            if (i.getNombre().equals(nombre)) {
                return i.getCantidad();
            }
        }
        return 0;
    }

    public boolean contieneItem(String nombreItem) {
        return items.stream().anyMatch(item -> item.getNombre().equalsIgnoreCase(nombreItem));
    }

    public int contarItems() {
        return items.size();
    }

    public void mostrarInventario() {
        if (items.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            System.out.println("Inventario:");
            for (Item item : items) {
                System.out.println("- " + item.getNombre() + " (Cantidad: " + item.getCantidad() + ")");
            }
        }
    }

    public static void main(String[] args) {
        Pinguino jugador1 = new Pinguino(0, "Jugador1", "Azul", new Inventario());
    }
}
