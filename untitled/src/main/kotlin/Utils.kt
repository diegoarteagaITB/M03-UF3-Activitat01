import java.io.IOException
import java.nio.file.*



fun roomServie():Path{
    val fileName = "textos.txt"
    val directoryPath = "src/data/"
    val filePath: Path = Paths.get(directoryPath, fileName)

    if (!Files.exists(filePath)) {
        try {
            Files.createFile(filePath)
            println("Archivo creado correctamente.")
        } catch (e: IOException) {
            println("Error al crear el archivo: ${e.message}")
        }
    }
    return filePath
}

fun menu(filePath:Path){
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
            1 -> insertLineAtBeginning(filePath)
            2 -> writeLinesToEnd(filePath)
            3 -> showLineContent(filePath)
            4 -> println("Saliendo del programa...")
            else -> println("Opción no válida, por favor ingrese un número del 1 al 4.")
        }
    } while (option != 4)
}

fun insertLineAtBeginning(filePath: Path) {
    println("\nIngrese la línea que desea agregar al comienzo del archivo:")
    val input = readLine() ?: ""
    try {
        val lines = mutableListOf<String>()
        lines.add(input)
        Files.readAllLines(filePath).forEach { lines.add(it) }
        Files.write(filePath, lines)
        println("Línea añadida al inicio del archivo.")
    } catch (e: IOException) {
        println("Error al escribir en el archivo: ${e.message}")
    }
}







fun writeLinesToEnd(filePath: Path) {
    println("\nEscribe las frases que deseas añadir al final del archivo (presiona Enter para agregar la frase al archivo y una cadena vacia apra finalizar la escritura) \n e ingresa \"@BORRAR\" para borrar el contenido del archivo:")
    var input = readLine()
    while (input != null && input.isNotEmpty()) {
        if (input.trim() == "@BORRAR") {
            deleteFile(filePath)
            return
        }
        try {
            Files.newBufferedWriter(filePath, StandardOpenOption.APPEND).use { writer ->
                writer.write("$input\n")
            }
            println("Frases añadidas al final del archivo.")
        } catch (e: IOException) {
            println("Error al escribir en el archivo: ${e.message}")
        }
        input = readLine()
    }
}
fun showLineContent(filePath: Path) {
    println("\nIngrese el número de línea que desea mostrar:")
    val lineNumber = readLine()?.toIntOrNull()
    if (lineNumber != null) {
        try {
            val lines = Files.readAllLines(filePath)
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

fun deleteFile(filePath: Path) {
    try {
        Files.deleteIfExists(filePath)
        println("Archivo borrado correctamente.")
    } catch (e: IOException) {
        println("Error al borrar el archivo: ${e.message}")
    }
}