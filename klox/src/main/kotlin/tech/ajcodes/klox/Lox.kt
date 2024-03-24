package tech.ajcodes.klox

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

var hadError = false
var hadRuntimeError = false

val interpreter = Interpreter()

fun main(args: Array<String>) {
    when {
        args.size > 1 -> {
            println("Usage: klox [script]")
            exitProcess(64)
        }
        args.size == 1 -> runFile(args[0])
        else -> runPrompt()
    }
}

fun runFile(path: String) {
    val bytes = Files.readAllBytes(Paths.get(path))
    run(String(bytes, Charset.defaultCharset()))
    if (hadError) exitProcess(65)
    if (hadRuntimeError) exitProcess(70)
}

fun runPrompt() {
    val input = InputStreamReader(System.`in`)
    val reader = BufferedReader(input)
    while (true) {
        print("> ")
        reader.readLine()?.let { line ->
            run(line)
            hadError = false
        } ?: break
    }
}

fun run(source: String) {
    val tokens = Scanner(source).scanTokens()
    val parser = Parser(tokens)
    val expr = parser.parse()
    if (hadError) return
    interpreter.interpret(expr)
    println(AstPrinter().print(expr))
}

