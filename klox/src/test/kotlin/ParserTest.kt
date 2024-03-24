import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.ajcodes.klox.*

class ParserTest {

    @Test
    fun testParserExpression() {
        val tokens = listOf(
            Token(TokenType.NUMBER, "1", 1.0, 1),
            Token(TokenType.PLUS, "+", null, 1),
            Token(TokenType.NUMBER, "2", 2.0, 1),
            Token(TokenType.STAR, "*", null, 1),
            Token(TokenType.NUMBER, "3", 3.0, 1),
            Token(TokenType.EOF, "", null, 2)
        )
        val parser = Parser(tokens)

        val expr = parser.parse()
        assertEquals(expr, Binary(
            left = Literal(1.0),
            operator = Token(TokenType.PLUS, "+", null, 1),
            right = Binary(
                left = Literal(2.0),
                operator = Token(TokenType.STAR, "*", null, 1),
                right = Literal(3.0),
            )
        ))
    }
}