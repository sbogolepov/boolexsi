package simplifier.errors;

import parser.exceptions.UnexpectedEndException;
import simplifier.Error;

import java.util.stream.Collectors;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public class UnexpectedEndError implements Error {

    private final UnexpectedEndException exception;

    public UnexpectedEndError(UnexpectedEndException exception) {

        this.exception = exception;
    }

    @Override
    public String getMessage() {
        String expectation = exception.getExpectedTokens().stream()
                .map(Enum::toString).collect(Collectors.joining(", "));
        return "Missing token (wanted: " + expectation + ")";
    }

    @Override
    public int startPosition() {
        return 0;
    }

    @Override
    public int endPosition() {
        return 1;
    }
}