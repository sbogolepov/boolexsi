package parser;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import node.Node;
import parser.exceptions.LexicalException;
import parser.exceptions.UnexpectedEndException;
import parser.exceptions.UnexpectedTokenException;
import node.nodes.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    private final List<TokenType> primaryTokenTypes;
    private final List<TokenType> factorTokenTypes;
    private final List<TokenType> expressionTokenTypes;
    private final List<TokenType> termTokenTypes;

    public Parser(Lexer lexer) {
        this.lexer = lexer;

        primaryTokenTypes = Arrays.asList(TokenType.ID, TokenType.FALSE, TokenType.TRUE, TokenType.LPAREN);
        factorTokenTypes = new ArrayList<>(primaryTokenTypes);
        factorTokenTypes.add(TokenType.NOT);
        termTokenTypes = new ArrayList<>(factorTokenTypes);
        expressionTokenTypes = new ArrayList<>(termTokenTypes);
    }

    private void takeNext(List<TokenType> expectedTokens) throws IOException, ParsingException {
        currentToken = lexer.next();
        if (currentToken.getTokenType() == TokenType.EOF) {
            throw new UnexpectedEndException(currentToken, new ArrayList<>(expectedTokens));
        }
        if (currentToken.getTokenType() == TokenType.ERROR) {
            throw new LexicalException(currentToken);
        }
        if (expectedTokens != null && !expectedTokens.contains(currentToken.getTokenType())) {
            throw new UnexpectedTokenException(currentToken, expectedTokens);
        }
    }

    private Node parsePrimary(Node parent) throws IOException, ParsingException {
        switch (currentToken.getTokenType()) {
            case TRUE:
                return new Literal(parent, true);
            case FALSE:
                return new Literal(parent, false);
            case ID:
                return new Id(parent, currentToken.getValue());
            case LPAREN:
                Parens parens = new Parens(parent, null);
                takeNext(expressionTokenTypes);
                parens.setChild(parseExpression(parens));
                takeNext(Collections.singletonList(TokenType.RPAREN));
                return parens;
            default:
                throw new UnexpectedTokenException(currentToken, primaryTokenTypes);
        }
    }

    private Node parseExpression(Node parent) throws IOException, ParsingException {
        Node result = parseTerm(parent);
        if (lexer.peek().getTokenType() == TokenType.OR) {
            BinaryOp op = new BinaryOp(parent, BinaryOp.Type.OR, result, null);
            result.setParent(op);
            takeNext(Collections.singletonList(TokenType.OR));
            takeNext(expressionTokenTypes);
            op.setRightChild(parseExpression(op));
            return op;
        } else {
            return result;
        }
    }

    private Node parseTerm(Node parent) throws IOException, ParsingException {
        Node result = parseFactor(parent);
        if (lexer.peek().getTokenType() == TokenType.AND) {
            BinaryOp op = new BinaryOp(parent, BinaryOp.Type.AND, result, null);
            result.setParent(op);
            takeNext(Collections.singletonList(TokenType.AND));
            takeNext(termTokenTypes);
            op.setRightChild(parseTerm(op));
            return op;
        } else {
            return result;
        }
    }

    private Node parseFactor(Node parent) throws IOException, ParsingException {
        if (currentToken.getTokenType() == TokenType.NOT) {
            takeNext(primaryTokenTypes);
            Not not = new Not(parent, null);
            not.setChild(parsePrimary(not));
            return not;
        } else {
            return parsePrimary(parent);
        }
    }

    public Root parse() throws IOException, ParsingException {
        Root root = new Root(null);
        takeNext(expressionTokenTypes);
        root.setChild(parseExpression(root));
        if (lexer.peek().getTokenType() != null && lexer.peek().getTokenType() != TokenType.EOF) {
            takeNext(null);
            throw new UnexpectedTokenException(currentToken, Arrays.asList(TokenType.AND, TokenType.OR));
        }
        return root;
    }
}
