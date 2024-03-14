

/**
 * ### **Ejercicio 2: Ampliación del Sistema de Gestión de Biblioteca con Métodos Estáticos**
 *
 * **Criterio global asociado**: 2. Crear y llamar métodos estáticos.
 *
 * **Contexto del Ejercicio**:
 * Sobre la base del sistema de gestión de biblioteca ya desarrollado, ahora se busca ampliar sus funcionalidades incorporando métodos estáticos que permitan realizar operaciones generales sin necesidad de instanciar objetos específicos.
 *
 * **Especificaciones**:
 *
 * 1. **Clase `UtilidadesBiblioteca`**:
 *    - Esta clase deberá contener métodos estáticos que ofrezcan funcionalidades adicionales al sistema de gestión de biblioteca sin necesitar una instancia para ser utilizados.
 *    - **Métodos Estáticos Propuestos**:
 *      - **`generarIdentificadorUnico()`** que retorne un identificador único (string o número) para cada libro (o cualquier otro elemento) nuevo agregado al catálogo. Este método puede, por ejemplo, combinar la fecha y hora actual con un contador o generar un UUID.
 * 2. Si consideras necesario cualquier otro método estático en cualquier clase, créalo y deja clara su existencia.
 * 3. **Integración con `GestorBiblioteca`**:
 *    - Modificar el método de agregar libro para que utilice el método estático **`generarIdentificadorUnico()`** para asignar un identificador único a cada libro nuevo antes de agregarlo al catálogo.
 *
 * **Programa Principal**:
 *
 * - Utilizar los métodos estáticos de **`UtilidadesBiblioteca`** para mejorar la interacción con el usuario al momento de agregar libros.
 * - Demostrar el uso de estos métodos estáticos mostrando un flujo de uso claro, asegúrate que los identificadores únicos son asignados correctamente a los libros.
 *
 * */

interface Elementos{
    val id:Int
    val titulo:String
    val autor:String
    val anoDePublicacion:String
    var tematica:String
    var estado: Estado
}
enum class Estado(desc:String){
    DISPONIBLE ("Disponible"), PRESTADO ("Prestado");
}
data class Libro(override val id:Int, override val titulo:String, override val autor:String, override val anoDePublicacion:String, override var tematica:String, override var estado: Estado = Estado.DISPONIBLE):
    Elementos {
    init {
        require(id < 0){ GestorConsola.errorId("id") }
        require(titulo == ""){ GestorConsola.error("titulo") }
        require(anoDePublicacion == ""){ GestorConsola.error("anoDePublicacion") }
        require(tematica == ""){ GestorConsola.error("tematica") }
        require(autor == ""){ GestorConsola.error("autor") }

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
}

/**
 * meto en un companion object la funcion para que pueda refenrenciarla o llamarala desde afuera
 * me aseguro que primero sumer +1 al contador, para que no haya otro libro igual con ese mismo ID
 * */

class UtilidadesBiblioteca{
    companion object{
        var contador = 0
        /**
         * retorna automaticamente un numero a modo de ID
         * @return el numero ID que es in integer
         * */
        fun generarIdentificadorUnico():Int{
            contador ++
            return contador
        }
    }
}

class GestorBiblioteca(val catalogoDeLibros:MutableList<Elementos>, val registroDePrestamos:MutableList<String>){
    fun agregarUnLibroAlCatalogo(){
        val id = UtilidadesBiblioteca.generarIdentificadorUnico()
        val titulo = GestorConsola.preguntarTitulo()
        val autor = GestorConsola.preguntarAutor()
        val anoDePublicacion= GestorConsola.preguntaranoDePublicacion()
        val tematica = GestorConsola.preguntarTematica()
        val libro = Libro(id,titulo,autor, anoDePublicacion, tematica)

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
        if (libro.estado == Estado.DISPONIBLE){
            GestorConsola.prestarLibro(libro)
            libro.estado = Estado.PRESTADO
        }else{
            GestorConsola.libroPrestado(libro)
        }
    }
    fun devolverUnLibro(libro: Elementos){
        if (libro.estado == Estado.PRESTADO){
            GestorConsola.libroDevuelto(libro)
            libro.estado = Estado.DISPONIBLE
        }else{
            GestorConsola.libronoPrestado(libro)
        }
    }
    fun consultarDisponibilidadDeUnLibro(libro: Elementos){
        val unLibro= catalogoDeLibros.find{it.id == libro.id}
        GestorConsola.disponibilidadDeUnLibro(unLibro)
    }
    fun retornarLosLibrosEnFuncionDeSuEstado(estado: org.pebiblioteca.Estado?){
        if (estado == org.pebiblioteca.Estado.DISPONIBLE){
            GestorConsola.mostraLibros(catalogoDeLibros.filter { it.estado == Estado.DISPONIBLE })
        }else{
            if (estado == org.pebiblioteca.Estado.PRESTADO){
                GestorConsola.mostraLibros(catalogoDeLibros.filter { it.estado == Estado.PRESTADO })
            }else{
                GestorConsola.mostraLibros(catalogoDeLibros)
            }
        }
    }
}