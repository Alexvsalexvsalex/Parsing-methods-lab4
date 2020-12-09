import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StarterGen {
    public static void writeStarter(Path outFolder, String pckg) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s;\n", pckg));
        sb.append("\n");
        sb.append("import java.io.IOException;\n");
        sb.append("import java.nio.file.Files;\n");
        sb.append("import java.nio.file.Path;\n");
        sb.append("\n");
        sb.append("public class Starter {\n");
        sb.append("    public static void main(String[] args) throws IOException {\n");
        sb.append("        if (args.length == 0) {\n");
        sb.append("            throw new RuntimeException(\"Need grammar file as program argument\");\n");
        sb.append("        }\n");
        sb.append("        String input = String.join(\"\\n\", Files.readAllLines(Path.of(args[0])));\n");
        sb.append("        Lexer lexer = new Lexer(input);\n");
        sb.append("        Node node = Parser.getTree(lexer);\n");
        sb.append("    }\n");
        sb.append("}\n");

        Files.writeString(outFolder.resolve("Starter.java"), sb.toString());

        System.out.println("Starter generated successfully");
    }
}
