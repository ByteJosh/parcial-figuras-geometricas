package figuras;

import java.util.Locale;

/** Círculo definido por su centro y su radio. A = πr², P = 2πr. */
public class Circulo extends Figura<Circulo> {
    private Punto centro;
    private double radio;

    public Circulo(Punto centro, double radio) {
        this.centro = centro;
        this.radio = validarPositivo(radio, "El radio");
    }

    @Override public double area() { return Math.PI * radio * radio; }

    @Override public double perimetro() { return 2 * Math.PI * radio; }

    /** En un círculo, dimensionar es el área. */
    @Override public double dimensionar() { return area(); }

    @Override
    public void desplazar(double dx, double dy) {
        centro = centro.desplazar(dx, dy);
    }

    @Override
    public void escalar(double factor) {
        validarPositivo(factor, "El factor de escala");
        radio = radio * factor;
    }

    @Override public String tipo() { return "Círculo"; }

    @Override
    public String dimensiones() {
        return String.format(Locale.US, "Centro %s, radio %.2f", centro, radio);
    }
}