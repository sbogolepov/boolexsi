package src.org.stepik.bogolepov.simplifier.errors;

import src.org.stepik.bogolepov.parser.exceptions.UnexpectedEndException;
import src.org.stepik.bogolepov.simplifier.Error;

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
        return exception.getToken().getPosition();
    }

    @Override
    public int endPosition() {
        return exception.getToken().getPosition() + 1;
    }
}
