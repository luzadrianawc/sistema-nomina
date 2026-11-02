package principal;

import conceptos.Beneficio;
import conceptos.Deduccion;
import empleados.Empleado;
import empleados.EmpleadoAsalariado;
import empleados.EmpleadoPorComision;
import empleados.EmpleadoPorHoras;
import empleados.EmpleadoTemporal;
import excepciones.ValidacionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Demostración del sistema de nómina: crea un empleado de cada tipo
 * y muestra su recibo. La lista es de tipo Empleado (polimorfismo):
 * el código no necesita saber qué tipo concreto es cada uno.
 */
public class Main {

    public static void main(String[] args) {
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new EmpleadoAsalariado("Ana Pérez", 3_000_000, 6));
        empleados.add(new EmpleadoPorHoras("Luis Gómez", 20_000, 45, 2, true));
        empleados.add(new EmpleadoPorComision("Marta Ruiz", 2_000_000, 25_000_000, 0.05));
        empleados.add(new EmpleadoTemporal("Pedro Díaz", 1_500_000,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30)));

        double totalNomina = 0;
        for (Empleado empleado : empleados) {
            imprimirRecibo(empleado);
            totalNomina += empleado.calcularSalarioNeto();
        }
        System.out.println("\nTOTAL NÓMINA (neto): " + moneda(totalNomina));

        demostrarValidacion();
    }

    /** Imprime el detalle de la nómina de un empleado. */
    private static void imprimirRecibo(Empleado empleado) {
        double bruto = empleado.calcularSalarioBruto();
        System.out.println("\n=== " + empleado.getNombre() + " ("
                + empleado.getClass().getSimpleName() + ") ===");
        System.out.println("Salario bruto: " + moneda(bruto));
        for (Beneficio beneficio : empleado.getBeneficios()) {
            System.out.println("  + " + beneficio.getNombre() + ": " + moneda(beneficio.calcular(bruto)));
        }
        for (Deduccion deduccion : empleado.getDeducciones()) {
            System.out.println("  - " + deduccion.getNombre() + ": " + moneda(deduccion.calcular(bruto)));
        }
        System.out.println("Salario neto: " + moneda(empleado.calcularSalarioNeto()));
    }

    /** Muestra que las validaciones de negocio funcionan. */
    private static void demostrarValidacion() {
        System.out.println("\n--- Prueba de validación ---");
        try {
            new EmpleadoPorHoras("Error Demo", 20_000, -5, 1, false);
        } catch (ValidacionException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }
    }

    /** Da formato de pesos con punto de miles: $1.000.000 */
    private static String moneda(double valor) {
        return String.format(Locale.US, "$%,.0f", valor).replace(',', '.');
    }
}