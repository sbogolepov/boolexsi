package src.org.stepik.bogolepov.parser;

import src.org.stepik.bogolepov.parser.exceptions.LexicalException;
import src.org.stepik.bogolepov.parser.exceptions.UnexpectedEndException;
import src.org.stepik.bogolepov.parser.exceptions.UnexpectedTokenException;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public interface ParsingExceptionVisitor<T> {
    T visit(LexicalException e);

    T visit(UnexpectedEndException e);

    T visit(UnexpectedTokenException e);
}
