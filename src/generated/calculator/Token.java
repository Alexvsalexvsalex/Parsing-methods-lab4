package generated.calculator;

public class Token {
    public TokenType type;
    public String str;
    public Token(TokenType type, String str) {
        this.type = type;
        this.str = str;
    }
}

enum TokenType {
    VALUE, PLUS, MINUS, POW, MULT, DIV, LBRACKET, RBRACKET, END
}
