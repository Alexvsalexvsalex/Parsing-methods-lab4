package generated.lab2;

public class Token {
    public TokenType type;
    public String str;
    public Token(TokenType type, String str) {
        this.type = type;
        this.str = str;
    }
}

enum TokenType {
    DEF, NAME, LBRACKET, RBRACKET, MULT, COMMA, COLON, EQ, VALUE, END
}
