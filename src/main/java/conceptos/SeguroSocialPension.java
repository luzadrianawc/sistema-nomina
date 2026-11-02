package conceptos;

/** Deducción obligatoria: 4 % del salario bruto. */
public class SeguroSocialPension implements Deduccion {

    private static final double PORCENTAJE = 0.04;

    @Override
    public String getNombre() {
        return "Seguro Social y Pensión";
    }

    @Override
    public double calcular(double salarioBruto) {
        return salarioBruto * PORCENTAJE;
    }
}