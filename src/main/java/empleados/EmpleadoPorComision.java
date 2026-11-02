package empleados;

import conceptos.Beneficio;
import conceptos.BonoAlimentacion;
import excepciones.ValidacionException;
import java.util.List;

/**
 * Empleado con salario base más un porcentaje de comisión sobre sus ventas.
 * Si las ventas SUPERAN $20.000.000 recibe un bono adicional del 3 % sobre las ventas.
 * Como es empleado permanente, también recibe el bono de alimentación.
 */
public class EmpleadoPorComision extends Empleado {

    private static final double UMBRAL_VENTAS_BONO = 20_000_000;
    private static final double PORCENTAJE_BONO_VENTAS = 0.03;

    private final double salarioBase;
    private final double ventas;
    private final double porcentajeComision; // se expresa entre 0 y 1 (5 % = 0.05)

    public EmpleadoPorComision(String nombre, double salarioBase, double ventas, double porcentajeComision) {
        super(nombre);
        if (salarioBase < 0) {
            throw new ValidacionException("El salario base no puede ser negativo.");
        }
        if (ventas < 0) {
            throw new ValidacionException("Las ventas no pueden ser menores a $0.");
        }
        if (porcentajeComision < 0 || porcentajeComision > 1) {
            throw new ValidacionException("El porcentaje de comisión debe estar entre 0 y 1.");
        }
        this.salarioBase = salarioBase;
        this.ventas = ventas;
        this.porcentajeComision = porcentajeComision;
    }

    @Override
    public double calcularSalarioBruto() {
        double comision = ventas * porcentajeComision;
        double bono = 0;
        if (ventas > UMBRAL_VENTAS_BONO) { // "superan": con exactamente $20.000.000 no hay bono
            bono = ventas * PORCENTAJE_BONO_VENTAS;
        }
        return salarioBase + comision + bono;
    }

    @Override
    public List<Beneficio> getBeneficios() {
        List<Beneficio> beneficios = super.getBeneficios();
        beneficios.add(new BonoAlimentacion());
        return beneficios;
    }
}
