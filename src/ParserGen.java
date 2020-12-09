import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ParserGen {
    public static void writeParser(Grammar grammar, Path outFolder, String pckg) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s;\n", pckg));
        sb.append("\n");
        sb.append("public class Parser {\n");
        sb.append("    public static Node getTree(Lexer lexer) {\n");
        sb.append("        return process_start(lexer, null);\n");
        sb.append("    }\n");
        sb.append("\n");
        for (String name : grammar.getNonTerminals().keySet()) {
            sb.append(String.format("    private static Node process_%s(Lexer lexer, Node par) {\n", name));
            sb.append("        Node node = new Node();\n");
            sb.append(String.format("        node.type = \"%s\";\n", name));
            sb.append("        node.par = par;\n");
            sb.append("        switch (lexer.getCurrent().type) {\n");
            List<List<String>> rules = grammar.getNonTerminals().get(name);
            List<String> synthesisCodes = grammar.getSynthesisCodes().get(name);
            List<String> parentCodes = grammar.getParentCodes().get(name);
            boolean hasDefault = false;
            for (int i = 0; i < rules.size(); i++) {
                List<String> seq = rules.get(i);
                String synthesisCode = synthesisCodes.get(i);
                String parentCode = parentCodes.get(i);
                for (String fst : grammar.getFirst().get(name).get(i)) {
                    if (!"".equals(fst)) {
                        sb.append(String.format("            case %s: \n", fst));
                    } else {
                        hasDefault = true;
                        sb.append("            default: \n");
                    }
                }
                sb.append("            {\n");
                sb.append(String.format("                %s\n", parentCode));
                sb.append(String.format("                node.ch = new Node[%d];\n", seq.size()));
                for (int k = 0; k < seq.size(); ++k) {
                    sb.append(String.format("                node.ch[%d] = process_%s(lexer, node);\n", k, seq.get(k)));
                }
                sb.append(String.format("                %s\n", synthesisCode));
                sb.append("                break;\n");
                sb.append("            }\n");
            }
            if (!hasDefault) {
                sb.append("            default: {\n");
                sb.append("                throw new RuntimeException(\"Unexpected token\");\n");
                sb.append("            }\n");
            }
            sb.append("        }\n");
            sb.append("        return node;\n");
            sb.append("    }\n");
            sb.append("\n");
        }
        for (String name : grammar.getSortedTerminals()) {
            sb.append(String.format("    private static Node process_%s(Lexer lexer, Node par) {\n", name));
            sb.append(String.format("        assert TokenType.%s == lexer.getCurrent().type;\n", name));
            sb.append("        Node node = new Node();\n");
            sb.append(String.format("        node.type = \"%s\";\n", name));
            sb.append("        node.par = par;\n");
            sb.append("        node.str = lexer.getCurrent().str;\n");
            String synthesisCode = grammar.getSynthesisCodes().get(name).get(0);
            String parentCode = grammar.getParentCodes().get(name).get(0);
            sb.append(String.format("        %s\n", parentCode));
            sb.append(String.format("        %s\n", synthesisCode));
            sb.append("        lexer.goNext();\n");
            sb.append("        return node;\n");
            sb.append("    }\n");
            sb.append("\n");
        }
        sb.append("}\n");
        sb.append("\n");
        sb.append("class Node {\n");
        for (String field : grammar.getFields()) {
            sb.append(String.format("    %s = null;\n", field));
        }
        sb.append("    Node par = null;\n");
        sb.append("    String str = null;\n");
        sb.append("    String type = null;\n");
        sb.append("    Node[] ch = null;\n");
        sb.append("}\n");

        Files.writeString(outFolder.resolve("Parser.java"), sb.toString());

        System.out.println("Parser generated successfully");
    }
}
