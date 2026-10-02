package figuras;

/**
 * Se lanza cuando una dimensión es menor o igual a cero o no es válida.
 */
public class DimensionInvalidaException extends IllegalArgumentException {
    public DimensionInvalidaException(String mensaje) {
        super(mensaje);
    }
}