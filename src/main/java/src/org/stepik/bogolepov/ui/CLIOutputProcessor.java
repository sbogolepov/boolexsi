package src.org.stepik.bogolepov.ui;

import src.org.stepik.bogolepov.simplifier.SimplificationOutputProcessor;
import src.org.stepik.bogolepov.simplifier.SimplifierOutput;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class CLIOutputProcessor implements SimplificationOutputProcessor {
    private CLIExpressionPrinter expressionPrinter = new CLIExpressionPrinter();

    @Override
    public void process(SimplifierOutput.Success success) {
        success.getNode().apply(expressionPrinter);
    }

    @Override
    public void process(SimplifierOutput.Fail fail) {
        int start = fail.getError().startPosition();
        int end = fail.getError().endPosition();
        String positions = "[" + start + ".." + end + "]";
        System.out.println("Parse error at " + positions + ": " + fail.getError().getMessage());
        System.out.println(fail.getInput());
        int i = 0;
        while (i < fail.getError().startPosition()) {
            System.out.print(' ');
            i++;
        }
        while (i < fail.getError().endPosition()) {
            System.out.print('^');
            i++;
        }
        System.out.println();
    }
}
