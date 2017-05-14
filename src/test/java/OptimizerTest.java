import lexer.Lexer;
import optimizer.Optimizer;
import optimizer.strategies.*;
import org.junit.Test;
import parser.Node;
import parser.Parser;

import java.io.StringReader;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by sbogolepov on 09/05/2017.
 */
public class OptimizerTest {

    private Node astFromString(String str) throws Exception {
        return new Parser(new Lexer(new StringReader(str))).parse();
    }

    private static Optimizer defaultOptimizer() {
        return new Optimizer(Arrays.asList(
                new DeMorganStrategy(),
                new InvertLiteral(),
                new NotNotStrategy(),
                new BinaryLiteralStrategy(),
                new ChainReducerStrategy(),
                new RemoveParensStrategy()
        ));
    }

    @Test
    public void notNotTest() throws Exception {
        Node root = astFromString("NOT (NOT TRUE)");
        new Optimizer(Arrays.asList(new NotNotStrategy())).optimize(root);
        assertThat(root).isEqualTo(astFromString("TRUE"));
    }

    @Test
    public void invertLiteralTest() throws Exception {
        Node root = astFromString("NOT TRUE");
        new Optimizer(Arrays.asList(new InvertLiteral())).optimize(root);
        assertThat(root).isEqualTo(astFromString("FALSE"));
    }

    @Test
    public void deMorganTest() throws Exception {
        Node root = astFromString("NOT (x OR y)");
        new Optimizer(Arrays.asList(new DeMorganStrategy())).optimize(root);
        assertThat(root).isEqualTo(astFromString("NOT (x) AND NOT (y)"));
    }

    @Test
    public void literalAndIdTest() throws Exception {
        Node root = astFromString("TRUE AND id");
        new Optimizer(Arrays.asList(new BinaryLiteralStrategy())).optimize(root);
        assertThat(root).isEqualTo(astFromString("id"));

        Node r = astFromString("TRUE OR NOT id");
        new Optimizer(Arrays.asList(new BinaryLiteralStrategy())).optimize(r);
        assertThat(r).isEqualTo(astFromString("TRUE"));
    }

    @Test
    public void complex1Test() throws Exception {
        Node root = astFromString("NOT(NOT dog AND NOT cat AND NOT FALSE)");
        defaultOptimizer().optimize(root);
        assertThat(root).isEqualTo(astFromString("dog OR cat"));
    }

    @Test
    public void simpleTest() throws Exception {
        Node root = astFromString("x OR y OR TRUE");
        defaultOptimizer().optimize(root);
        assertThat(root).isEqualTo(astFromString("TRUE"));
    }

    @Test
    public void toBeOrToBe() throws Exception {
        Node root = astFromString("x OR x");
        defaultOptimizer().optimize(root);
        assertThat(root).isEqualTo(astFromString("x"));
    }

    @Test
    public void toBeOrToBeOrToBeOrToBe() throws Exception {
        Node root = astFromString("x OR x OR x OR x");
        defaultOptimizer().optimize(root);
        assertThat(root).isEqualTo(astFromString("x"));
    }

    @Test
    public void toBeOrToBeOrToNotBeOrToBe() throws Exception {
        Node root = astFromString("x OR x OR NOT x OR x");
        defaultOptimizer().optimize(root);
        assertThat(root).isEqualTo(astFromString("TRUE"));
    }

    @Test
    public void toBeOrNotToBe() throws Exception {
        Node root = astFromString("x OR NOT x");
        defaultOptimizer().optimize(root);
        assertThat(root).isEqualTo(astFromString("TRUE"));
    }
}
