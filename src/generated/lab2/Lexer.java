package generated.lab2;

import java.util.List;

public class Lexer {
    private int curPos = 0;
    private Token currentToken;
    private final String input;
    private final List<Pair<TokenType, String>> exprs = List.of(
            new Pair<>(TokenType.DEF, "[d][e][f]"),
            new Pair<>(TokenType.NAME, "[a-z]+"),
            new Pair<>(TokenType.LBRACKET, "[(]"),
            new Pair<>(TokenType.RBRACKET, "[)]"),
            new Pair<>(TokenType.MULT, "[*]"),
            new Pair<>(TokenType.COMMA, "[,]"),
            new Pair<>(TokenType.COLON, "[:]"),
            new Pair<>(TokenType.EQ, "[=]"),
            new Pair<>(TokenType.VALUE, "[0-9]+"),
            new Pair<>(TokenType.END, "$")
    );

    public Lexer(String input) {
        this.input = input;
        goNext();
    }

    public void goNext() {
        while (curPos < input.length() &&
                Character.isWhitespace(input.charAt(curPos))) {
            ++curPos;
        }
        if (curPos == input.length()) {
            currentToken = new Token(TokenType.END, "");
            return;
        }
        for (var p : exprs) {
            for (int i = input.length(); i > curPos; --i) {
                String subS = input.substring(curPos, i);
                if (subS.matches(p.snd)) {
                    curPos = i;
                    currentToken = new Token(p.fst, subS);
                    return;
                }
            }
        }
        throw new RuntimeException("Can not get next token");
    }

    public Token getCurrent() {
        return currentToken;
    }

    static class Pair<A, B>{
        A fst;
        B snd;
        Pair(A a, B b) {
            fst = a;
            snd = b;
        }
    }
}
