package parser.exceptions;

import lexer.Token;
import lexer.TokenType;
import parser.ParsingException;

import java.util.List;
import java.util.Set;

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


}
