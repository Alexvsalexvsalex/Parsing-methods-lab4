import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LexerGen {
    public static void writeLexer(Grammar grammar, Path outFolder, String pckg) throws IOException {
        List<Extra.Pair<String, String>> tokens = new ArrayList<>();
        for (var term : grammar.getSortedTerminals()) {
            String p = grammar.getTerminalRegexps().get(term);
            tokens.add(new Extra.Pair<>(term, p));
        }
        tokens.add(new Extra.Pair<>("END", "$"));
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s;\n", pckg));
        sb.append("\n");
        sb.append("import java.util.List;\n");
        sb.append("\n");
        sb.append("public class Lexer {\n");
        sb.append("    private int curPos = 0;\n");
        sb.append("    private Token currentToken;\n");
        sb.append("    private final String input;\n");
        sb.append("    private final List<Pair<TokenType, String>> exprs = List.of(\n");
        for (int i = 0; i < tokens.size(); i++) {
            sb.append(String.format("            new Pair<>(TokenType.%s, \"%s\")",
                    tokens.get(i).fst, tokens.get(i).snd));
            if (i < tokens.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("    );\n");
        sb.append("\n");
        sb.append("    public Lexer(String input) {\n");
        sb.append("        this.input = input;\n");
        sb.append("        goNext();\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public void goNext() {\n");
        sb.append("        while (curPos < input.length() &&\n");
        sb.append("                Character.isWhitespace(input.charAt(curPos))) {\n");
        sb.append("            ++curPos;\n");
        sb.append("        }\n");
        sb.append("        if (curPos == input.length()) {\n");
        sb.append("            currentToken = new Token(TokenType.END, \"\");\n");
        sb.append("            return;\n");
        sb.append("        }\n");
        sb.append("        for (var p : exprs) {\n");
        sb.append("            for (int i = input.length(); i > curPos; --i) {\n");
        sb.append("                String subS = input.substring(curPos, i);\n");
        sb.append("                if (subS.matches(p.snd)) {\n");
        sb.append("                    curPos = i;\n");
        sb.append("                    currentToken = new Token(p.fst, subS);\n");
        sb.append("                    return;\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("        throw new RuntimeException(\"Can not get next token\");\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public Token getCurrent() {\n");
        sb.append("        return currentToken;\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    static class Pair<A, B>{\n");
        sb.append("        A fst;\n");
        sb.append("        B snd;\n");
        sb.append("        Pair(A a, B b) {\n");
        sb.append("            fst = a;\n");
        sb.append("            snd = b;\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("}\n");

        Files.writeString(outFolder.resolve("Lexer.java"), sb.toString());

        sb = new StringBuilder();
        sb.append(String.format("package %s;\n", pckg));
        sb.append("\n");
        sb.append("public class Token {\n");
        sb.append("    public TokenType type;\n");
        sb.append("    public String str;\n");
        sb.append("    public Token(TokenType type, String str) {\n");
        sb.append("        this.type = type;\n");
        sb.append("        this.str = str;\n");
        sb.append("    }\n");
        sb.append("}\n");
        sb.append("\n");
        sb.append("enum TokenType {\n");

        sb.append("    ");
        for (int i = 0; i < tokens.size(); i++) {
            sb.append(tokens.get(i).fst);
            if (i < tokens.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("\n");

        sb.append("}\n");

        Files.writeString(outFolder.resolve("Token.java"), sb.toString());

        System.out.println("Lexer generated successfully");
    }
}
