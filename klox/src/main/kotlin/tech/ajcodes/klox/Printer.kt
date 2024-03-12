package tech.ajcodes.klox

import kotlin.math.exp

class AstPrinter : Visitor<String> {

    fun print(expr: Expr?) = expr?.accept(this)

    override fun visitBinaryExpr(expr: Binary): String {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right)
    }

   override fun visitGroupingExpr(expr: Grouping): String {
      return parenthesize("group", expr.expression)
   }

    override fun visitLiteralExpr(expr: Literal): String {
        return expr.value?.toString() ?: "nil"
    }

    override fun visitUnaryExpr(expr: Unary): String {
        return parenthesize(expr.operator.lexeme, expr.right)
    }

    private fun parenthesize(name: String, vararg exprs: Expr): String {
        return buildString {
            append("(").append(name)
            exprs.forEach {
                append(" ")
                append(it.accept(this@AstPrinter))
            }
            append(")")

        }
    }
}

fun main() {
    val expression = Binary(
        Unary(
            Token(TokenType.MINUS, "-", null, 1),
            Literal(123)
        ),
        Token(TokenType.STAR, "*", null, 1),
        Grouping(Literal(45.67))
    )
    println(AstPrinter().print(expression))
}