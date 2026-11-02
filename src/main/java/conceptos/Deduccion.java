package conceptos;

/** Algo que se RESTA al pago del empleado. */
public interface Deduccion {
    String getNombre();
    double calcular(double salarioBruto);
}
