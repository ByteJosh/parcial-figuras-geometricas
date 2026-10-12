package figuras;

import java.util.Arrays;
import java.util.Locale;

/** Pentágono regular definido por su centro y su lado. A = P·a/2, P = 5l. */
public class PentagonoRegular extends Figura<PentagonoRegular> {
    private Punto[] vertices;
    private double lado;

    public PentagonoRegular(Punto centro, double lado) {
        this.lado = validarPositivo(lado, "El lado");
        this.vertices = generarVertices(centro, lado);
    }

    /** Calcula los 5 vértices a partir del radio circunscrito. */
    private static Punto[] generarVertices(Punto centro, double lado) {
        double radio = lado / (2 * Math.sin(Math.PI / 5));
        Punto[] puntos = new Punto[5];
        for (int i = 0; i < 5; i++) {
            double angulo = Math.PI / 2 + i * 2 * Math.PI / 5;
            puntos[i] = new Punto(centro.getX() + radio * Math.cos(angulo),
                    centro.getY() + radio * Math.sin(angulo));
        }
        return puntos;
    }

    /** Apotema: distancia del centro al punto medio de un lado. */
    public double apotema() { return lado / (2 * Math.tan(Math.PI / 5)); }

    @Override public double perimetro() { return 5 * lado; }

    @Override public double area() { return perimetro() * apotema() / 2; }

    /** Suma de las coordenadas X de cada punto. */
    @Override
    public double dimensionar() {
        double suma = 0;
        for (Punto p : vertices) {
            suma += p.getX();
        }
        return suma;
    }

    @Override
    public void desplazar(double dx, double dy) {
        vertices = desplazarPuntos(vertices, dx, dy);
    }

    @Override
    public void escalar(double factor) {
        vertices = escalarPuntos(vertices, factor); // valida el factor
        lado = lado * factor;
    }

    @Override public String tipo() { return "Pentágono regular"; }

    @Override
    public String dimensiones() {
        return String.format(Locale.US, "lado %.2f, apotema %.2f, vértices %s",
                lado, apotema(), Arrays.toString(vertices));
    }
}