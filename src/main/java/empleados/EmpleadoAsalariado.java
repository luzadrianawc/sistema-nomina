package empleados;

import conceptos.Beneficio;
import conceptos.BonoAlimentacion;
import excepciones.ValidacionException;
import java.util.List;

/**
 * Empleado con salario fijo mensual.
 * Si lleva MÁS de 5 años en la empresa recibe un bono mensual del 10 % del salario.
 * Como es empleado permanente, también recibe el bono de alimentación.
 */
public class EmpleadoAsalariado extends Empleado {

    private static final int ANIOS_PARA_BONO = 5;
    private static final double PORCENTAJE_BONO = 0.10;

    private final double salarioMensual;
    private final double aniosAntiguedad;

    public EmpleadoAsalariado(String nombre, double salarioMensual, double aniosAntiguedad) {
        super(nombre);
        if (salarioMensual < 0) {
            throw new ValidacionException("El salario no puede ser negativo.");
        }
        if (aniosAntiguedad < 0) {
            throw new ValidacionException("La antigüedad no puede ser negativa.");
        }
        this.salarioMensual = salarioMensual;
        this.aniosAntiguedad = aniosAntiguedad;
    }

    @Override
    public double calcularSalarioBruto() {
        double bono = 0;
        if (aniosAntiguedad > ANIOS_PARA_BONO) { // "más de 5 años": con 5 exactos no hay bono
            bono = salarioMensual * PORCENTAJE_BONO;
        }
        return salarioMensual + bono;
    }

    @Override
    public List<Beneficio> getBeneficios() {
        List<Beneficio> beneficios = super.getBeneficios();
        beneficios.add(new BonoAlimentacion());
        return beneficios;
    }
}