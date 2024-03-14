package org.pebiblioteca.ejercicio3

/**
 * meto en un companion object la funcion para que pueda refenrenciarla o llamarala desde afuera
 * me aseguro que primero sumer +1 al contador, para que no haya otro libro igual con ese mismo ID
 * */
class UtilidadesBiblioteca{
    companion object{
        var contador = 0
        var usersId=0
        /**
         * retorna automaticamente un numero a modo de ID
         * @return el numero ID que es in integer
         * */
        fun generarIdentificadorUnico():Int{
            contador ++
            return contador
        }
        /**
         * retorna automaticamente un numero a modo de ID
         * @return el numero ID de los usuarios
         * */
        fun generarIdentificadorUnicoUsers():Int{
            usersId ++
            return usersId
        }
    }
}

class GestorBiblioteca(val usuarios:MutableList<Usuario>,val catalogoDeLibros:MutableList<Libro>, val registroDePrestamos:RegistroPrestamos){
    fun agregarUnLibroAlCatalogo(){
        val titulo = GestorConsola.preguntarTitulo()
        val autor = GestorConsola.preguntarAutor()
        val anoDePublicacion= GestorConsola.preguntaranoDePublicacion()
        val tematica = GestorConsola.preguntarTematica()
        val libro = Libro(titulo,autor, anoDePublicacion, tematica)

        GestorConsola.agregarLibro(libro)
        catalogoDeLibros.add(libro)

    }
    fun eliminarUnLibroAlCatalogo(){
        var id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        while (id == 0){
            id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        }
        val libro = catalogoDeLibros.find{it.saberId()==id}
        if (libro in catalogoDeLibros){
            if (libro != null) {
                GestorConsola.eliminarLibro(libro)
                catalogoDeLibros.remove(libro)
            }else{
                GestorConsola.libroNoEncontrado()
            }
        }else{
            GestorConsola.libroNoEncontrado()
        }
    }
    fun registrarUnPrestamo(){
        var usuario = GestorConsola.dameElIdUsuario(usuarios)
        while (usuario == 0){
            usuario = GestorConsola.dameElIdUsuario(usuarios)
        }
        var id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        while (id != 0){
            id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        }
        val libro = catalogoDeLibros.find{it.saberId()==id}
        val users = this.usuarios. find { it.id == usuario }
        if (users !=null && libro!=null){
        if (libro.saberEstado() == Estado.DISPONIBLE){
            registroDePrestamos.registrarUnPrestamo(users,libro)
            libro.cambiarEstado(Estado.PRESTADO)
        }else{
            GestorConsola.libroPrestado(libro)
        }
        }else{
            GestorConsola.usuarioNoEncontrado(usuario)
        }
    }
    fun devolverUnLibro(){
        var usuario = GestorConsola.dameElIdUsuario(usuarios)
        while (usuario == 0){
            usuario = GestorConsola.dameElIdUsuario(usuarios)
        }
        var id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        while (id == 0){
            id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        }
        val libro = catalogoDeLibros.find{it.saberId()==id}
        val users = this.usuarios. find { it.id == usuario }
        if (users !=null && libro!=null){
        if (libro.saberEstado() == Estado.PRESTADO){
            GestorConsola.libroDevuelto(libro)
            registroDePrestamos.devolverUnPrestamo(users,libro)
            libro.cambiarEstado(Estado.DISPONIBLE)
        }else{
            GestorConsola.libronoPrestado(libro)
        }
        }else{
            GestorConsola.usuarioNoEncontrado(usuario)
        }
    }
    fun consultarDisponibilidadDeUnLibro(){
        var id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        while (id == 0){
            id = GestorConsola.dameElIdABorrrar(catalogoDeLibros)
        }
        val unlibro = catalogoDeLibros.find{it.saberId()==id}
        GestorConsola.disponibilidadDeUnLibro(unlibro)
    }
    fun retornarLosLibrosEnFuncionDeSuEstado(){
        var opcion = GestorConsola.librosSegunFunciones()
        while (opcion == 0){
            opcion = GestorConsola.librosSegunFunciones()
        }
        if (opcion ==1){
            GestorConsola.mostraLibros(catalogoDeLibros.filter { it.saberEstado() == Estado.DISPONIBLE })
        }else{
            if (opcion == 2){
                GestorConsola.mostraLibros(catalogoDeLibros.filter { it.saberEstado() == Estado.PRESTADO })
            }else{
                GestorConsola.mostraLibros(catalogoDeLibros)
            }
        }
    }
}