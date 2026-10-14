package app;

import figuras.Figura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Almacén en memoria de las figuras creadas desde la interfaz.
 * Es compartido por todas las pantallas; no contiene lógica gráfica.
 */
public class RepositorioFiguras {

    /** Tipos disponibles; coinciden con el valor de {@link Figura#tipo()}. */
    public static final String[] TIPOS = {"Círculo", "Triángulo", "Cuadrilátero", "Pentágono regular"};

    /** Figura registrada con un nombre legible (por ejemplo "Círculo 2"). */
    public record Item(String nombre, Figura<?> figura) {
        @Override
        public String toString() {
            return nombre;
        }
    }

    private final List<Item> items = new ArrayList<>();
    private final Map<String, Integer> contadores = new HashMap<>();

    public Item agregar(Figura<?> figura) {
        int numero = contadores.merge(figura.tipo(), 1, Integer::sum);
        Item item = new Item(figura.tipo() + " " + numero, figura);
        items.add(item);
        return item;
    }

    public void eliminar(Item item) {
        items.remove(item);
    }

    public List<Item> todos() {
        return Collections.unmodifiableList(items);
    }

    public List<Item> deTipo(String tipo) {
        List<Item> resultado = new ArrayList<>();
        for (Item item : items) {
            if (item.figura().tipo().equals(tipo)) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}
