import java.io.IOException
import java.nio.file.*

/**
 * @author Diego Arteaga
 */


/**
 * Esta funcion varifica si existe el documento en un directorio con un nombre especifico
 * y si no, lo crea
 *
 * @return El archivo del tipo Path
 */
fun roomServie():Path{
    val fileName = "textos.txt"
    val directoryPath = "src/data/"
    val file: Path = Paths.get(directoryPath, fileName)

    if (!Files.exists(file)) {
        try {
            Files.createFile(file)
            println("Archivo creado correctamente.")
        } catch (e: IOException) {
            println("Error al crear el archivo: ${e.message}")
        }
    }
    return file
}

/**
 * Un menu que muestra las opciones al usuario
 *
 * @param file El documento para trabajar
 */
fun menu(file:Path){
    var option: Int
    do {
        println("\nMENU:")
        println("1. Insertar una línea al comienzo del archivo")
        println("2. Escribir frases al final del archivo")
        println("3. Mostrar el contenido de una línea")
        println("4. Salir")
        print("Ingrese su opción: ")
        option = readLine()?.toIntOrNull() ?: 0

        when (option) {
            1 -> insertLineAtBeginning(file)
            2 -> writeLinesToEnd(file)
            3 -> showLineContent(file)
            4 -> println("Saliendo del programa...")
            else -> println("Opción no válida, por favor ingrese un número del 1 al 4.")
        }
    } while (option != 4)
}

/**
 * Este prodecimiento inserta un registro al principio del archivo manejando la informacion
 * con listas
 *
 * @param file el docmuento
 */
fun insertLineAtBeginning(file: Path) {
    println("\nIngrese la línea que desea agregar al comienzo del archivo:")
    val input = readLine() ?: ""
    try {
        val lines = mutableListOf<String>()
        lines.add(input)
        Files.readAllLines(file).forEach { lines.add(it) }
        Files.write(file, lines)
        println("Línea añadida al inicio del archivo.")
    } catch (e: IOException) {
        println("Error al escribir en el archivo: ${e.message}")
    }
}

/**
 * Este prcedimiento escribe las frases del usuario y puede borrar contenido del archivo
 *
 * @param file el docmmuento a tratar
 */

fun writeLinesToEnd(file: Path) {
    println("\nEscribe las frases que deseas añadir al final del archivo (presiona Enter para agregar la frase al archivo y una cadena vacia apra finalizar la escritura) \n e ingresa \"@BORRAR\" para borrar el contenido del archivo:")
    var input = readLine()
    while (input != null && input.isNotEmpty()) {
        if (input.trim() == "@BORRAR") {
            deleteFile(file)
            return
        }
        try {
            Files.newBufferedWriter(file, StandardOpenOption.APPEND).use { writer ->
                writer.write("$input\n")
            }
            println("Frases añadidas al final del archivo.")
        } catch (e: IOException) {
            println("Error al escribir en el archivo: ${e.message}")
        }
        input = readLine()
    }
}
fun showLineContent(file: Path) {
    println("\nIngrese el número de línea que desea mostrar:")
    val lineNumber = readLine()?.toIntOrNull()
    if (lineNumber != null) {
        try {
            val lines = Files.readAllLines(file)
            if (lineNumber in 1..lines.size) {
                println("Contenido de la línea $lineNumber: ${lines[lineNumber - 1]}")
            } else {
                println("El número de línea ingresado está fuera de rango.")
            }
        } catch (e: IOException) {
            println("Error al leer el archivo: ${e.message}")
        }
    } else {
        println("Entrada no válida para el número de línea.")
    }
}

/**
 * Este procedimiento se encarga de borrar contenido del archivo
 *
 * @param file el docmmuento a tratar
 */
fun deleteFile(file: Path) {
    try {
        Files.deleteIfExists(file)
        println("Archivo borrado correctamente.")
    } catch (e: IOException) {
        println("Error al borrar el archivo: ${e.message}")
    }
}