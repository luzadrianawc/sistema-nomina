package empleados;

import conceptos.Deduccion;
import conceptos.FondoAhorro;
import excepciones.ValidacionException;
import java.util.List;

/**
 * Empleado al que se le paga por horas trabajadas.
 * Las horas que pasan de 40 se pagan a 1,5 veces la tarifa normal.
 * No recibe bonos. Si lleva MÁS de 1 año y acepta, aporta al fondo de ahorro (2 %).
 */
public class EmpleadoPorHoras extends Empleado {

    private static final double HORAS_NORMALES_MAXIMAS = 40;
    private static final double FACTOR_HORA_EXTRA = 1.5;
    private static final int ANIOS_PARA_FONDO = 1;

    private final double tarifaHora;
    private final double horasTrabajadas;
    private final double aniosAntiguedad;
    private final boolean aceptaFondoAhorro;

    public EmpleadoPorHoras(String nombre, double tarifaHora, double horasTrabajadas,
                            double aniosAntiguedad, boolean aceptaFondoAhorro) {
        super(nombre);
        if (tarifaHora < 0) {
            throw new ValidacionException("La tarifa por hora no puede ser negativa.");
        }
        if (horasTrabajadas < 0) {
            throw new ValidacionException("Las horas trabajadas no pueden ser negativas.");
        }
        if (aniosAntiguedad < 0) {
            throw new ValidacionException("La antigüedad no puede ser negativa.");
        }
        this.tarifaHora = tarifaHora;
        this.horasTrabajadas = horasTrabajadas;
        this.aniosAntiguedad = aniosAntiguedad;
        this.aceptaFondoAhorro = aceptaFondoAhorro;
    }

    @Override
    public double calcularSalarioBruto() {
        double horasNormales = Math.min(horasTrabajadas, HORAS_NORMALES_MAXIMAS);
        double horasExtras = Math.max(horasTrabajadas - HORAS_NORMALES_MAXIMAS, 0);
        double pagoNormal = horasNormales * tarifaHora;
        double pagoExtras = horasExtras * tarifaHora * FACTOR_HORA_EXTRA;
        return pagoNormal + pagoExtras;
    }

    @Override
    public List<Deduccion> getDeducciones() {
        List<Deduccion> deducciones = super.getDeducciones();
        if (aniosAntiguedad > ANIOS_PARA_FONDO && aceptaFondoAhorro) {
            deducciones.add(new FondoAhorro());
        }
        return deducciones;
    }
}