package org.pebiblioteca.ejercicio4

/**
## **Ejercicio 4: Refactorización del Sistema de Gestión de Biblioteca con Herencia, Clases Abstractas e Interfaces**

 **Criterio global asociado**: 5. Herencia y uso de clases abstractas e interfaces.

 **Contexto del Ejercicio**:
Refactorizar y extender el sistema de gestión de biblioteca existente para incorporar herencia, el uso de clases abstractas e interfaces, y la aplicación de principios SOLID, con énfasis en el principio de inversión de dependencias para mejorar la modularidad y extensibilidad del sistema.

 **Especificaciones**:

1. **Introducción de Clases Abstractas e Interfaces**:
- **`ElementoBiblioteca` (Clase Abstracta)**: Crear una clase abstracta **`ElementoBiblioteca`** que defina propiedades y métodos comunes a todos los elementos de una biblioteca (como libros, revistas, DVDs). Esto puede incluir identificador, título, y estado (`**disponible**` o `**prestado**`)..
- **`Prestable` (Interface)**: Definir una interfaz **`Prestable`** que especifique los métodos **`prestar()`** y **`devolver()`**. Asegurar que la clase **`Libro`** implemente esta interfaz, lo que garantiza que todos los tipos de elementos que puedan ser prestados cumplan con estos métodos.
2. **Refactorización de `GestorBiblioteca` para Inyección de Dependencias**:
- **`IGestorPrestamos` (Interface)**: Crear una interfaz **`IGestorPrestamos`** que defina los métodos necesarios para gestionar préstamos (registrar prestamo/devolución, consultar historial, etc.). La clase **`RegistroPrestamos`** debe implementar esta interfaz. Ten en cuenta que puede que ya los tengas implementados, por tanto tendrias que refactorizar.
- Modificar la clase **`GestorBiblioteca`** para que acepte una instancia de **`IGestorPrestamos`** a través de su constructor (inyección de dependencias), en lugar de crear una instancia de **`RegistroPrestamos`** directamente dentro de la clase. Esto facilita la extensibilidad y el testing al desacoplar el sistema de gestión de la biblioteca del sistema de registro de préstamos específico. **`GestorBiblioteca`** trabajará con la interface en vez de con la instancia concreta.
- Hacer lo mismo con el **`Catalogo`**.
3. **Aplicación de Principios SOLID**:
- Asegurar que la refactorización anterior cumple con el **principio de responsabilidad única** al separar las responsabilidades de gestión de préstamos de la lógica de gestión de la biblioteca.
- Verificar que el **principio de abierto/cerrado** se cumple permitiendo la extensión del sistema (por ejemplo, añadiendo nuevos tipos de ítems para préstamo) sin modificar el código existente.
- Aplicar el **principio de sustitución de Liskov** asegurando que las subclases (por ejemplo, diferentes tipos de ítems de la biblioteca) puedan ser utilizadas en lugar de sus clases base sin afectar la correctitud del programa.
- Confirmar que el **principio de segregación de interfaces** se aplica al crear interfaces específicas y pequeñas que los clientes pueden implementar, en lugar de una única interfaz grande.
- Implementar el **principio de inversión de dependencias** como se ha descrito anteriormente, dependiendo de abstracciones (interfaces) y no de concreciones (clases).

 **Programa Principal**:

- Demostrar la creación de  elementos de biblioteca (libros, revistas, DVDs) que hereden de **`ElementoBiblioteca`** y/o implementen la interfaz **`Prestable`**.
- Instanciar **`GestorBiblioteca`** pasando una instancia de **`RegistroPrestamos`** (que implementa **`IGestorPrestamos`**) para demostrar la inyección de dependencias.
- Realizar operaciones de préstamo y devolución, asegurando que el sistema maneja correctamente los elementos.

 * */

enum class Estado(desc:String){
    DISPONIBLE ("Disponible"), PRESTADO ("Prestado");
}

/**2. **Modificaciones en la Clase `Libro`**:
 *    - Implementar control de acceso en las propiedades para asegurar que solo se puedan modificar internamente en la clase (privadas) y proporcionar métodos públicos para acceder a ellas de manera controlada si es necesario*/
class Libro(private  val titulo:String, val autor:String, private  val anoDePublicacion:String, private  var tematica:String, private  var estado: Estado = Estado.DISPONIBLE,private val id:Int=UtilidadesBiblioteca.generarIdentificadorUnico()){
    init {
        require(id > 0){ GestorConsola.errorId("id") }
        require(titulo != ""){ GestorConsola.error("titulo") }
        require(anoDePublicacion != ""){ GestorConsola.error("anoDePublicacion") }
        require(tematica != ""){ GestorConsola.error("tematica") }
        require(autor != ""){ GestorConsola.error("autor") }

    }
    fun saberEstado() = this.estado
    fun saberTitulo()=this.titulo
    fun saberautor()=this.autor
    fun saberanoDePublicacion()=this.anoDePublicacion
    fun sabertematica()= this.tematica
    fun saberId()=this.id
    fun cambiarEstado(estado: Estado){
        this.estado=estado
    }

}

/**
 * **Clase `Usuario`**:
 * *    - **Propiedades**: Identificador único (ID), nombre, lista de libros prestados.
 * *    - **Métodos**:
 * *      - Constructor para inicializar el usuario con ID y nombre. La lista de libros prestados debe iniciarse vacía.
 * *      - Métodos para agregar y quitar libros de la lista de libros prestados.
 * *      - Método para consultar los libros prestados por el usuario.
 * */
class Usuario(val nombre:String,val id:Int=UtilidadesBiblioteca.generarIdentificadorUnicoUsers()){
    val listaLibro: MutableList<Libro> = mutableListOf()
    fun agregarUnLibroAlUsuario(libro: Libro){
        listaLibro.add(libro)
        GestorConsola.ususarioCogeUnLibro(libro.saberId(),nombre)

    }
    fun eliminarUnLibroAlUsuario(libro: Libro){
        GestorConsola.ususarioDevuelveUnLibro(libro.saberId(),nombre)
        listaLibro.remove(libro)
    }
    fun consultarLibrosPrestado(){
        GestorConsola.mostraLibros(listaLibro.filter { it.saberEstado() == Estado.PRESTADO })
    }
}

fun main(){
    val libro= mutableListOf(Libro("hola","mundo","22/11/2003","miedo"),
        Libro("mundo","mundo","22/11/2003","miedo"))
    val users= mutableListOf(Usuario("nico"))
    val registroPrestamos = RegistroPrestamos()
    val gestorBiblioteca = GestorBiblioteca(users,libro,registroPrestamos)
    while (true){
        var numero = 0
        GestorConsola.mostrarOpciones()
        numero = GestorConsola.opciones(6)
        while (numero != 0){
            when(numero){
                1-> gestorBiblioteca.agregarUnLibroAlCatalogo()
                2-> gestorBiblioteca.eliminarUnLibroAlCatalogo()
                3-> gestorBiblioteca.registrarUnPrestamo()
                4 -> gestorBiblioteca.devolverUnLibro()
                5-> gestorBiblioteca.consultarDisponibilidadDeUnLibro()
                6-> gestorBiblioteca.retornarLosLibrosEnFuncionDeSuEstado()
            }
            numero = 0

        }
    }
}
