package org.pebiblioteca.ejercicio3

/**
 * ### **Ejercicio 3: Implementación de Control de Acceso y Clases Auxiliares en el Sistema de Gestión de Biblioteca**
 *
 * **Criterio global asociado**: 4. Definir clases y su contenido.
 *
 * **Contexto del Ejercicio**:
 * Refactorizar y extender el sistema de gestión de biblioteca existente, introduciendo conceptos avanzados de definición de clases y control de acceso para mejorar la estructura, eficiencia, y seguridad del sistema.
 *
 * **Especificaciones**:
 *
 * 1. **Clase `Usuario`**:
 *    - **Propiedades**: Identificador único (ID), nombre, lista de libros prestados.
 *    - **Métodos**:
 *      - Constructor para inicializar el usuario con ID y nombre. La lista de libros prestados debe iniciarse vacía.
 *      - Métodos para agregar y quitar libros de la lista de libros prestados.
 *      - Método para consultar los libros prestados por el usuario.
 * 2. **Modificaciones en la Clase `Libro`**:
 *    - Implementar control de acceso en las propiedades para asegurar que solo se puedan modificar internamente en la clase (privadas) y proporcionar métodos públicos para acceder a ellas de manera controlada si es necesario.
 * 3. **Clase `RegistroPrestamos`**: Ten en cuenta que tienes que refactorizar, porque posiblemente ya tengas algo para registrar los prestamos.
 *    - **Propiedades**:
 *      - Mantendrá los registros de los prestamos actuales.
 *      - Además, mantendra un historial de todos los préstamos realizados.
 *    - **Métodos**:
 *      - Registrar un préstamo.
 *      - Devolver un libro.
 *      - Consultar el historial de préstamos de un libro específico.
 *      - Consultar el historial de préstamos de un usuario específico.
 * 4. **Integración y Visibilidad**:
 *    - Asegurar que el uso de modificadores de acceso esté correctamente implementado en todas las clases para controlar la visibilidad de las propiedades y métodos.
 *    - Integrar las clases `**Usuario**` y **`RegistroPrestamos`** con **`GestorBiblioteca`** para mantener un registro detallado de los préstamos y devoluciones y poder consultar el historial de prestamos.
 *
 * **Programa Principal**:
 *
 * - Crear instancias de varios usuarios y realizar operaciones de préstamo y devolución utilizando el sistema modificado.
 * - Utilizar la clase **`GestorBiblioteca`,** que hará uso de **`RegistroPrestamos`**, para realizar las operaciones de prestamos y devolución y mostrar el historial de préstamos y devoluciones.
 * - Demostrar cómo se puede acceder a la información de los libros y usuarios de manera controlada a través de los métodos públicos sin exponer directamente las propiedades internas.
 *
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
