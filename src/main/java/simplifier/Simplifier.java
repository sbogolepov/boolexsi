package simplifier;

import lexer.Lexer;
import optimizer.OptimizationStrategy;
import optimizer.Optimizer;
import node.Node;
import parser.Parser;
import parser.ParsingException;
import parser.ParsingExceptionVisitor;
import parser.exceptions.LexicalException;
import parser.exceptions.UnexpectedEndException;
import parser.exceptions.UnexpectedTokenException;
import simplifier.errors.LexicalError;
import simplifier.errors.UnexpectedEndError;
import simplifier.errors.UnexpectedTokenError;

import java.io.IOException;
import java.io.StringReader;
import java.lang.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class Simplifier {

    private final Optimizer optimizer;

    private ParsingExceptionVisitor<Error> exceptionVisitor = new ParsingExceptionVisitor<Error>() {
        @Override
        public Error visit(LexicalException e) {
            return new LexicalError(e);
        }

        @Override
        public Error visit(UnexpectedEndException e) {
            return new UnexpectedEndError(e);
        }

        @Override
        public Error visit(UnexpectedTokenException e) {
            return new UnexpectedTokenError(e);
        }
    };

    public Simplifier(List<OptimizationStrategy<? extends Node>> optimizations) {
        optimizer = new Optimizer(optimizations);
    }

    public SimplifierOutput simplify(String input) {
        try {
            Node node = new Parser(new Lexer(new StringReader(input))).parse();
            optimizer.optimize(node);
            return new SimplifierOutput.Success(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParsingException e) {
            return new SimplifierOutput.Fail(input, e.apply(exceptionVisitor));
        }
    }
}
