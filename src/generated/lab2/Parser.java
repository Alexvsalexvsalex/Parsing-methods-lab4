package generated.lab2;

public class Parser {
    public static Node getTree(Lexer lexer) {
        return process_start(lexer, null);
    }

    private static Node process_start(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "start";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case DEF: 
            {
                
                node.ch = new Node[1];
                node.ch[0] = process_definition(lexer, node);
                
                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_defval(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "defval";
        node.par = par;
        switch (lexer.getCurrent().type) {
            default: 
            {
                
                node.ch = new Node[0];
                
                break;
            }
            case EQ: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_EQ(lexer, node);
                node.ch[1] = process_VALUE(lexer, node);
                
                break;
            }
        }
        return node;
    }

    private static Node process_definition(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "definition";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case DEF: 
            {
                
                node.ch = new Node[6];
                node.ch[0] = process_DEF(lexer, node);
                node.ch[1] = process_NAME(lexer, node);
                node.ch[2] = process_LBRACKET(lexer, node);
                node.ch[3] = process_arguments(lexer, node);
                node.ch[4] = process_RBRACKET(lexer, node);
                node.ch[5] = process_EQ(lexer, node);
                
                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_arguments(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "arguments";
        node.par = par;
        switch (lexer.getCurrent().type) {
            default: 
            {
                
                node.ch = new Node[0];
                
                break;
            }
            case MULT: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_MULT(lexer, node);
                node.ch[1] = process_aargs(lexer, node);
                
                break;
            }
            case NAME: 
            {
                
                node.ch = new Node[4];
                node.ch[0] = process_NAME(lexer, node);
                node.ch[1] = process_defval(lexer, node);
                node.ch[2] = process_typedef(lexer, node);
                node.ch[3] = process_argumentss(lexer, node);
                
                break;
            }
        }
        return node;
    }

    private static Node process_aargs(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "aargs";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case MULT: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_MULT(lexer, node);
                node.ch[1] = process_NAME(lexer, node);
                
                break;
            }
            case NAME: 
            {
                
                node.ch = new Node[1];
                node.ch[0] = process_NAME(lexer, node);
                
                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_typedef(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "typedef";
        node.par = par;
        switch (lexer.getCurrent().type) {
            default: 
            {
                
                node.ch = new Node[0];
                
                break;
            }
            case COLON: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_COLON(lexer, node);
                node.ch[1] = process_NAME(lexer, node);
                
                break;
            }
        }
        return node;
    }

    private static Node process_argumentss(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "argumentss";
        node.par = par;
        switch (lexer.getCurrent().type) {
            default: 
            {
                
                node.ch = new Node[0];
                
                break;
            }
            case COMMA: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_COMMA(lexer, node);
                node.ch[1] = process_arguments(lexer, node);
                
                break;
            }
        }
        return node;
    }

    private static Node process_DEF(Lexer lexer, Node par) {
        assert TokenType.DEF == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "DEF";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_NAME(Lexer lexer, Node par) {
        assert TokenType.NAME == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "NAME";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_LBRACKET(Lexer lexer, Node par) {
        assert TokenType.LBRACKET == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "LBRACKET";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_RBRACKET(Lexer lexer, Node par) {
        assert TokenType.RBRACKET == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "RBRACKET";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_MULT(Lexer lexer, Node par) {
        assert TokenType.MULT == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "MULT";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_COMMA(Lexer lexer, Node par) {
        assert TokenType.COMMA == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "COMMA";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_COLON(Lexer lexer, Node par) {
        assert TokenType.COLON == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "COLON";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_EQ(Lexer lexer, Node par) {
        assert TokenType.EQ == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "EQ";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_VALUE(Lexer lexer, Node par) {
        assert TokenType.VALUE == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "VALUE";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

}

class Node {
    Node par = null;
    String str = null;
    String type = null;
    Node[] ch = null;
}
