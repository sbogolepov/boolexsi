package parser;

import parser.exceptions.LexicalException;
import parser.exceptions.UnexpectedEndException;
import parser.exceptions.UnexpectedTokenException;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public interface ParsingExceptionVisitor<T> {
    T visit(LexicalException e);

    T visit(UnexpectedEndException e);

    T visit(UnexpectedTokenException e);
}
