package generated.calculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Starter {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("Need grammar file as program argument");
        }
        String input = String.join("\n", Files.readAllLines(Path.of(args[0])));
        Lexer lexer = new Lexer(input);
        Node node = Parser.getTree(lexer);
    }
}
