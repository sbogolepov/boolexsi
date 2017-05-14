package optimizer.strategies;

import node.nodes.Not;
import optimizer.OptimizationStrategy;
import node.Node;
import node.nodes.BinaryOp;
import node.nodes.Parens;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public class RemoveParensStrategy implements OptimizationStrategy<Parens> {
    @Override
    public Class<Parens> target() {
        return Parens.class;
    }

    @Override
    public boolean isAppropriate(Parens node) {
        Node parent = node.getParent();
        if (parent == null) {
            return false;
        }
        Node child = node.getChild();
        if (child instanceof BinaryOp && parent instanceof BinaryOp) {
            return ((BinaryOp) child).getType() == ((BinaryOp) parent).getType();
        }
        if (child instanceof Not && parent instanceof Not) {
            return false;
        }
        return true;
    }

    @Override
    public Node optimize(Parens node) {
        return node.getChild();
    }
}
