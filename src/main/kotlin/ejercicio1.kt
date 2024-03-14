package org.pebiblioteca

import org.pebiblioteca.ejercicio2.Elementos
import org.pebiblioteca.ejercicio2.GestorConsola

/**### **Ejercicio 1: Sistema de Gestión de Biblioteca**

**Criterio global asociado**: 1. Instancia objetos y hacer uso de ellos.

**Contexto del Ejercicio**:
Desarrollar un sistema simple de gestión para una biblioteca que permita gestionar y realizar prestamos de libros y otros elementos que veremos mas adelante. Este sistema debe permitir agregar/eliminar libros a un catálogo, registrar préstamos/devoluciones de libros a usuarios y consultar el estado actual de un libro (`**disponible**` o `**prestado**`).

**Especificaciones**:

1. **Clase `Libro`**:  solo contendrá datos, sin ninguna lógica asociada.

- Algunos de los datos que contendrá: Id, título, autor, año de publicación, temática, y estado (disponible o prestado).
- Se debe poder inicializar las propiedades del libro, y no pueden ser vacías. El estado inicial de cualquier libro al incorporarse a la biblioteca es `**disponible**`.

1. **Clase `GestorBiblioteca`**: Contendrá la lógica para gestionar los libros, integrará el **(1) catalogo** y  el **(2) registro de prestamos**. Sobre estas dos clases hablaremos a lo largo de los ejercicios e irán evolucionando la implementación de ellas.

- **Propiedades**:
- Catalogo de libros (o cualquier otro elemento que se gestione) en la biblioteca. El catalogo de libro se inicializa sin libros, y posteriormente se incorporan.
- El Registro de prestamos de los elementos del catálogo. Igualmente, el registro de prestamos se inicializa sin registros, y posteriormente se incorporan.
- **Métodos**:
- Agregar un libro al catálogo.
- Eliminar un libro del catálogo.
- Registrar un préstamo (cambia el estado del libro a prestado si está disponible).
- Devolver un libro (cambia el estado del libro a disponible).
- Consultar disponibilidad de un libro.
- Retornar los libros en función de su estado (todos, disponibles y prestados).*/

interface Elementos{
    val id:Int
    val titulo:String
    val autor:String
    val anoDePublicacion:String
    var tematica:String
    var estado: org.pebiblioteca.ejercicio2.Estado
}
enum class Estado(desc:String){
    DISPONIBLE ("Disponible"), PRESTADO ("Prestado");
}
data class libro(override val id:Int, override val titulo:String, override val autor:String, override val anoDePublicacion:String, override var tematica:String, override var estado: org.pebiblioteca.ejercicio2.Estado = org.pebiblioteca.ejercicio2.Estado.DISPONIBLE):
    Elementos {
    init {
        require(id < 0){ GestorConsola.errorId("id")}
        require(titulo == ""){ GestorConsola.error("titulo")}
        require(anoDePublicacion == ""){ GestorConsola.error("anoDePublicacion")}
        require(tematica == ""){ GestorConsola.error("tematica")}
        require(autor == ""){ GestorConsola.error("autor")}

    }
}

object GestorConsola{
    fun error(elemento:String){
        println("EL $elemento no puede estar vacio")
    }
    fun errorId(elemento:String){
        println("EL $elemento no puede ser negativo")
    }

    fun agregarLibro(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} ha sido agregado")
    }
    fun eliminarLibro(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} ha sido agregado")
    }

    fun libroYaSidoAgregado(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} ya ha fue agregado")
    }

    fun libroNoEncontrado(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} no encontrado")
    }

    fun prestarLibro(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} ha sido prestado")
    }

    fun libroPrestado(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} ya ha sido prestado")
    }

    fun libroDevuelto(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} ha sido devuelto")
    }

    fun libronoPrestado(libro: Elementos) {
        println("El ${libro.titulo} con id:${libro.id} no ha sido prestado")
    }

    fun disponibilidadDeUnLibro(libro: Elementos?) {
        if (libro != null){
            println("El ${libro.titulo} con id:${libro.id} esta ${libro.estado}")
        }else{
            println("Ellibro no ha sido encontrado")
        }
    }

    fun mostraLibros(libros: List<Elementos>) {
        for (libro in libros){
            println("El ${libro.titulo} con id:${libro.id} esta ${libro.estado}")
        }

    }
}

/**Clase `GestorBiblioteca`**: Contendrá la lógica para gestionar los libros, integrará el **(1) catalogo** y  el **(2) registro de prestamos**. Sobre estas dos clases hablaremos a lo largo de los ejercicios e irán evolucionando la implementación de ellas.

- **Propiedades**:
- Catalogo de libros (o cualquier otro elemento que se gestione) en la biblioteca. El catalogo de libro se inicializa sin libros, y posteriormente se incorporan.
- El Registro de prestamos de los elementos del catálogo. Igualmente, el registro de prestamos se inicializa sin registros, y posteriormente se incorporan.
- **Métodos**:
- Agregar un libro al catálogo.
- Eliminar un libro del catálogo.
- Registrar un préstamo (cambia el estado del libro a prestado si está disponible).
- Devolver un libro (cambia el estado del libro a disponible).
- Consultar disponibilidad de un libro.
- Retornar los libros en función de su estado (todos, disponibles y prestados).*/

class GestorBiblioteca(val catalogoDeLibros:MutableList<Elementos>, val registroDePrestamos:MutableList<String>){
    fun agregarUnLibroAlCatalogo(libro: Elementos){
        if (libro !in catalogoDeLibros){
            GestorConsola.agregarLibro(libro)
            catalogoDeLibros.add(libro)
        }else{
            GestorConsola.libroYaSidoAgregado(libro)
        }

    }
    fun eliminarUnLibroAlCatalogo(libro: Elementos){
        if (libro in catalogoDeLibros){
            catalogoDeLibros.remove(libro)
            GestorConsola.eliminarLibro(libro)
        }else{
            GestorConsola.libroNoEncontrado(libro)
        }
    }
    fun registrarUnPrestamo(libro: Elementos){
        if (libro.estado == org.pebiblioteca.ejercicio2.Estado.DISPONIBLE){
            GestorConsola.prestarLibro(libro)
            libro.estado = org.pebiblioteca.ejercicio2.Estado.PRESTADO
        }else{
            GestorConsola.libroPrestado(libro)
        }
    }
    fun devolverUnLibro(libro: Elementos){
        if (libro.estado == org.pebiblioteca.ejercicio2.Estado.PRESTADO){
            GestorConsola.libroDevuelto(libro)
            libro.estado = org.pebiblioteca.ejercicio2.Estado.DISPONIBLE
        }else{
            GestorConsola.libronoPrestado(libro)
        }
    }
    fun consultarDisponibilidadDeUnLibro(libro: Elementos){
        val unLibro= catalogoDeLibros.find{it.id == libro.id}
        GestorConsola.disponibilidadDeUnLibro(unLibro)
    }
    fun retornarLosLibrosEnFuncionDeSuEstado(estado: org.pebiblioteca.ejercicio2.Estado?){
        if (estado == org.pebiblioteca.ejercicio2.Estado.DISPONIBLE){
            GestorConsola.mostraLibros(catalogoDeLibros.filter { it.estado == org.pebiblioteca.ejercicio2.Estado.DISPONIBLE })
        }else{
            if (estado == org.pebiblioteca.ejercicio2.Estado.PRESTADO){
            GestorConsola.mostraLibros(catalogoDeLibros.filter { it.estado == org.pebiblioteca.ejercicio2.Estado.PRESTADO })
            }else{
                GestorConsola.mostraLibros(catalogoDeLibros)
            }
        }
    }
}