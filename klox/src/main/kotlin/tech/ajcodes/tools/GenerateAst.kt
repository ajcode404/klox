package tech.ajcodes.tools

import java.io.PrintWriter
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Usage: generate_ast <output_directory>")
        exitProcess(64)
    }
    val outputDir = args[0]
    defineAst(outputDir, "Expr", arrayOf(
        "Binary   - val left: Expr,val operator: Token ,val right: Expr",
        "Grouping - val expression: Expr",
        "Literal  - val value: Any?",
        "Unary    - val operator: Token ,val right: Expr",
    ))
    defineAst(outputDir, "Stmt", arrayOf(
        "Expression - val expression: Expr",
        "Print      - val expression: Expr",
    ))
}

fun defineAst(outputDir: String, baseName: String, types: Array<String>) {
    val path = "$outputDir/$baseName.kt"
    val writer = PrintWriter(path, "UTF-8")

    writer.println("package tech.ajcodes.klox")
    writer.println()
    writer.println("sealed class $baseName {")
    // the base accept() method.
    writer.println()
    writer.println("\tabstract fun <R> accept(visitor: ${baseName}Visitor<R>): R?")

    writer.println("}")

    writer.println()
    // define visitor
    defineVisitor(writer, baseName, types)

    // define type
    types.forEach {
        val className = it.split("-")[0].trim()
        val fields = it.split("-")[1].trim()
        """
            
            data class $className($fields): $baseName() {
                override fun <R> accept(visitor: ${baseName}Visitor<R>): R? {
                    return visitor.visit$className$baseName(this)
                }
            }
        """.trimIndent().let {
            writer.println(it)
        }
    }
    writer.println()
    writer.close()
}



fun defineVisitor(writer: PrintWriter, baseName: String, types: Array<String>) {
    writer.println("interface ${baseName}Visitor<R> {")
    types.forEach {
        val typeName = it.split("-")[0].trim()
        writer.println("\tfun visit$typeName$baseName(${baseName.lowercase()}: $typeName): R?")
    }
    writer.println("}")
}


