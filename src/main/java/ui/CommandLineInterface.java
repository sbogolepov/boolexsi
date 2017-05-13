package ui;

import simplifier.SimplificationOutputProcessor;
import simplifier.Simplifier;

import java.io.*;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class CommandLineInterface {
    private final Simplifier simplifier;

    private CLIOutputProcessor outputProcessor = new CLIOutputProcessor();

    public CommandLineInterface(Simplifier simplifier) {
        this.simplifier = simplifier;
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
                outputProcessor.setInput(input);
                simplifier.simplify(input).process(outputProcessor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
