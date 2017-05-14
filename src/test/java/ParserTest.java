import lexer.Lexer;
import org.junit.Test;
import parser.Node;
import parser.Parser;
import parser.exceptions.LexicalException;
import parser.nodes.*;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class ParserTest {

    private Node astFromString(String str) throws Exception {
       return new Parser(new Lexer(new StringReader(str))).parse().getChild();
    }

    @Test
    public void singleTrue() throws Exception {
        assertThat(astFromString("TRUE")).isEqualTo(new Literal(null, true));
    }

    @Test
    public void singleFalse() throws Exception {
        assertThat(astFromString("FALSE")).isEqualTo(new Literal(null, false));
    }

    @Test
    public void singleId() throws Exception {
        assertThat(astFromString("id")).isEqualTo(new Id(null, "id"));
    }

    @Test
    public void simpleAnd() throws Exception {
        BinaryOp binaryOp = new BinaryOp(null, BinaryOp.Type.AND, null, null);
        Literal t = new Literal(binaryOp, true);
        Literal f = new Literal(binaryOp, false);
        binaryOp.setLeftChild(t);
        binaryOp.setRightChild(f);
        assertThat(astFromString("TRUE AND FALSE")).isEqualTo(binaryOp);
    }

    @Test
    public void simpleOr() throws Exception {
        BinaryOp binaryOp = new BinaryOp(null, BinaryOp.Type.OR, null, null);
        Literal t = new Literal(binaryOp, true);
        Literal f = new Literal(binaryOp, false);
        binaryOp.setLeftChild(t);
        binaryOp.setRightChild(f);
        assertThat(astFromString("TRUE OR FALSE")).isEqualTo(binaryOp);
    }

    @Test
    public void multipleOr() throws Exception {
        BinaryOp binaryOp = new BinaryOp(null, BinaryOp.Type.OR, null, null);
        Literal t1 = new Literal(binaryOp, true);
        BinaryOp binaryOp1 = new BinaryOp(binaryOp, BinaryOp.Type.OR, null, null);
        binaryOp.setLeftChild(t1);
        binaryOp.setRightChild(binaryOp1);
        Literal t2 = new Literal(binaryOp1, true);
        Literal t3 = new Literal(binaryOp1, true);
        binaryOp1.setLeftChild(t2);
        binaryOp1.setRightChild(t3);
        assertThat(astFromString("TRUE OR TRUE OR TRUE")).isEqualTo(binaryOp);
    }

    @Test
    public void multipleAnd() throws Exception {
        BinaryOp binaryOp = new BinaryOp(null, BinaryOp.Type.AND, null, null);
        Literal t1 = new Literal(binaryOp, true);
        BinaryOp binaryOp1 = new BinaryOp(binaryOp, BinaryOp.Type.AND, null, null);
        binaryOp.setLeftChild(t1);
        binaryOp.setRightChild(binaryOp1);
        Literal t2 = new Literal(binaryOp1, true);
        Literal t3 = new Literal(binaryOp1, true);
        binaryOp1.setLeftChild(t2);
        binaryOp1.setRightChild(t3);
        assertThat(astFromString("TRUE AND TRUE AND TRUE")).isEqualTo(binaryOp);
    }

    // (TRUE OR x) AND NOT FALSE
    @Test
    public void complex() throws Exception {
        BinaryOp and = new BinaryOp(null, BinaryOp.Type.AND, null, null);
        BinaryOp or = new BinaryOp(and, BinaryOp.Type.OR, null, null);
        Literal t = new Literal(or, true);
        Id id = new Id(or, "id");
        or.setLeftChild(t);
        or.setRightChild(id);
        and.setLeftChild(or);
        Not n = new Not(and, null);
        Literal f = new Literal(n, false);
        n.setChild(f);
        and.setRightChild(n);
        assertThat(astFromString("(TRUE OR id) AND NOT FALSE")).isEqualTo(and);
    }

    // NOT (x OR y)
    @Test
    public void notWithParen1() throws Exception {
        BinaryOp or = new BinaryOp(null, BinaryOp.Type.OR, null, null);
        Not not = new Not(null, or);
        or.setParent(not);
        or.setLeftChild(new Id(or, "x"));
        or.setRightChild(new Id(or, "y"));
        assertThat(astFromString("NOT (x OR y)")).isEqualTo(not);
    }

    // NOT (x) OR NOT (y)
    @Test
    public void notWithParen2() throws Exception {
        BinaryOp or = new BinaryOp(null, BinaryOp.Type.OR, null, null);
        Not not = new Not(null, or);
        or.setParent(not);
        or.setLeftChild(new Id(or, "x"));
        or.setRightChild(new Id(or, "y"));
        assertThat(astFromString("NOT (x OR y)")).isEqualTo(not);
    }
}
