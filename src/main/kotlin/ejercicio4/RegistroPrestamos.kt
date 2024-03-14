package org.pebiblioteca.ejercicio4

import java.time.LocalDate

/**
 *  **Clase `RegistroPrestamos`**: Ten en cuenta que tienes que refactorizar, porque posiblemente ya tengas algo para registrar los prestamos.
 *  *    - **Propiedades**:
 *  *      - Mantendrá los registros de los prestamos actuales.
 *  *      - Además, mantendra un historial de todos los préstamos realizados.
 *  *    - **Métodos**:
 *  *      - Registrar un préstamo.
 *  *      - Devolver un libro.
 *  *      - Consultar el historial de préstamos de un libro específico.
 *  *      - Consultar el historial de préstamos de un usuario específico.
 *  * 4. **Integración y Visibilidad**:
 *  *    - Asegurar que el uso de modificadores de acceso esté correctamente implementado en todas las clases para controlar la visibilidad de las propiedades y métodos.
 *  *    - Integrar las clases `**Usuario**` y **`RegistroPrestamos`** con **`GestorBiblioteca`** para mantener un registro detallado de los préstamos y devoluciones y poder consultar el historial de prestamos.
 * */
interface IGestorPrestamos{
    val prestamosActuales:MutableMap<Int,MutableList<String>>
    val historialUsuarios:MutableMap<Int,MutableList<String>>
    val historialLibros:MutableMap<Int,MutableList<String>>
    fun registrarUnPrestamo(idUsuario:Usuario, libro: ElementoBiblioteca?)
    fun devolverUnPrestamo(idUsuario:Usuario, libro: ElementoBiblioteca?)
    fun borrarRegistroUsuario(idUsuario: Usuario, libro: ElementoBiblioteca?)
    fun historialUsuarios(id:Int)
    fun historialLibros(id:Int)
}
class RegistroPrestamos():IGestorPrestamos {
    override val prestamosActuales:MutableMap<Int,MutableList<String>> = mutableMapOf()
    override val historialUsuarios:MutableMap<Int,MutableList<String>> = mutableMapOf()
    override val historialLibros:MutableMap<Int,MutableList<String>> = mutableMapOf()
    override fun registrarUnPrestamo(idUsuario:Usuario, libro: ElementoBiblioteca?){
        if (prestamosActuales.containsKey(idUsuario.id)) {
            prestamosActuales[idUsuario.id]?.addLast("el libro ${libro?.saberTitulo()} con ID:${libro?.saberId()} ha sido prestado para ${idUsuario.id}, a fecha de ${LocalDate.now()}")
        }
        else {
            prestamosActuales[idUsuario.id] = mutableListOf("el libro ${libro?.saberTitulo()} con ID:${libro?.saberId()} ha sido prestado para ${idUsuario.id}, a fecha de ${LocalDate.now()}")
        }

        if (historialUsuarios.containsKey(idUsuario.id)) {
            historialUsuarios[idUsuario.id]?.addLast("el libro ${libro?.saberTitulo()} con ID:${libro?.saberId()} ha sido prestado para ${idUsuario.id}, a fecha de ${LocalDate.now()}")
        }
        else {
            historialUsuarios[idUsuario.id] = mutableListOf("el libro ${libro?.saberTitulo()} con ID:${libro?.saberId()} ha sido prestado para ${idUsuario.id}, a fecha de ${LocalDate.now()}")
        }

        if (libro != null) {
            if (historialLibros.containsKey(libro.saberId())) {
                historialLibros[libro.saberId()]?.addLast("el libro ${libro.saberTitulo()} con ID:${libro.saberId()} ha sido prestado para ${idUsuario.id}, a fecha de ${LocalDate.now()}")
            }
            else {
                historialLibros[libro.saberId()] = mutableListOf("el libro ${libro.saberTitulo()} con ID:${libro.saberId()} ha sido prestado para ${idUsuario.id}, a fecha de ${LocalDate.now()}")
            }
        }
        idUsuario.agregarUnLibroAlUsuario(libro)
        GestorConsola.devolverPrestarLibro(historialUsuarios[idUsuario.id]?.last())
    }
    override fun devolverUnPrestamo(idUsuario:Usuario, libro: ElementoBiblioteca?){
        if (libro != null) {
            historialUsuarios[idUsuario.id]?.addLast("el libro ${libro.saberTitulo()} con ID:${libro.saberId()} ha sido devuelto por ${idUsuario.id}, a fecha de ${LocalDate.now()}")
        }
        if (libro != null) {
            historialLibros[libro?.saberId()]?.addLast("el libro ${libro.saberTitulo()} con ID:${libro.saberId()} ha sido devuelto por ${idUsuario.id}, a fecha de ${LocalDate.now()}")
        }
        borrarRegistroUsuario(idUsuario,libro)
        idUsuario.eliminarUnLibroAlUsuario(libro)
        GestorConsola.devolverPrestarLibro(historialUsuarios[idUsuario.id]?.last())
    }

    override fun borrarRegistroUsuario(idUsuario: Usuario, libro: ElementoBiblioteca?) {
        if (prestamosActuales[idUsuario.id]?.isEmpty() == true){
            prestamosActuales.remove(idUsuario.id)
        }else{
            val libroABorrar = prestamosActuales[idUsuario.id]?.find { it.contains("${libro?.saberId()}") }
            if (libroABorrar != null){
                prestamosActuales[idUsuario.id]?.remove(libroABorrar)
            }
        }
    }

    override fun historialUsuarios(id:Int){
        GestorConsola.mostrarhistorial(historialUsuarios[id])
    }
    override fun historialLibros(id:Int){
        GestorConsola.mostrarhistorial(historialLibros[id])
    }
}
