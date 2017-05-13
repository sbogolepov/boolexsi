package parser.exceptions;

import lexer.Token;
import lexer.TokenType;
import parser.ParsingException;

import java.util.List;
import java.util.Set;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class UnexpectedEndException extends ParsingException {
    private final List<TokenType> expectedTokens;

    public UnexpectedEndException(List<TokenType> expectedTokens) {
        this.expectedTokens = expectedTokens;
    }

    public List<TokenType> getExpectedTokens() {
        return expectedTokens;
    }
}
