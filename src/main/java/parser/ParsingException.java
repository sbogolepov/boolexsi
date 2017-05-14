package parser;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public abstract class ParsingException extends Exception {
    public abstract <T> T apply(ParsingExceptionVisitor<T> visitor);
}
