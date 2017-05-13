package ui;

import lexer.Lexer;
import optimizer.Optimizer;
import parser.Parser;
import parser.nodes.Root;

import java.io.*;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public class CommandLineInterface {
    private Optimizer optimizer;

    public CommandLineInterface(Optimizer optimizer) {
        this.optimizer = optimizer;
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
                System.out.println(processInput(input));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String processInput(String input) throws Exception {
        Root root = new Parser(new Lexer(new StringReader(input))).parse();
        optimizer.optimize(root);
        return root.toString();
    }
}
