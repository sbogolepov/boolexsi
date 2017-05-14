package optimizer.strategies;

import optimizer.OptimizationStrategy;
import parser.Node;
import parser.nodes.BinaryOp;
import parser.nodes.Not;
import parser.nodes.Parens;

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
            return ((BinaryOp) child).getType() != ((BinaryOp) parent).getType();
        }
        return true;
    }

    @Override
    public Node optimize(Parens node) {
        return node.getChild();
    }
}
