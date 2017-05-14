package simplifier.errors;

import lexer.Token;
import parser.exceptions.LexicalException;
import simplifier.Error;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public class LexicalError implements Error {

    private final Token token;

    public LexicalError(LexicalException exception) {
        token = exception.getToken();
    }

    @Override
    public String getMessage() {
        return "Cannot parse input fragment";
    }

    @Override
    public int startPosition() {
        return token.getPosition();
    }

    @Override
    public int endPosition() {
        return token.getPosition() + token.getValue().length();
    }
}
