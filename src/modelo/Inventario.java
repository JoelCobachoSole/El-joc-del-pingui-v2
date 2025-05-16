package modelo;

import java.io.Serializable;
import java.util.ArrayList;

<<<<<<< Updated upstream
public class Inventario {
    private ArrayList<Item> lista;
=======
public class Inventario implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Item> items;
>>>>>>> Stashed changes

    public Inventario() {
        this.lista = new ArrayList<>();
    }

<<<<<<< Updated upstream
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
=======
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void añadirItem(Item nuevo) {
        for (Item i : items) {
            if (i.getNombre().equals(nuevo.getNombre())) {
                i.setCantidad(i.getCantidad() + nuevo.getCantidad());
                return;
            }
        }
        items.add(nuevo);
    }

    public void quitarItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item actual = items.get(i);
            if (actual.getNombre().equals(item.getNombre())) {
                int nuevaCantidad = actual.getCantidad() - item.getCantidad();
                if (nuevaCantidad <= 0) {
                    items.remove(i);
                } else {
                    actual.setCantidad(nuevaCantidad);
                }
                return;
            }
>>>>>>> Stashed changes
        }
        lista.remove(item);
    }

<<<<<<< Updated upstream
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
=======
    public void vaciar() {
        items.clear();
>>>>>>> Stashed changes
    }
}
