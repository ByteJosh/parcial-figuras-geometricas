package figuras;

import java.util.Locale;

/**
 * Punto inmutable en el plano cartesiano.
 */
public final class Punto {
    private final double x;
    private final double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Devuelve un nuevo punto desplazado.
     */
    public Punto desplazar(double dx, double dy) {
        return new Punto(x + dx, y + dy);
    }

    /**
     * Devuelve un nuevo punto escalado respecto a un punto de referencia.
     */
    public Punto escalar(double factor, Punto ref) {
        return new Punto(ref.x + (x - ref.x) * factor,
                ref.y + (y - ref.y) * factor);
    }

    /**
     * Distancia euclidiana a otro punto.
     */
    public double distanciaA(Punto otro) {
        return Math.hypot(x - otro.x, y - otro.y);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f)", x, y);
    }
}