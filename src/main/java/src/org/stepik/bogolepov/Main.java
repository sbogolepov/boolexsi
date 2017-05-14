package src.org.stepik.bogolepov;

import src.org.stepik.bogolepov.optimizer.strategies.*;
import src.org.stepik.bogolepov.simplifier.Simplifier;
import src.org.stepik.bogolepov.ui.CommandLineInterface;

import java.util.Arrays;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class Main {
    public static void main(String[] args) {

        Simplifier simplifier = new Simplifier(Arrays.asList(
                new DeMorganStrategy(),
                new InvertLiteral(),
                new NotNotStrategy(),
                new BinaryLiteralStrategy(),
                new ChainReducerStrategy(),
                new RemoveParensStrategy()
        ));
        CommandLineInterface cli = new CommandLineInterface(simplifier);
        cli.run();
    }
}
