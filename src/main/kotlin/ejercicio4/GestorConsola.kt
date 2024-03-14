package org.pebiblioteca.ejercicio4

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

object GestorConsola{
    fun error(elemento:String){
        println("EL $elemento no puede estar vacio")
    }
    fun errorId(elemento:String){
        println("EL $elemento no puede ser negativo")
    }

    fun agregarLibro(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} ha sido agregado")
    }
    fun eliminarLibro(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} ha sido agregado")
    }

    fun libroYaSidoAgregado(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} ya ha fue agregado")
    }

    fun libroNoEncontrado() {
        println("Libro no encontrado")
    }

    fun prestarLibro(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} ha sido prestado")
    }

    fun libroPrestado(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} ya ha sido prestado")
    }

    fun libroDevuelto(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} ha sido devuelto")
    }

    fun libronoPrestado(libro: Libro) {
        println("El ${libro.saberTitulo()} con id:${libro.saberId()} no ha sido prestado")
    }

    fun disponibilidadDeUnLibro(libro: Libro?) {
        if (libro != null){
            println("El ${libro.saberTitulo()} con id:${libro.saberId()} esta ${libro.saberEstado()}")
        }else{
            println("Ellibro no ha sido encontrado")
        }
    }

    fun mostraLibros(libros: List<Libro>) {
        for (libro in libros){
            println("El ${libro.saberTitulo()} con id:${libro.saberId()} esta ${libro.saberEstado()}")
        }

    }

    fun preguntarTitulo(): String {
        println("Dime el titulo del libro")
        var nombre = readln()
        while (nombre == ""){
            println("ERROR, el titulo no debe estar vacio, Dime el titulo del libro")
            nombre= readln()
        }
        return nombre
    }

    fun preguntaranoDePublicacion(): String {
        println("Dime el año de publicacion del libro")
        var anoDePublicacion = readln()
        while (anoDePublicacion == ""){
            println("ERROR, el año de publicacion no debe estar vacio, Dime el año de publicacion del libro")
            anoDePublicacion= readln()
        }
        return anoDePublicacion
    }

    fun preguntarAutor(): String {
        println("Dime el autor del libro")
        var autor = readln()
        while (autor == ""){
            println("ERROR, el autor no debe estar vacio, Dime el el autor del libro")
            autor= readln()
        }
        return autor
    }
    fun preguntarTematica():String{
        println("Dime la tematica del libro")
        var autor = readln()
        while (autor == ""){
            println("ERROR, la tematica no debe estar vacio, Dime el la tematica del libro")
            autor= readln()
        }
        return autor
    }

    fun ususarioCogeUnLibro(saberId: Int, nombre: String) {
        println("El usuario $nombre, le prestan el libro con id:$saberId")
    }

    fun ususarioDevuelveUnLibro(saberId: Int, nombre: String) {
        println("El usuario $nombre, devuelve el libro con id:$saberId")
    }

    fun devolverPrestarLibro(last: String?) {
        println(last)
    }

    fun usuarioNoEncontrado(usuario: Int) {
        println("el ususario con el $usuario, no existe")
    }

    fun mostrarOpciones() {
        println("1. agregar un  libro al catalogo")
        println("2.eliminar un libroc al catalogo")
        println("3. registrar un prestamo")
        println("4. devolver un libro")
        println("5. consultar disponibilidad de un libro")
        println("6. retornar los libros en funcion de su estado")
    }
    fun opciones(numeroOpciones:Int):Int{
        try {
            var numero = readln().toInt()
            if (numero <0 && numero< numeroOpciones){
                throw IllegalArgumentException()
            }
            return numero
        }catch (e:NumberFormatException){
            errorOpciones()
            return 0
        }catch (e:IllegalArgumentException){
            errorOpciones()
            return 0
        }
    }

    private fun errorOpciones() {
        println("ERROR debes introducir un numero del 1 al 6")
    }

    fun dameElIdABorrrar(catalogoDeLibros: MutableList<Libro>): Int {
        catalogoDeLibros.forEach{println("${it.saberId()}:${it.saberTitulo()},${it.autor}")}
        println("Dame EL id del libro a borrar")
        return opciones(catalogoDeLibros.size)
    }

    fun dameElIdUsuario(usuarios: MutableList<Usuario>): Int {
        usuarios.forEach{println("${it.id}:${it.nombre}")}
        return opciones(usuarios.size)
    }

    fun librosSegunFunciones(): Int {
        println("1. dar listado de los libros Disponibles")
        println("2. dar listado de los libros Prestados")
        println("3. dar listado de todos los libros")
        return opciones(3)
    }
}
