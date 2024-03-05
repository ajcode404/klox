package tech.ajcodes.klox

// error handling
fun error(line: Int, message: String) {
    report(line, "", message)
}

fun error(token: Token, message: String) {
    if (token.type == TokenType.EOF) {
        report(token.line, " at end", message)
    } else {
        report(token.line, " at '${token.lexeme}'", message)
    }
}

fun report(line: Int, where: String, message: String) {
    System.err.println("[line $line] Error $where: $message")
    hadError = true
}
