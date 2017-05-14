package optimizer.strategies;

import optimizer.OptimizationStrategy;
import node.Node;
import node.nodes.BinaryOp;
import node.nodes.Not;
import node.nodes.Parens;

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

        Parens leftParens = new Parens(null, leftChild);
        Parens rightParens = new Parens(null, rightChild);

        Not leftNot = new Not(newOp, leftParens);
        leftParens.setParent(leftNot);
        Not rightNot = new Not(newOp, rightParens);
        rightParens.setParent(rightNot);

        newOp.setLeftChild(leftNot);
        newOp.setRightChild(rightNot);
        return newOp;
    }
}
