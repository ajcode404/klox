import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.ajcodes.klox.*


class InterpreterTest {

    @Test
    fun interpreterTest() {
        val interpreter = Interpreter()
        val expr = Binary(
            Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0)),
            Token(TokenType.STAR, "*", null, 1),
            Grouping(Literal(45.67))
        )
        val result = interpreter.evaluate(expr)
        assertEquals(-5617.41, result)
    }

    @Test
    fun testDivision() {
        val interpreter = Interpreter()
        val expr = Binary(
            Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0)),
            Token(TokenType.SLASH, "/", null, 1),
            Grouping(Literal(45.67))
        )
        val result = interpreter.evaluate(expr)
        assertEquals(-2.6932340705058024, result)
    }

    @Test
    fun testAddition() {
        val interpreter = Interpreter()
        val expr = Binary(
            Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0)),
            Token(TokenType.PLUS, "+", null, 1),
            Grouping(Literal(45.67))
        )
        val result = interpreter.evaluate(expr)
        assertEquals(-77.33, result)
    }

    @Test
    fun testSubtraction() {
        val interpreter = Interpreter()
        val expr = Binary(
            Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0)),
            Token(TokenType.MINUS, "-", null, 1),
            Grouping(Literal(45.0))
        )
        val result = interpreter.evaluate(expr)
        assertEquals(-168.0, result)
    }

    @Test
    fun testUnaryOperation() {
        val interpreter = Interpreter()
        val expr = Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0))
        val result = interpreter.evaluate(expr)
        assertEquals(-123.0, result)
    }

    @Test
    fun testGrouping() {
        val interpreter = Interpreter()
        val expr = Grouping(
            Binary(
                Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0)),
                Token(TokenType.STAR, "*", null, 1),
                Grouping(Literal(45.67))
            )
        )
        val result = interpreter.evaluate(expr)
        assertEquals(-5617.41, result)
    }

    @Test
    fun testMultipleOperationAtOnce() {
        val interpreter = Interpreter()
        val expr = Binary(
            Binary(
                Unary(Token(TokenType.MINUS, "-", null, 1), Literal(123.0)),
                Token(TokenType.STAR, "*", null, 1),
                Grouping(Literal(45.67))
            ),
            Token(TokenType.PLUS, "+", null, 1),
            Grouping(Literal(45.67))
        )
        val result = interpreter.evaluate(expr)
        assertEquals(-5571.74, result)
    }
}