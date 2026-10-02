# Parcial: Figuras Geométricas

<img width="554" height="554" alt="image" src="https://github.com/user-attachments/assets/24b4df4f-f8f9-4c59-9ab0-60e8c5cd23a5" />

Programa en Java para manejar círculos, triángulos, cuadriláteros y pentágonos
regulares. Permite calcular área y perímetro, dimensionar, comparar figuras del
mismo tipo, desplazar, escalar y rechazar dimensiones no válidas.

## Integrantes

| Integrante | GitHub | Responsabilidad |
|---|---|---|
| [Josue Castaño] | [@ByteJosh] | Diagrama de clases, `Figura`, `Punto`, `DimensionInvalidaException`, `Circulo` |
| [Daniel Cataño] | [@DanielJ1332] | `Triangulo`, `Cuadrilatero`, `PentagonoRegular`, `Main` y documentación |

## Requisitos

- JDK 17 o superior (`java -version`)
- Git (`git --version`)
- IntelliJ IDEA (recomendado) o una terminal

## Cómo ejecutar

### Desde IntelliJ IDEA
1. Clonar: **File → New → Project from Version Control** y pegar
   `https://github.com/ByteJosh/parcial-figuras-geometricas.git`
2. Configurar el JDK: **File → Project Structure → Project → SDK** (17 o superior).
3. Marcar `src` como Sources Root: clic derecho en `src` → **Mark Directory as → Sources Root**.
4. Abrir `src/app/Main.java` y pulsar el triángulo verde junto a `main`.

### Desde la terminal
```bash
git clone https://github.com/ByteJosh/parcial-figuras-geometricas.git
cd parcial-figuras-geometricas
mkdir out
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

En Windows (PowerShell):
```powershell
mkdir out
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out app.Main
```

## Estructura del proyecto

```
.
├── .github/              Configuración de GitHub (workflow y plantilla de PR)
├── src/
│   ├── app/Main.java     Pruebas de funcionamiento
│   └── figuras/
│       ├── Figura.java                    Clase abstracta base
│       ├── Punto.java                     Punto inmutable del plano
│       ├── DimensionInvalidaException.java
│       ├── Circulo.java
│       ├── Triangulo.java
│       ├── Cuadrilatero.java
│       └── PentagonoRegular.java
├── .env.example          Plantilla de variables sensibles
├── .gitignore
└── README.md
```

## Reglas de cada figura

| Figura | Área | Perímetro | `dimensionar()` |
|---|---|---|---|
| Círculo | πr² | 2πr | su área |
| Triángulo | b·h / 2 | l₁ + l₂ + l₃ | su perímetro |
| Cuadrilátero | D·d / 2 | l₁ + l₂ + l₃ + l₄ | suma de las distancias de sus 4 puntos al origen (0,0) |
| Pentágono regular | P·a / 2 | 5·l | suma de las coordenadas X de sus puntos |

Nota: la fórmula D·d/2 del cuadrilátero es exacta para cuadriláteros de
diagonales perpendiculares.

## Funcionalidades

- **Comparación:** `compareTo` solo permite comparar figuras del mismo tipo
  (genéricos) y usa el valor de `dimensionar()`.
- **Desplazar y escalar:** comunes a todas las figuras.
- **Validación:** cualquier dimensión menor o igual a cero lanza
  `DimensionInvalidaException`.
- **Información:** `informacion()` muestra tipo, dimensiones, área, perímetro y
  dimensionamiento.

## Diseño

- **OCP:** una figura nueva se agrega extendiendo `Figura`, sin modificar lo existente.
- **LSP / DIP:** el código cliente usa `Figura`, no clases concretas.
- **SRP:** `Punto` solo maneja geometría de puntos; cada figura calcula lo suyo.
- Alta cohesión y bajo acoplamiento: las figuras solo conocen `Punto`, `Figura`
  y la excepción.

## Variables sensibles

Las variables sensibles van en un archivo `.env` que **no se sube** al repositorio.
Copia la plantilla y completa los valores:

```bash
cp .env.example .env
```

## Flujo de trabajo en Git

```
main      versión estable y entregable
develop   integración
feature/* una rama por integrante
```

Los cambios llegan a `develop` y `main` solo mediante Pull Request con revisión
del otro integrante. Convención de commits: `feat:`, `fix:`, `docs:`,
`refactor:`, `test:`, `chore:`.
