package figuras;

import java.util.Arrays;
import java.util.Locale;

/**
 * Cuadrilátero definido por 4 vértices. A = D·d/2 (diagonales), P = suma de los 4 lados.
 * La fórmula de área del enunciado aplica a cuadriláteros de diagonales perpendiculares.
 */
public class Cuadrilatero extends Figura<Cuadrilatero> {
    private static final Punto ORIGEN = new Punto(0, 0);
    private Punto[] vertices;

    public Cuadrilatero(Punto a, Punto b, Punto c, Punto d) {
        this.vertices = new Punto[]{a, b, c, d};
        validarPositivo(diagonalMayor(), "La diagonal D");
        validarPositivo(diagonalMenor(), "La diagonal d");
    }

    /** Diagonal entre el primer y el tercer vértice. */
    public double diagonalMayor() {
        return vertices[0].distanciaA(vertices[2]);
    }

    /** Diagonal entre el segundo y el cuarto vértice. */
    public double diagonalMenor() {
        return vertices[1].distanciaA(vertices[3]);
    }

    @Override public double area() { return diagonalMayor() * diagonalMenor() / 2; }

    @Override public double perimetro() { return perimetroDe(vertices); }

    /** Suma de las distancias de los 4 puntos al centro del plano (0,0). */
    @Override
    public double dimensionar() {
        double suma = 0;
        for (Punto p : vertices) {
            suma += p.distanciaA(ORIGEN);
        }
        return suma;
    }

    @Override
    public void desplazar(double dx, double dy) {
        vertices = desplazarPuntos(vertices, dx, dy);
    }

    @Override
    public void escalar(double factor) {
        vertices = escalarPuntos(vertices, factor);
    }

    @Override public String tipo() { return "Cuadrilátero"; }

    @Override
    public String dimensiones() {
        return String.format(Locale.US, "D %.2f, d %.2f, vértices %s",
                diagonalMayor(), diagonalMenor(), Arrays.toString(vertices));
    }
}