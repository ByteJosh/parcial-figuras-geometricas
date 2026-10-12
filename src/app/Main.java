package app;

import figuras.*;

import java.util.List;

/** Pruebas de funcionamiento de la solución de figuras geométricas. */
public class Main {

    public static void main(String[] args) {
        System.out.println("===== 1. CREACIÓN DE FIGURAS =====");
        Circulo c1 = new Circulo(new Punto(0, 0), 2);
        Circulo c2 = new Circulo(new Punto(1, 1), 3);
        Triangulo t1 = new Triangulo(new Punto(0, 0), new Punto(4, 0), new Punto(0, 3));
        Triangulo t2 = new Triangulo(new Punto(0, 0), new Punto(6, 0), new Punto(0, 8));
        Cuadrilatero q1 = new Cuadrilatero(new Punto(0, 2), new Punto(2, 0),
                new Punto(0, -2), new Punto(-2, 0));
        Cuadrilatero q2 = new Cuadrilatero(new Punto(0, 3), new Punto(3, 0),
                new Punto(0, -3), new Punto(-3, 0));
        PentagonoRegular p1 = new PentagonoRegular(new Punto(0, 0), 2);
        PentagonoRegular p2 = new PentagonoRegular(new Punto(5, 0), 4);
        System.out.println("Figuras creadas correctamente.");

        System.out.println("\n===== 2. INFORMACIÓN (polimorfismo) =====");
        List<Figura<?>> figuras = List.of(c1, t1, q1, p1);
        for (Figura<?> f : figuras) {
            System.out.println(f.informacion());
            System.out.println("----------------");
        }

        System.out.println("\n===== 3. COMPARACIÓN (mismo tipo) =====");
        comparar("Círculo", c1.compareTo(c2));
        comparar("Triángulo", t1.compareTo(t2));
        comparar("Cuadrilátero", q1.compareTo(q2));
        comparar("Pentágono", p1.compareTo(p2));

        System.out.println("\n===== 4. DESPLAZAMIENTO =====");
        System.out.println("Antes:   " + c1.dimensiones());
        c1.desplazar(5, -3);
        System.out.println("Después: " + c1.dimensiones());
        t1.desplazar(1, 1);
        System.out.println("Triángulo desplazado: " + t1.dimensiones());

        System.out.println("\n===== 5. ESCALAMIENTO =====");
        double areaAntes = c1.area();
        c1.escalar(2);
        System.out.printf("Área círculo antes: %.2f, después de x2: %.2f%n", areaAntes, c1.area());
        q1.escalar(0.5);
        System.out.println("Cuadrilátero x0.5:\n" + q1.informacion());
        p1.escalar(3);
        System.out.println("Pentágono x3:\n" + p1.informacion());

        System.out.println("\n===== 6. DIMENSIONES NO VÁLIDAS =====");
        probar("Círculo con radio 0", () -> new Circulo(new Punto(0, 0), 0));
        probar("Círculo con radio negativo", () -> new Circulo(new Punto(0, 0), -5));
        probar("Pentágono con lado -1", () -> new PentagonoRegular(new Punto(0, 0), -1));
        probar("Triángulo colineal", () -> new Triangulo(new Punto(0, 0), new Punto(1, 1), new Punto(2, 2)));
        probar("Cuadrilátero degenerado", () -> new Cuadrilatero(new Punto(0, 0), new Punto(0, 0),
                new Punto(0, 0), new Punto(0, 0)));
        probar("Escalar con factor 0", () -> c2.escalar(0));
    }

    private static void comparar(String nombre, int r) {
        String texto = r < 0 ? "la primera es MENOR" : r > 0 ? "la primera es MAYOR" : "son IGUALES";
        System.out.println(nombre + ": " + texto);
    }

    private static void probar(String descripcion, Runnable accion) {
        try {
            accion.run();
            System.out.println("[FALLO] " + descripcion + ": no lanzó excepción");
        } catch (DimensionInvalidaException e) {
            System.out.println("[OK] " + descripcion + " -> " + e.getMessage());
        }
    }
}