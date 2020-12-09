import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("Need grammar file as program argument");
        }
        String grammarFile = args[0];
        String pckg = "generated." + grammarFile;

        Path dir = Path.of(String.format("src/%s/", pckg.replace('.', '/')));
        Files.createDirectories(dir);

        Grammar g = new Grammar(grammarFile);
        LexerGen.writeLexer(g, dir, pckg);
        ParserGen.writeParser(g, dir, pckg);
        StarterGen.writeStarter(dir, pckg);
    }
}
