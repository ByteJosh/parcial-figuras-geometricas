package app;

import figuras.Figura;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;
import java.util.Locale;
import java.util.function.Consumer;

/** Pantalla para ver la información de cada figura, desplazarla, escalarla o eliminarla. */
public class GestionarFigurasDialog extends JDialog {
    private JPanel rootPanel;
    private JTable tabla;
    private JTextArea txtInfo;
    private JTextField txtDx;
    private JTextField txtDy;
    private JTextField txtFactor;
    private JButton btnDesplazar;
    private JButton btnEscalar;
    private JButton btnEliminar;
    private JButton btnCerrar;

    private final RepositorioFiguras repositorio;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Figura", "Área", "Perímetro", "Dimensionamiento"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    public GestionarFigurasDialog(Frame owner, RepositorioFiguras repositorio) {
        super(owner, "Gestionar figuras", true);
        this.repositorio = repositorio;
        setContentPane(rootPanel);

        tabla.setModel(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarDetalle();
            }
        });

        btnDesplazar.addActionListener(e -> aplicar(figura ->
                figura.desplazar(Entradas.leer(txtDx, "dx"), Entradas.leer(txtDy, "dy"))));
        btnEscalar.addActionListener(e -> aplicar(figura ->
                figura.escalar(Entradas.leer(txtFactor, "El factor de escala"))));
        btnEliminar.addActionListener(e -> eliminar());
        btnCerrar.addActionListener(e -> dispose());
        Entradas.cerrarConEsc(this);

        refrescar(null);
        pack();
        setLocationRelativeTo(owner);
    }

    private RepositorioFiguras.Item seleccionada() {
        int fila = tabla.getSelectedRow();
        return fila < 0 ? null : repositorio.todos().get(fila);
    }

    /** Ejecuta una operación sobre la figura seleccionada y muestra el error si la dimensión no es válida. */
    private void aplicar(Consumer<Figura<?>> operacion) {
        RepositorioFiguras.Item item = seleccionada();
        if (item == null) {
            return;
        }
        try {
            operacion.accept(item.figura());
        } catch (IllegalArgumentException ex) { // incluye DimensionInvalidaException
            Entradas.error(this, ex.getMessage());
            return;
        }
        refrescar(item);
    }

    private void eliminar() {
        RepositorioFiguras.Item item = seleccionada();
        if (item == null) {
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Eliminar " + item.nombre() + "?",
                "Eliminar figura", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            repositorio.eliminar(item);
            refrescar(null);
        }
    }

    /** Recarga la tabla y, si se indica, deja seleccionada esa figura. */
    private void refrescar(RepositorioFiguras.Item seleccionar) {
        modelo.setRowCount(0);
        for (RepositorioFiguras.Item item : repositorio.todos()) {
            Figura<?> f = item.figura();
            modelo.addRow(new Object[]{item.nombre(),
                    String.format(Locale.US, "%.2f", f.area()),
                    String.format(Locale.US, "%.2f", f.perimetro()),
                    String.format(Locale.US, "%.2f", f.dimensionar())});
        }
        int indice = seleccionar == null ? -1 : repositorio.todos().indexOf(seleccionar);
        if (indice >= 0) {
            tabla.setRowSelectionInterval(indice, indice);
        } else {
            tabla.clearSelection();
        }
        actualizarDetalle();
    }

    private void actualizarDetalle() {
        RepositorioFiguras.Item item = seleccionada();
        boolean hay = item != null;
        btnDesplazar.setEnabled(hay);
        btnEscalar.setEnabled(hay);
        btnEliminar.setEnabled(hay);
        if (hay) {
            txtInfo.setText(item.nombre() + "\n\n" + item.figura().informacion());
        } else if (repositorio.todos().isEmpty()) {
            txtInfo.setText("Aún no hay figuras. Créalas desde \"Crear figura\" en el menú principal.");
        } else {
            txtInfo.setText("Selecciona una figura de la tabla.");
        }
        txtInfo.setCaretPosition(0);
    }
}
