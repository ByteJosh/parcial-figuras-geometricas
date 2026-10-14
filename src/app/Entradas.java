package app;

import figuras.Punto;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.event.KeyEvent;

/** Utilidades compartidas por las pantallas: lectura de campos y mensajes de error. */
final class Entradas {

    private Entradas() {
    }

    /** Lee un número del campo (acepta coma o punto decimal). */
    static double leer(JTextField campo, String nombre) {
        String texto = campo.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombre + " está vacío.");
        }
        double valor;
        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nombre + " no es un número válido: \"" + campo.getText().trim() + "\".");
        }
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new IllegalArgumentException(nombre + " no es un número válido.");
        }
        return valor;
    }

    static Punto leerPunto(JTextField x, JTextField y, String nombre) {
        return new Punto(leer(x, nombre + " (X)"), leer(y, nombre + " (Y)"));
    }

    static void error(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Dato no válido", JOptionPane.ERROR_MESSAGE);
    }

    /** Permite cerrar el diálogo con la tecla Esc. */
    static void cerrarConEsc(JDialog dialogo) {
        dialogo.getRootPane().registerKeyboardAction(e -> dialogo.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
