package src.org.stepik.bogolepov.simplifier;

import src.org.stepik.bogolepov.lexer.Lexer;
import src.org.stepik.bogolepov.optimizer.OptimizationStrategy;
import src.org.stepik.bogolepov.optimizer.Optimizer;
import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.parser.Parser;
import src.org.stepik.bogolepov.parser.ParsingException;
import src.org.stepik.bogolepov.parser.ParsingExceptionVisitor;
import src.org.stepik.bogolepov.parser.exceptions.LexicalException;
import src.org.stepik.bogolepov.parser.exceptions.UnexpectedEndException;
import src.org.stepik.bogolepov.parser.exceptions.UnexpectedTokenException;
import src.org.stepik.bogolepov.simplifier.errors.LexicalError;
import src.org.stepik.bogolepov.simplifier.errors.UnexpectedEndError;
import src.org.stepik.bogolepov.simplifier.errors.UnexpectedTokenError;

import java.io.IOException;
import java.io.StringReader;
import java.lang.*;
import java.util.List;

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
