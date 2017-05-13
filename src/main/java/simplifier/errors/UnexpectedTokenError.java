package simplifier.errors;

import parser.exceptions.UnexpectedTokenException;
import simplifier.Error;

import java.util.stream.Collectors;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class UnexpectedTokenError implements Error {

    private UnexpectedTokenException exception;

    public UnexpectedTokenError(UnexpectedTokenException exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        String expectation = exception.getExpectedTokens().stream()
                .map(Enum::toString).collect(Collectors.joining(", "));
        String got = exception.getToken().getValue();
        return "Unexpected token (wanted: " + expectation + "; got: " + got + ")";
    }

    @Override
    public int startPosition() {
        return exception.getToken().getPosition();
    }

    @Override
    public int endPosition() {
        return exception.getToken().getPosition() + exception.getToken().getValue().length();
    }
}
