package src.org.stepik.bogolepov.ui;

import src.org.stepik.bogolepov.simplifier.Simplifier;

import java.io.*;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class CommandLineInterface {
    private final Simplifier simplifier;

    private CLIOutputProcessor outputProcessor;

    public CommandLineInterface(Simplifier simplifier) {
        this.simplifier = simplifier;
        outputProcessor = new CLIOutputProcessor();
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.print("> ");
                String input = reader.readLine();
                if (input.equals("quit")) {
                    break;
                }
                simplifier.simplify(input).process(outputProcessor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
