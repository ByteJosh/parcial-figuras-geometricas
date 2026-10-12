package figuras;

import java.util.Arrays;
import java.util.Locale;

/** Triángulo definido por 3 vértices. A = b·h/2, P = l1+l2+l3. */
public class Triangulo extends Figura<Triangulo> {
    private Punto[] vertices;

    public Triangulo(Punto a, Punto b, Punto c) {
        this.vertices = new Punto[]{a, b, c};
        validarPositivo(base(), "La base");
        validarPositivo(altura(), "La altura (puntos colineales)");
    }

    /** Base: lado entre el primer y el segundo vértice. */
    public double base() {
        return vertices[0].distanciaA(vertices[1]);
    }

    /** Altura: distancia del tercer vértice a la recta de la base. */
    public double altura() {
        Punto a = vertices[0], b = vertices[1], c = vertices[2];
        double doble = Math.abs((b.getX() - a.getX()) * (c.getY() - a.getY())
                - (c.getX() - a.getX()) * (b.getY() - a.getY()));
        return doble / base();
    }

    @Override public double area() { return base() * altura() / 2; }

    @Override public double perimetro() { return perimetroDe(vertices); }

    /** En un triángulo, dimensionar es el perímetro. */
    @Override public double dimensionar() { return perimetro(); }

    @Override
    public void desplazar(double dx, double dy) {
        vertices = desplazarPuntos(vertices, dx, dy);
    }

    @Override
    public void escalar(double factor) {
        vertices = escalarPuntos(vertices, factor);
    }

    @Override public String tipo() { return "Triángulo"; }

    @Override
    public String dimensiones() {
        return String.format(Locale.US, "base %.2f, altura %.2f, vértices %s",
                base(), altura(), Arrays.toString(vertices));
    }
}