Sistema de Nómina (POO en Java)

Sistema de nómina con 4 tipos de empleado: asalariado, por horas, por comisión y temporal. Cada tipo calcula su propio salario, beneficios y deducciones.

Principios SOLID
- Responsabilidad única: cada deducción y cada beneficio es una clase pequeña.
- Abierto/cerrado: se agregan tipos de empleado sin modificar la clase Empleado.
- Liskov: cualquier hijo de Empleado se usa donde se espera un Empleado.
- Segregación de interfaces: las interfaces Deducción y Beneficio tienen un solo propósito.
- Inversión de dependencias: Empleado depende de interfaces, no de clases concretas.

 Código limpio y refactorización
- Constantes en lugar de números fijos.
- Métodos cortos y comentarios en cada clase.
- Validaciones en los constructores con una excepción propia (ValidacionException).
- Patrón Template Method: el cálculo del neto se escribe una sola vez en Empleado.

Pruebas unitarias
15 pruebas con JUnit 5 que verifican cada regla del negocio, incluidos los límites exactos (5 años, 1 año y $20.000.000) y las validaciones.

Metodología de desarrollo
Desarrollo iterativo e incremental: se construyó una pieza por avance (excepción, conceptos, clase base, cada tipo de empleado, Main y pruebas), verificando cada paso y guardando el progreso con commits pequeños en Git.

Supuestos
- ARL Clase I (riesgo mínimo): 0,522 % del IBC. El IBC se toma como el salario bruto.
- "Más de 5 años", "más de 1 año" y "superan $20.000.000" se toman de forma estricta.
- El fondo de ahorro se descuenta al empleado por horas con más de 1 año que lo acepta.
