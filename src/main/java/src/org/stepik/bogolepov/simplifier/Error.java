package src.org.stepik.bogolepov.simplifier;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public interface Error {
    String getMessage();

    int startPosition();

    int endPosition();
}
