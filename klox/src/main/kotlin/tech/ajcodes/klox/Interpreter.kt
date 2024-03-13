package tech.ajcodes.klox

import kotlin.math.exp

class RuntimeError(val token: Token, message: String) : RuntimeException(message)

class Interpreter : Visitor<Any> {

    fun interpret(expression: Expr?) {
        try {
            val value = evaluate(expression)
            println(stringify(value))
        } catch (e: RuntimeError) {
            runtimeError(e)
        }
    }

    private fun stringify(obj: Any?): String {
        return when (obj) {
            null -> return "nil"
            is Double -> {
                var text = obj.toString()
                if (text.endsWith(".0")) {
                    text = text.substring(0, text.length - 2)
                }
                text
            }
            else -> obj.toString()
        }
    }

    override fun visitBinaryExpr(expr: Binary): Any? {
        val left = evaluate(expr.left)
        val right = evaluate(expr.right)
        return when(expr.operator.type) {

            TokenType.GREATER -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) > (right as Double)
            }
            TokenType.GREATER_EQUAL -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) >= (right as Double)
            }
            TokenType.LESS -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) < (right as Double)
            }
            TokenType.LESS_EQUAL -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) <= (right as Double)
            }

            TokenType.MINUS -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) - (right as Double)
            }
            TokenType.PLUS -> if (left is Double && right is Double) {
                left + right
            } else if (left is String && right is String)  {
                left + right
            } else {
                throw RuntimeError(expr.operator, "Operands must be two numbers or two strings.")
            }
            TokenType.SLASH -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) / (right as Double)
            }
            TokenType.STAR -> {
                checkNumberOperand(expr.operator, left, right)
                (left as Double) * (right as Double)
            }

            TokenType.BANG_EQUAL -> !isEqual(left, right)
            TokenType.EQUAL_EQUAL -> isEqual(left, right)
            // Unreachable
            else -> null
        }
    }

    override fun visitGroupingExpr(expr: Grouping): Any? {
        return evaluate(expr.expression)
    }
    override fun visitLiteralExpr(expr: Literal): Any? {
        return expr.value
    }

    override fun visitUnaryExpr(expr: Unary): Any? {
        val right = evaluate(expr.right)

        return when(expr.operator.type) {
            TokenType.MINUS -> {
                checkNumberOperand(expr.operator, right)
                ((right as Double) * -1)
            }
            TokenType.BANG -> !isTruthy(right)
            else ->  null
        }
    }

    private fun checkNumberOperand(operator: Token, operand: Any?) {
        if (operand is Double) return
        throw RuntimeError(operator, "Operand must be a number.")
    }

    private fun checkNumberOperand(operator: Token, left: Any?, right: Any?) {
        if (left is Double && right is Double) return
        throw RuntimeError(operator, "Operands must be a numbers.")
    }

    private fun evaluate(expression: Expr?): Any? {
        return expression?.accept(this)
    }

    private fun isTruthy(obj: Any?): Boolean = when (obj) {
        null -> false
        is Boolean -> obj
        else -> true
    }

    private fun isEqual(a: Any?, b: Any?): Boolean = when {
        a == null && b == null -> true
        a == null -> false
        else -> a == b
    }
}