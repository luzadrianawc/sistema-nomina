package empleados;

import conceptos.Deduccion;
import excepciones.ValidacionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Pruebas unitarias de las reglas de negocio de cada tipo de empleado. */
public class EmpleadosTest {

    private static final double MARGEN = 0.01; // tolerancia para comparar decimales

    // ---------- Empleado asalariado ----------

    @Test
    void asalariadoConMasDe5AniosRecibeBono() {
        Empleado ana = new EmpleadoAsalariado("Ana", 3_000_000, 6);
        assertEquals(3_300_000, ana.calcularSalarioBruto(), MARGEN);
        assertEquals(4_150_774, ana.calcularSalarioNeto(), MARGEN);
    }

    @Test
    void asalariadoConExactamente5AniosNoRecibeBono() {
        Empleado ana = new EmpleadoAsalariado("Ana", 3_000_000, 5);
        assertEquals(3_000_000, ana.calcularSalarioBruto(), MARGEN);
    }

    @Test
    void asalariadoRecibeBonoAlimentacion() {
        Empleado ana = new EmpleadoAsalariado("Ana", 3_000_000, 1);
        assertEquals(1, ana.getBeneficios().size());
    }

    // ---------- Empleado por horas ----------

    @Test
    void porHorasPagaHorasExtrasA1_5() {
        Empleado luis = new EmpleadoPorHoras("Luis", 20_000, 45, 0, false);
        assertEquals(950_000, luis.calcularSalarioBruto(), MARGEN);
    }

    @Test
    void porHorasSinHorasExtras() {
        Empleado luis = new EmpleadoPorHoras("Luis", 20_000, 40, 0, false);
        assertEquals(800_000, luis.calcularSalarioBruto(), MARGEN);
    }

    @Test
    void porHorasConMasDe1AnioYAceptaTieneFondoAhorro() {
        Empleado luis = new EmpleadoPorHoras("Luis", 20_000, 45, 2, true);
        assertEquals(3, luis.getDeducciones().size()); // pensión, ARL y fondo
        assertEquals(888_041, luis.calcularSalarioNeto(), MARGEN);
    }

    @Test
    void porHorasQueNoAceptaNoTieneFondoAhorro() {
        Empleado luis = new EmpleadoPorHoras("Luis", 20_000, 45, 2, false);
        assertEquals(2, luis.getDeducciones().size());
    }

    @Test
    void porHorasConExactamente1AnioNoTieneFondoAhorro() {
        Empleado luis = new EmpleadoPorHoras("Luis", 20_000, 45, 1, true);
        assertEquals(2, luis.getDeducciones().size());
    }

    @Test
    void horasNegativasLanzanError() {
        assertThrows(ValidacionException.class,
                () -> new EmpleadoPorHoras("Luis", 20_000, -1, 0, false));
    }

    // ---------- Empleado por comisión ----------

    @Test
    void comisionConVentasSobreElUmbralRecibeBono3PorCiento() {
        Empleado marta = new EmpleadoPorComision("Marta", 2_000_000, 25_000_000, 0.05);
        assertEquals(4_000_000, marta.calcularSalarioBruto(), MARGEN);
        assertEquals(4_819_120, marta.calcularSalarioNeto(), MARGEN);
    }

    @Test
    void comisionConVentasExactamenteEnElUmbralNoRecibeBono() {
        Empleado marta = new EmpleadoPorComision("Marta", 2_000_000, 20_000_000, 0.05);
        assertEquals(3_000_000, marta.calcularSalarioBruto(), MARGEN);
    }

    @Test
    void ventasNegativasLanzanError() {
        assertThrows(ValidacionException.class,
                () -> new EmpleadoPorComision("Marta", 2_000_000, -1, 0.05));
    }

    // ---------- Empleado temporal ----------

    @Test
    void temporalNoTieneBeneficiosYCalculaSuNeto() {
        Empleado pedro = new EmpleadoTemporal("Pedro", 1_500_000,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30));
        assertTrue(pedro.getBeneficios().isEmpty());
        assertEquals(1_432_170, pedro.calcularSalarioNeto(), MARGEN);
    }

    @Test
    void temporalConFechasInvalidasLanzaError() {
        assertThrows(ValidacionException.class, () -> new EmpleadoTemporal("Pedro", 1_500_000,
                LocalDate.of(2026, 6, 30), LocalDate.of(2026, 1, 1)));
    }

    // ---------- Validación general ----------

    @Test
    void salarioNetoNegativoLanzaError() {
        // Empleado de prueba con una deducción exagerada para forzar un neto negativo
        Empleado empleado = new Empleado("Prueba") {
            @Override
            public double calcularSalarioBruto() {
                return 1_000_000;
            }

            @Override
            public List<Deduccion> getDeducciones() {
                List<Deduccion> deducciones = new ArrayList<>();
                deducciones.add(new Deduccion() {
                    @Override
                    public String getNombre() {
                        return "Descuento exagerado";
                    }

                    @Override
                    public double calcular(double salarioBruto) {
                        return salarioBruto * 2;
                    }
                });
                return deducciones;
            }
        };
        assertThrows(ValidacionException.class, empleado::calcularSalarioNeto);
    }
}