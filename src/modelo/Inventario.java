package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventario implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Item> items;

    public Inventario() {
        this.items = new ArrayList<>();
    }

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

    public void vaciar() {
        items.clear();
    }
}
