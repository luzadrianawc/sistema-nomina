package empleados;

import excepciones.ValidacionException;
import java.time.LocalDate;

/**
 * Empleado con salario fijo mensual y contrato por tiempo definido.
 * No recibe bonos ni beneficios adicionales, por eso no sobreescribe getBeneficios().
 */
public class EmpleadoTemporal extends Empleado {

    private final double salarioMensual;
    private final LocalDate inicioContrato;
    private final LocalDate finContrato;

    public EmpleadoTemporal(String nombre, double salarioMensual,
                            LocalDate inicioContrato, LocalDate finContrato) {
        super(nombre);
        if (salarioMensual < 0) {
            throw new ValidacionException("El salario no puede ser negativo.");
        }
        if (inicioContrato == null || finContrato == null || !finContrato.isAfter(inicioContrato)) {
            throw new ValidacionException("La fecha de fin del contrato debe ser posterior a la de inicio.");
        }
        this.salarioMensual = salarioMensual;
        this.inicioContrato = inicioContrato;
        this.finContrato = finContrato;
    }

    @Override
    public double calcularSalarioBruto() {
        return salarioMensual;
    }

    public LocalDate getInicioContrato() {
        return inicioContrato;
    }

    public LocalDate getFinContrato() {
        return finContrato;
    }
}