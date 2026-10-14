package app;

import figuras.Circulo;
import figuras.Cuadrilatero;
import figuras.Figura;
import figuras.PentagonoRegular;
import figuras.Triangulo;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.Frame;
import java.util.Locale;

/** Pantalla para crear círculos, triángulos, cuadriláteros y pentágonos regulares. */
public class CrearFiguraDialog extends JDialog {
    /** Nombres de las tarjetas del CardLayout, en el mismo orden que RepositorioFiguras.TIPOS. */
    private static final String[] TARJETAS = {"CIRCULO", "TRIANGULO", "CUADRILATERO", "PENTAGONO"};

    private JPanel rootPanel;
    private JComboBox<String> comboTipo;
    private JPanel panelDatos;
    private JLabel lblEstado;
    private JButton btnCrear;
    private JButton btnCerrar;

    // Círculo
    private JTextField txtCirX;
    private JTextField txtCirY;
    private JTextField txtCirRadio;
    // Triángulo
    private JTextField txtTriAx;
    private JTextField txtTriAy;
    private JTextField txtTriBx;
    private JTextField txtTriBy;
    private JTextField txtTriCx;
    private JTextField txtTriCy;
    // Cuadrilátero
    private JTextField txtCuaAx;
    private JTextField txtCuaAy;
    private JTextField txtCuaBx;
    private JTextField txtCuaBy;
    private JTextField txtCuaCx;
    private JTextField txtCuaCy;
    private JTextField txtCuaDx;
    private JTextField txtCuaDy;
    // Pentágono regular
    private JTextField txtPenX;
    private JTextField txtPenY;
    private JTextField txtPenLado;

    private final RepositorioFiguras repositorio;

    public CrearFiguraDialog(Frame owner, RepositorioFiguras repositorio) {
        super(owner, "Crear figura", true);
        this.repositorio = repositorio;
        setContentPane(rootPanel);

        comboTipo.setModel(new DefaultComboBoxModel<>(RepositorioFiguras.TIPOS));
        comboTipo.addActionListener(e -> mostrarTarjeta());
        btnCrear.addActionListener(e -> crear());
        btnCerrar.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(btnCrear);
        Entradas.cerrarConEsc(this);

        mostrarTarjeta();
        pack();
        setLocationRelativeTo(owner);
    }

    private void mostrarTarjeta() {
        ((CardLayout) panelDatos.getLayout()).show(panelDatos, TARJETAS[comboTipo.getSelectedIndex()]);
        lblEstado.setText(" ");
    }

    private void crear() {
        Figura<?> figura;
        try {
            figura = construir(comboTipo.getSelectedIndex());
        } catch (IllegalArgumentException ex) { // incluye DimensionInvalidaException
            Entradas.error(this, ex.getMessage());
            return;
        }
        RepositorioFiguras.Item item = repositorio.agregar(figura);
        lblEstado.setText(String.format(Locale.US, "Se creó %s (área %.2f, perímetro %.2f)",
                item.nombre(), figura.area(), figura.perimetro()));
    }

    private Figura<?> construir(int indice) {
        return switch (indice) {
            case 0 -> new Circulo(
                    Entradas.leerPunto(txtCirX, txtCirY, "Centro"),
                    Entradas.leer(txtCirRadio, "El radio"));
            case 1 -> new Triangulo(
                    Entradas.leerPunto(txtTriAx, txtTriAy, "Vértice A"),
                    Entradas.leerPunto(txtTriBx, txtTriBy, "Vértice B"),
                    Entradas.leerPunto(txtTriCx, txtTriCy, "Vértice C"));
            case 2 -> new Cuadrilatero(
                    Entradas.leerPunto(txtCuaAx, txtCuaAy, "Vértice A"),
                    Entradas.leerPunto(txtCuaBx, txtCuaBy, "Vértice B"),
                    Entradas.leerPunto(txtCuaCx, txtCuaCy, "Vértice C"),
                    Entradas.leerPunto(txtCuaDx, txtCuaDy, "Vértice D"));
            default -> new PentagonoRegular(
                    Entradas.leerPunto(txtPenX, txtPenY, "Centro"),
                    Entradas.leer(txtPenLado, "El lado"));
        };
    }
}
