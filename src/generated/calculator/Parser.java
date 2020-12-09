package generated.calculator;

public class Parser {
    public static Node getTree(Lexer lexer) {
        return process_start(lexer, null);
    }

    private static Node process_t(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "t";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case LBRACKET: 
            case VALUE: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_f(lexer, node);
                node.ch[1] = process_ts(lexer, node);
                node.val = node.ch[0].val * node.ch[1].val;

                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_e(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "e";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case LBRACKET: 
            case VALUE: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_t(lexer, node);
                node.ch[1] = process_es(lexer, node);
                node.val = node.ch[0].val + node.ch[1].val;

                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_f(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "f";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case LBRACKET: 
            {
                
                node.ch = new Node[4];
                node.ch[0] = process_LBRACKET(lexer, node);
                node.ch[1] = process_e(lexer, node);
                node.ch[2] = process_RBRACKET(lexer, node);
                node.ch[3] = process_pow(lexer, node);
                node.val = Math.pow(node.ch[1].val, node.ch[3].val);

                break;
            }
            case VALUE: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_VALUE(lexer, node);
                node.ch[1] = process_pow(lexer, node);
                node.val = Math.pow(node.ch[0].val, node.ch[1].val);

                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_start(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "start";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case LBRACKET: 
            case VALUE: 
            {
                
                node.ch = new Node[1];
                node.ch[0] = process_e(lexer, node);
                node.val = node.ch[0].val;

                break;
            }
            default: {
                throw new RuntimeException("Unexpected token");
            }
        }
        return node;
    }

    private static Node process_pow(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "pow";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case POW: 
            {
                
                node.ch = new Node[2];
                node.ch[0] = process_POW(lexer, node);
                node.ch[1] = process_f(lexer, node);
                node.val = node.ch[1].val;

                break;
            }
            default: 
            {
                
                node.ch = new Node[0];
                node.val = 1.0;

                break;
            }
        }
        return node;
    }

    private static Node process_es(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "es";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case PLUS: 
            {
                
                node.ch = new Node[3];
                node.ch[0] = process_PLUS(lexer, node);
                node.ch[1] = process_t(lexer, node);
                node.ch[2] = process_es(lexer, node);
                node.val = node.ch[1].val + node.ch[2].val;

                break;
            }
            case MINUS: 
            {
                
                node.ch = new Node[3];
                node.ch[0] = process_MINUS(lexer, node);
                node.ch[1] = process_t(lexer, node);
                node.ch[2] = process_es(lexer, node);
                node.val = -node.ch[1].val + node.ch[2].val;

                break;
            }
            default: 
            {
                
                node.ch = new Node[0];
                node.val = 0.0;

                break;
            }
        }
        return node;
    }

    private static Node process_ts(Lexer lexer, Node par) {
        Node node = new Node();
        node.type = "ts";
        node.par = par;
        switch (lexer.getCurrent().type) {
            case MULT: 
            {
                
                node.ch = new Node[3];
                node.ch[0] = process_MULT(lexer, node);
                node.ch[1] = process_f(lexer, node);
                node.ch[2] = process_ts(lexer, node);
                node.val = node.ch[1].val * node.ch[2].val;

                break;
            }
            case DIV: 
            {
                
                node.ch = new Node[3];
                node.ch[0] = process_DIV(lexer, node);
                node.ch[1] = process_f(lexer, node);
                node.ch[2] = process_ts(lexer, node);
                node.val = 1 / node.ch[1].val * node.ch[2].val;

                break;
            }
            default: 
            {
                
                node.ch = new Node[0];
                node.val = 1.0;

                break;
            }
        }
        return node;
    }

    private static Node process_VALUE(Lexer lexer, Node par) {
        assert TokenType.VALUE == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "VALUE";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        node.val = Double.parseDouble(node.str);

        lexer.goNext();
        return node;
    }

    private static Node process_PLUS(Lexer lexer, Node par) {
        assert TokenType.PLUS == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "PLUS";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_MINUS(Lexer lexer, Node par) {
        assert TokenType.MINUS == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "MINUS";
        node.par = par;
        node.str = lexer.getCurrent().str;
        
        
        lexer.goNext();
        return node;
    }

    private static Node process_POW(Lexer lexer, Node par) {
        assert TokenType.POW == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "POW";
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

    private static Node process_DIV(Lexer lexer, Node par) {
        assert TokenType.DIV == lexer.getCurrent().type;
        Node node = new Node();
        node.type = "DIV";
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

}

class Node {
    Double val = null;
    Node par = null;
    String str = null;
    String type = null;
    Node[] ch = null;
}
