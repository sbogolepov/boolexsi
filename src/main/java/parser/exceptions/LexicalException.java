package parser.exceptions;

import lexer.Token;
import parser.ParsingException;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public class LexicalException extends ParsingException {
    private final Token token;

    public LexicalException(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
