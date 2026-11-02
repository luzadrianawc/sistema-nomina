package conceptos;

/** Deducción obligatoria ARL Clase I (riesgo mínimo): 0,522 % del IBC. */
public class Arl implements Deduccion {

    private static final double PORCENTAJE = 0.00522;

    @Override
    public String getNombre() {
        return "ARL";
    }

    @Override
    public double calcular(double salarioBruto) {
        return salarioBruto * PORCENTAJE; // el IBC se toma como el salario bruto
    }
}