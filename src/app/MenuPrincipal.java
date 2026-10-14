package app;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Ventana principal. Punto de entrada de la interfaz gráfica. */
public class MenuPrincipal extends JFrame {
    private JPanel rootPanel;
    private JLabel lblResumen;
    private JButton btnCrear;
    private JButton btnGestionar;
    private JButton btnComparar;
    private JButton btnSalir;

    private final RepositorioFiguras repositorio = new RepositorioFiguras();

    public MenuPrincipal() {
        super("Figuras geométricas");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnCrear.addActionListener(e -> {
            new CrearFiguraDialog(this, repositorio).setVisible(true);
            actualizarResumen();
        });
        btnGestionar.addActionListener(e -> {
            new GestionarFigurasDialog(this, repositorio).setVisible(true);
            actualizarResumen();
        });
        btnComparar.addActionListener(e -> new CompararFigurasDialog(this, repositorio).setVisible(true));
        btnSalir.addActionListener(e -> dispose());

        actualizarResumen();
        pack();
        setLocationRelativeTo(null);
    }

    private void actualizarResumen() {
        lblResumen.setText("Figuras registradas: " + repositorio.todos().size());
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignorada) {
            // Si Nimbus no está disponible se usa el aspecto por defecto.
        }
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
