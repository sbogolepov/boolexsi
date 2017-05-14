package parser.exceptions;

import lexer.Token;
import lexer.TokenType;
import parser.ParsingExceptionVisitor;
import parser.ParsingException;

import java.util.List;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class UnexpectedTokenException extends ParsingException {
    private final Token token;
    private final List<TokenType> expectedTokens;

    public UnexpectedTokenException(Token token, List<TokenType> expectedTokens) {
        this.token = token;
        this.expectedTokens = expectedTokens;
    }

    public Token getToken() {
        return token;
    }

    public List<TokenType> getExpectedTokens() {
        return expectedTokens;
    }

    @Override
    public <T> T apply(ParsingExceptionVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
