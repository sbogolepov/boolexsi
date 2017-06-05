package src.org.stepik.bogolepov.optimizer.strategies;

import src.org.stepik.bogolepov.optimizer.OptimizationStrategy;
import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.nodes.BinaryOp;
import src.org.stepik.bogolepov.node.nodes.Not;
import src.org.stepik.bogolepov.node.nodes.Parens;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class DeMorganStrategy implements OptimizationStrategy<Not> {

    @Override
    public Class<Not> target() {
        return Not.class;
    }

    @Override
    public  boolean isAppropriate(Not node) {
        return node.getChild() instanceof Parens
                && ((Parens) node.getChild()).getChild() instanceof BinaryOp
                || node.getChild() instanceof BinaryOp;
    }

    @Override
    public Node optimize(Not node) {
        BinaryOp prevOp;
        if (node.getChild() instanceof Parens) {
            prevOp = (BinaryOp) ((Parens) node.getChild()).getChild();
        } else {
            prevOp = (BinaryOp) node.getChild();
        }
        return applyDeMorgan(prevOp, node.getParent());
    }

    private Node applyDeMorgan(BinaryOp prevOp, Node parent) {
        Node leftChild = prevOp.getLeftChild();
        Node rightChild = prevOp.getRightChild();

        BinaryOp.Type type = prevOp.getType() == BinaryOp.Type.AND ? BinaryOp.Type.OR : BinaryOp.Type.AND;
        BinaryOp newOp = new BinaryOp(parent, type);

        Not leftNot = Not.invert(Parens.wrap(leftChild));
        Not rightNot = Not.invert(Parens.wrap(rightChild));

        newOp.setLeftChild(leftNot);
        newOp.setRightChild(rightNot);
        return newOp;
    }
}
