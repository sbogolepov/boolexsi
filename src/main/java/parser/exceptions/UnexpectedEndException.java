package parser.exceptions;

import lexer.Token;
import lexer.TokenType;
import parser.ParsingExceptionVisitor;
import parser.ParsingException;

import java.util.List;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class UnexpectedEndException extends ParsingException {
    private Token token;
    private final List<TokenType> expectedTokens;

    public UnexpectedEndException(Token currentToken, List<TokenType> expectedTokens) {
        token = currentToken;
        this.expectedTokens = expectedTokens;
    }

    public List<TokenType> getExpectedTokens() {
        return expectedTokens;
    }

    @Override
    public <T> T apply(ParsingExceptionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Token getToken() {
        return token;
    }
}
