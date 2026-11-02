package conceptos;

/** Beneficio de empleados permanentes: $1.000.000 al mes cubiertos por la empresa. */
public class BonoAlimentacion implements Beneficio {

    private static final double VALOR = 1_000_000;

    @Override
    public String getNombre() {
        return "Bono de alimentación";
    }

    @Override
    public double calcular(double salarioBruto) {
        return VALOR; // valor fijo, no depende del salario
    }
}