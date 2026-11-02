package conceptos;

/** Deducción del fondo de ahorro: 2 % del salario. Aplica a empleados por horas con más de 1 año que lo aceptan. */
public class FondoAhorro implements Deduccion {

    private static final double PORCENTAJE = 0.02;

    @Override
    public String getNombre() {
        return "Fondo de ahorro";
    }

    @Override
    public double calcular(double salarioBruto) {
        return salarioBruto * PORCENTAJE;
    }
}