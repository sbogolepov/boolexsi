package src.org.stepik.bogolepov.parser.exceptions;

import src.org.stepik.bogolepov.lexer.Token;
import src.org.stepik.bogolepov.parser.ParsingExceptionVisitor;
import src.org.stepik.bogolepov.parser.ParsingException;

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

    @Override
    public <T> T apply(ParsingExceptionVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
