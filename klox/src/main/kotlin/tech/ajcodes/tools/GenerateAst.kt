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
        "Literal  - val value: Any",
        "Unary    - val operator: Token ,val right: Expr"
    ))
}

fun defineAst(outputDir: String, baseName: String, types: Array<String>) {
    val path = "$outputDir/$baseName.kt"
    val writer = PrintWriter(path, "UTF-8")

    writer.println("package tech.ajcodes.klox")
    writer.println()
    writer.println("sealed class $baseName {")
    types.forEach {
        val className = it.split("-")[0].trim()
        val fields = it.split("-")[1].trim()
        writer.println("\tdata class $className($fields): $baseName()")
    }
    writer.println("}")
    writer.println()
    writer.close()
}
