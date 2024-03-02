package tech.ajcodes.klox

class Scanner(
    private val source: String,
) {

    private val tokens = mutableListOf<Token>()
    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<Token> {
        while (!isAtTheEnd()) {
            start = current
            scanToken()
        }
        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        when (val c = advance()) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> if (match('/')) {
                if (!isAtTheEnd()) {
                    advance()
                }
            } else {
                addToken(TokenType.STAR)
            }

            // operators
            '!' -> addToken((if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG))
            '=' -> addToken((if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL))
            '<' -> addToken((if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS))
            '>' -> addToken((if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER))

            '/' -> if (match('/')) {
                while (peek() != '\n' && !isAtTheEnd()) advance()
            } else if (match('*')) {
                while (peek() != '*' && !isAtTheEnd()) {
                    if (peek() == '\n') line++
                    advance()
                }
            } else {
                addToken(TokenType.SLASH)
            }

            ' ' -> {}
            '\r' -> {}
            '\t' -> {}
            '\n' -> line++

            '"' -> string()

            else -> {
                if (isDigit(c)) {
                    number()
                } else if (isAlpha(c)) {
                    identifier()
                } else {
                    error(line, "Unexpected character.")
                }
            }
        }
    }

    private fun match(expected: Char): Boolean = when {
        isAtTheEnd() -> false
        source[current] != expected -> false
        else -> {
            current++
            true
        }
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()
        val text = source.substring(start, current)
        val type: TokenType = keywords[text] ?: TokenType.IDENTIFIER
        addToken(type)
    }

    private fun peekNext(): Char {
        if (current + 1 >= source.length) return Char.MIN_VALUE
        return source[current + 1]
    }

    private fun number() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peekNext())) {
            advance()
            while (isDigit(peek())) advance()
        }
        addToken(
            TokenType.NUMBER,
            source.substring(start, current).toDouble(),
        )
    }

    private fun string() {
        while (peek() != '"' && !isAtTheEnd()) {
            if (peek() == '\n') line++
            advance()
        }
        if (isAtTheEnd()) {
            error(line, "Unterminated string.")
            return
        }

        // the closing ".
        advance()

        // trim the surrounding quotes.
        val value = source.substring(start + 1, current - 1)
        addToken(TokenType.STRING, value)
    }

    private fun peek(): Char {
        if (isAtTheEnd()) return Char.MIN_VALUE
        return source[current]
    }

    private fun advance(): Char = source[current++]

    private fun addToken(type: TokenType, literal: Any? = null) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

    private fun isAtTheEnd(): Boolean = current >= source.length
}

private fun isAlphaNumeric(c: Char): Boolean = isAlpha(c) || isDigit(c)

private fun isDigit(c: Char): Boolean = c in '0'..'9'

private fun isAlpha(c: Char): Boolean =
    (c in 'a'..'z') ||
        (c in 'A'..'Z') ||
        c == '_'

private val keywords = mapOf(
    "or" to TokenType.OR,
    "and" to TokenType.AND,
    "class" to TokenType.CLASS,
    "else" to TokenType.ELSE,
    "false" to TokenType.FALSE,
    "for" to TokenType.FOR,
    "fun" to TokenType.FUN,
    "if" to TokenType.IF,
    "nil" to TokenType.NIL,
    "or" to TokenType.OR,
    "print" to TokenType.PRINT,
    "return" to TokenType.RETURN,
    "super" to TokenType.SUPER,
    "this" to TokenType.THIS,
    "true" to TokenType.TRUE,
    "var" to TokenType.VAR,
    "while" to TokenType.WHILE,
)
