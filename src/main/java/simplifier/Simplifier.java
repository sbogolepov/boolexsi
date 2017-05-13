package simplifier;

import lexer.Lexer;
import optimizer.OptimizationStrategy;
import optimizer.Optimizer;
import parser.Node;
import parser.Parser;
import parser.ParsingException;
import parser.exceptions.UnexpectedEndException;
import parser.exceptions.UnexpectedTokenException;
import simplifier.errors.UnexpectedTokenError;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class Simplifier {

    private final Optimizer optimizer;

    public Simplifier(List<OptimizationStrategy<? extends Node>> optimizations) {
        optimizer = new Optimizer(optimizations);
    }


    // TODO: process other exceptions
    public SimplifierOutput simplify(String input) {
        try {
            Node node = new Parser(new Lexer(new StringReader(input))).parse();
            optimizer.optimize(node);
            return new SimplifierOutput.Success(node);
        } catch (UnexpectedTokenException e) {
            return new SimplifierOutput.Fail(new UnexpectedTokenError(e));
        } catch (UnexpectedEndException e) {
            return new SimplifierOutput.Fail(null);
        } catch (IOException e) {
            return new SimplifierOutput.Fail(null);
        } catch (ParsingException e) {
            return new SimplifierOutput.Fail(null);
        }
    }
}
