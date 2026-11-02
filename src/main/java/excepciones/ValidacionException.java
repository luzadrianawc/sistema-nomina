package excepciones;

/**
 * Excepción propia del sistema de nómina.
 * Se lanza cuando un dato o un resultado incumple una regla de negocio
 * (por ejemplo: horas negativas, ventas negativas o salario neto negativo).
 */
public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje); // pasa el mensaje a la clase padre para poder mostrarlo
    }
}