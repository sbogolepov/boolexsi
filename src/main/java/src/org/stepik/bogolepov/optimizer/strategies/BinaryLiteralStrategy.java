package src.org.stepik.bogolepov.optimizer.strategies;

import src.org.stepik.bogolepov.optimizer.OptimizationStrategy;
import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.nodes.BinaryOp;
import src.org.stepik.bogolepov.node.nodes.Literal;

/**
 * Created by sbogolepov on 09/05/2017.
 */
public class BinaryLiteralStrategy implements OptimizationStrategy<BinaryOp> {
    @Override
    public Class<BinaryOp> target() {
        return BinaryOp.class;
    }

    @Override
    public boolean isAppropriate(BinaryOp node) {
        return node.getLeftChild() instanceof Literal
                || node.getRightChild() instanceof Literal;
    }

    @Override
    public Node optimize(BinaryOp node) {
        Literal literal;
        Node child;
        if (node.getLeftChild() instanceof Literal) {
            literal = (Literal) node.getLeftChild();
            child = node.getRightChild();
        } else {
            literal = (Literal) node.getRightChild();
            child = node.getLeftChild();
        }
        if (node.getType() == BinaryOp.Type.OR) {
            return literal.value() ? literal : child;
        } else {
            return literal.value() ? child : literal;
        }
    }


}
