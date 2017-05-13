package ui;

import simplifier.SimplificationOutputProcessor;
import simplifier.SimplifierOutput;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class CLIOutputProcessor implements SimplificationOutputProcessor {
    private String input;

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public void process(SimplifierOutput.Success success) {
        System.out.println(success.getNode());
    }

    @Override
    public void process(SimplifierOutput.Fail fail) {
        String positions = "[" + fail.getError().startPosition() + ".." + fail.getError().endPosition() + "]";
        System.out.println("Parse error at " + positions + ":" + fail.getError().getMessage());
        System.out.println(input);
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
