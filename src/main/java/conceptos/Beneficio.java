package conceptos;

/** Algo que se SUMA al pago del empleado. */
public interface Beneficio {
    String getNombre();
    double calcular(double salarioBruto);
}