package empleados;

import conceptos.Arl;
import conceptos.Beneficio;
import conceptos.Deduccion;
import conceptos.SeguroSocialPension;
import excepciones.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 * Molde de todos los empleados de la empresa.
 * Define el cálculo general de la nómina; cada tipo de empleado solo
 * aporta lo que lo hace distinto (su salario bruto, sus beneficios y
 * sus deducciones extra).
 */
public abstract class Empleado {

    protected final String nombre;

    protected Empleado(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del empleado es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    /** Cada tipo de empleado calcula su salario bruto a su manera. */
    public abstract double calcularSalarioBruto();

    /** Por defecto un empleado no tiene beneficios; las hijas lo sobreescriben si los tienen. */
    public List<Beneficio> getBeneficios() {
        return new ArrayList<>();
    }

    /** Deducciones obligatorias para todos; las hijas pueden agregar más. */
    public List<Deduccion> getDeducciones() {
        List<Deduccion> deducciones = new ArrayList<>();
        deducciones.add(new SeguroSocialPension());
        deducciones.add(new Arl());
        return deducciones;
    }

    public double calcularTotalBeneficios() {
        double bruto = calcularSalarioBruto();
        double total = 0;
        for (Beneficio beneficio : getBeneficios()) {
            total += beneficio.calcular(bruto);
        }
        return total;
    }

    public double calcularTotalDeducciones() {
        double bruto = calcularSalarioBruto();
        double total = 0;
        for (Deduccion deduccion : getDeducciones()) {
            total += deduccion.calcular(bruto);
        }
        return total;
    }

    /** Salario neto = bruto + beneficios - deducciones. Nunca puede ser negativo. */
    public double calcularSalarioNeto() {
        double neto = calcularSalarioBruto() + calcularTotalBeneficios() - calcularTotalDeducciones();
        if (neto < 0) {
            throw new ValidacionException(nombre + ": el salario neto no puede ser negativo.");
        }
        return neto;
    }
}
