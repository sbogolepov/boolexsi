package parser;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import parser.nodes.*;

import java.io.IOException;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Parser {
    private Lexer lexer;
    private Token currentToken;

    private void takeNext() throws IOException {
            currentToken = lexer.next();
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    private Node parsePrimary(Node parent) throws Exception {
        switch (currentToken.getTokenType()) {
            case TRUE:
                return new Literal(parent, true);
            case FALSE:
                return new Literal(parent, false);
            case ID:
                return parseId(parent);
            case LPAREN:
                takeNext();
                Node expr = parseExpression(parent);
                takeNext();
                if (currentToken.getTokenType() != TokenType.RPAREN) {
                    throw new Exception("expected )");
                }
                return expr;
            default:
                throw new Exception("Unexpected token");
        }
    }

    private Node parseExpression(Node parent) throws Exception {
        Node result = parseTerm(parent);
        if (lexer.peek().getTokenType() == TokenType.OR) {
            BinaryOp op = new BinaryOp(parent, BinaryOp.Type.OR, result, null);
            result.setParent(op);
            takeNext();
            takeNext();
            op.setRightChild(parseExpression(op));
            return op;
        } else {
            return result;
        }
    }

    private Node parseTerm(Node parent) throws Exception {
        Node result = parseFactor(parent);
        if (lexer.peek().getTokenType() == TokenType.AND) {
            BinaryOp op = new BinaryOp(parent, BinaryOp.Type.AND, result, null);
            result.setParent(op);
            takeNext();
            takeNext();
            op.setRightChild(parseTerm(op));
            return op;
        } else {
            return result;
        }
    }

    private Node parseFactor(Node parent) throws Exception {
        if (currentToken.getTokenType() == TokenType.NOT) {
            takeNext();
            Not not = new Not(parent, null);
            not.setChild(parsePrimary(not));
            return not;
        } else {
            return parsePrimary(parent);
        }
    }

    private Id parseId(Node parent) throws Exception {
        if (currentToken.getTokenType() == TokenType.ID) {
            return new Id(parent, currentToken.getValue());
        } else {
            throw new Exception("Unexpected token");
        }
    }

    public Root parse() throws Exception {
        takeNext();
        Root root = new Root(null);
        root.setChild(parseExpression(root));
        return root;
    }
}
