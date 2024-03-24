import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.ajcodes.klox.Scanner
import tech.ajcodes.klox.TokenType

class ScannerTest {
    @Test
    fun testScanTokensForSimpleAdditionExpression() {
        val scanner = Scanner("1 + 2")
        val tokens = scanner.scanTokens()
        assertEquals(4, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].type)
        assertEquals(TokenType.PLUS, tokens[1].type)
        assertEquals(TokenType.NUMBER, tokens[2].type)
        assertEquals(TokenType.EOF, tokens[3].type)
    }

    @Test
    fun testScanTokensForSimpleMultiplicationExpression() {
        val scanner = Scanner("1 * 2")
        val tokens = scanner.scanTokens()
        assertEquals(4, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].type)
        assertEquals(TokenType.STAR, tokens[1].type)
        assertEquals(TokenType.NUMBER, tokens[2].type)
        assertEquals(TokenType.EOF, tokens[3].type)
    }

    @Test
    fun testScanTokensForSimpleSubtractionExpression() {
        val scanner = Scanner("1 - 2")
        val tokens = scanner.scanTokens()
        assertEquals(4, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].type)
        assertEquals(TokenType.MINUS, tokens[1].type)
        assertEquals(TokenType.NUMBER, tokens[2].type)
        assertEquals(TokenType.EOF, tokens[3].type)
    }

    @Test
    fun testScanTokensForSimpleDivisionExpression() {
        val scanner = Scanner("1 / 2")
        val tokens = scanner.scanTokens()
        assertEquals(4, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].type)
        assertEquals(TokenType.SLASH, tokens[1].type)
        assertEquals(TokenType.NUMBER, tokens[2].type)
        assertEquals(TokenType.EOF, tokens[3].type)
    }

    @Test
    fun testScanTokensForSimpleGroupingExpression() {
        val scanner = Scanner("(1 + 2)")
        val tokens = scanner.scanTokens()
        assertEquals(6, tokens.size)
        assertEquals(TokenType.LEFT_PAREN, tokens[0].type)
        assertEquals(TokenType.NUMBER, tokens[1].type)
        assertEquals(TokenType.PLUS, tokens[2].type)
        assertEquals(TokenType.NUMBER, tokens[3].type)
        assertEquals(TokenType.RIGHT_PAREN, tokens[4].type)
        assertEquals(TokenType.EOF, tokens[5].type)
    }

    @Test
    fun testScanTokensForSimpleGroupingExpressionWithMultipleDigits() {
        val scanner = Scanner("(12 + 34)")
        val tokens = scanner.scanTokens()
        assertEquals(6, tokens.size)
        assertEquals(TokenType.LEFT_PAREN, tokens[0].type)
        assertEquals(TokenType.NUMBER, tokens[1].type)
        assertEquals(TokenType.PLUS, tokens[2].type)
        assertEquals(TokenType.NUMBER, tokens[3].type)
        assertEquals(TokenType.RIGHT_PAREN, tokens[4].type)
        assertEquals(TokenType.EOF, tokens[5].type)
    }

    @Test
    fun testScanTokensForSimpleGroupingExpressionWithMultipleDigitsAndSpaces() {
        val scanner = Scanner("( 12 + 34 )")
        val tokens = scanner.scanTokens()
        assertEquals(6, tokens.size)
        assertEquals(TokenType.LEFT_PAREN, tokens[0].type)
        assertEquals(TokenType.NUMBER, tokens[1].type)
        assertEquals(TokenType.PLUS, tokens[2].type)
        assertEquals(TokenType.NUMBER, tokens[3].type)
        assertEquals(TokenType.RIGHT_PAREN, tokens[4].type)
        assertEquals(TokenType.EOF, tokens[5].type)
    }

    @Test
    fun testScanTokensForSimpleGroupingExpressionWithMultipleDigitsAndSpacesAndNewLines() {
        val scanner = Scanner("(\n12 + 34\n)")
        val tokens = scanner.scanTokens()
        assertEquals(6, tokens.size)
        assertEquals(TokenType.LEFT_PAREN, tokens[0].type)
        assertEquals(TokenType.NUMBER, tokens[1].type)
        assertEquals(TokenType.PLUS, tokens[2].type)
        assertEquals(TokenType.NUMBER, tokens[3].type)
        assertEquals(TokenType.RIGHT_PAREN, tokens[4].type)
        assertEquals(TokenType.EOF, tokens[5].type)
    }
}