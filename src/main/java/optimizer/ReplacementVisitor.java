package optimizer;

import parser.Node;
import parser.nodes.*;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class ReplacementVisitor implements NodeVisitor<Void> {
    private Node from;
    private Node to;

    public ReplacementVisitor(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Void visit(BinaryOp binaryOp) {
        to.setParent(binaryOp);
        if (binaryOp.getLeftChild().equals(from)) {
            binaryOp.setLeftChild(to);
        } else {
            binaryOp.setRightChild(to);
        }
        return null;
    }

    @Override
    public Void visit(Id id) {
        return null;
    }

    @Override
    public Void visit(Literal literal) {
        return null;
    }

    @Override
    public Void visit(Not not) {
        to.setParent(not);
        not.setChild(to);
        return null;
    }

    @Override
    public Void visit(Root root) {
        to.setParent(root);
        root.setChild(to);
        return null;
    }

    @Override
    public Void visit(Parens parens) {
        to.setParent(parens);
        parens.setChild(to);
        return null;
    }
}
