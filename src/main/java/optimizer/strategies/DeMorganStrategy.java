package optimizer.strategies;

import optimizer.OptimizationStrategy;
import parser.Node;
import parser.nodes.BinaryOp;
import parser.nodes.Not;

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
        return node.getChild() instanceof BinaryOp;
    }

    @Override
    public Node optimize(Not node) {
        BinaryOp prevOp = (BinaryOp) node.getChild();
        Node leftChild = prevOp.getLeftChild();
        Node rightChild = prevOp.getRightChild();
        BinaryOp.Type type = prevOp.getType() == BinaryOp.Type.AND ? BinaryOp.Type.OR : BinaryOp.Type.AND;
        BinaryOp newOp = new BinaryOp(node.getParent(), type, null, null);
        newOp.setLeftChild(new Not(newOp, leftChild));
        newOp.setRightChild(new Not(newOp, rightChild));
        return newOp;
    }
}
