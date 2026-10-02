package figuras;

import java.util.Locale;

/**
 * Clase base de todas las figuras geométricas.
 * Define el contrato común (área, perímetro, dimensionar, desplazar, escalar)
 * y el comportamiento compartido (comparar, mostrar información, validar).
 *
 * @param <T> tipo concreto de la figura; permite comparar solo figuras del mismo tipo.
 */
public abstract class Figura<T extends Figura<T>> implements Comparable<T> {

    /**
     * Área de la figura (depende de cada figura).
     */
    public abstract double area();

    /**
     * Perímetro de la figura (depende de cada figura).
     */
    public abstract double perimetro();

    /**
     * Valor real usado para comparar figuras del mismo tipo.
     */
    public abstract double dimensionar();

    /**
     * Mueve la figura en el plano.
     */
    public abstract void desplazar(double dx, double dy);

    /**
     * Escala la figura por un factor mayor que cero.
     */
    public abstract void escalar(double factor);

    /**
     * Nombre del tipo de figura.
     */
    public abstract String tipo();

    /**
     * Descripción de las dimensiones que definen la figura.
     */
    public abstract String dimensiones();

    /**
     * Compara por el valor de dimensionamiento.
     */
    @Override
    public int compareTo(T otra) {
        return Double.compare(this.dimensionar(), otra.dimensionar());
    }

    /**
     * Información completa: tipo, dimensiones, área, perímetro y dimensionamiento.
     */
    public String informacion() {
        return String.format(Locale.US,
                "Tipo: %s%nDimensiones: %s%nÁrea: %.2f%nPerímetro: %.2f%nDimensionamiento: %.2f",
                tipo(), dimensiones(), area(), perimetro(), dimensionar());
    }

    /**
     * Valida que un valor sea estrictamente positivo; si no, lanza la excepción.
     */
    protected static double validarPositivo(double valor, String nombre) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor <= 0) {
            throw new DimensionInvalidaException(
                    nombre + " debe ser mayor que cero, pero fue: " + valor);
        }
        return valor;
    }

    // ---- Utilidades para figuras definidas por vértices ----

    /**
     * Devuelve los puntos desplazados.
     */
    protected static Punto[] desplazarPuntos(Punto[] puntos, double dx, double dy) {
        Punto[] nuevos = new Punto[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            nuevos[i] = puntos[i].desplazar(dx, dy);
        }
        return nuevos;
    }

    /**
     * Devuelve los puntos escalados respecto a su centroide.
     */
    protected static Punto[] escalarPuntos(Punto[] puntos, double factor) {
        validarPositivo(factor, "El factor de escala");
        double sx = 0, sy = 0;
        for (Punto p : puntos) {
            sx += p.getX();
            sy += p.getY();
        }
        Punto centro = new Punto(sx / puntos.length, sy / puntos.length);
        Punto[] nuevos = new Punto[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            nuevos[i] = puntos[i].escalar(factor, centro);
        }
        return nuevos;
    }

    /**
     * Suma de las distancias entre vértices consecutivos.
     */
    protected static double perimetroDe(Punto[] puntos) {
        double suma = 0;
        for (int i = 0; i < puntos.length; i++) {
            suma += puntos[i].distanciaA(puntos[(i + 1) % puntos.length]);
        }
        return suma;
    }
}