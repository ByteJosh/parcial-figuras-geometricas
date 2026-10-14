package app;

import figuras.Figura;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Frame;
import java.util.List;
import java.util.Locale;

/** Pantalla para comparar dos figuras del mismo tipo (usa {@code compareTo}, es decir, dimensionar()). */
public class CompararFigurasDialog extends JDialog {
    private JPanel rootPanel;
    private JComboBox<String> comboTipo;
    private JComboBox<RepositorioFiguras.Item> comboA;
    private JComboBox<RepositorioFiguras.Item> comboB;
    private JTextArea txtResultado;
    private JButton btnComparar;
    private JButton btnCerrar;

    private final RepositorioFiguras repositorio;

    public CompararFigurasDialog(Frame owner, RepositorioFiguras repositorio) {
        super(owner, "Comparar figuras", true);
        this.repositorio = repositorio;
        setContentPane(rootPanel);

        comboTipo.setModel(new DefaultComboBoxModel<>(RepositorioFiguras.TIPOS));
        comboTipo.addActionListener(e -> cargarFiguras());
        btnComparar.addActionListener(e -> comparar());
        btnCerrar.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(btnComparar);
        Entradas.cerrarConEsc(this);

        cargarFiguras();
        pack();
        setLocationRelativeTo(owner);
    }

    private void cargarFiguras() {
        String tipo = (String) comboTipo.getSelectedItem();
        List<RepositorioFiguras.Item> items = repositorio.deTipo(tipo);
        comboA.setModel(new DefaultComboBoxModel<>(items.toArray(new RepositorioFiguras.Item[0])));
        comboB.setModel(new DefaultComboBoxModel<>(items.toArray(new RepositorioFiguras.Item[0])));
        boolean suficientes = items.size() >= 2;
        if (suficientes) {
            comboB.setSelectedIndex(1);
        }
        comboA.setEnabled(suficientes);
        comboB.setEnabled(suficientes);
        btnComparar.setEnabled(suficientes);
        txtResultado.setText(suficientes
                ? "Elige dos figuras y pulsa \"Comparar\"."
                : "Se necesitan al menos 2 figuras de tipo \"" + tipo + "\" (hay " + items.size() + ").");
    }

    private void comparar() {
        RepositorioFiguras.Item a = (RepositorioFiguras.Item) comboA.getSelectedItem();
        RepositorioFiguras.Item b = (RepositorioFiguras.Item) comboB.getSelectedItem();
        if (a == null || b == null) {
            return;
        }
        int r = comparar(a.figura(), b.figura());
        String relacion = r < 0 ? "es MENOR que" : r > 0 ? "es MAYOR que" : "es IGUAL a";
        txtResultado.setText(String.format(Locale.US,
                "%s: dimensionamiento %.2f%n%s: dimensionamiento %.2f%n%nResultado: %s %s %s.",
                a.nombre(), a.figura().dimensionar(),
                b.nombre(), b.figura().dimensionar(),
                a.nombre(), relacion, b.nombre()));
    }

    /** Ambas figuras son del mismo tipo (lo garantizan los combos), así que el compareTo es válido. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static int comparar(Figura<?> a, Figura<?> b) {
        return ((Comparable) a).compareTo(b);
    }
}
