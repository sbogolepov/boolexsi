import optimizer.Optimizer;
import optimizer.strategies.*;
import ui.CommandLineInterface;

import java.util.Arrays;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class Main {
    public static void main(String[] args) {
        Optimizer optimizer = new Optimizer(Arrays.asList(
                new DeMorganStrategy(),
                new InvertLiteral(),
                new NotNotStrategy(),
                new BinaryLiteralStrategy(),
                new ChainReducerStrategy()
        ));
        CommandLineInterface cli = new CommandLineInterface(optimizer);
        cli.run();
    }
}
