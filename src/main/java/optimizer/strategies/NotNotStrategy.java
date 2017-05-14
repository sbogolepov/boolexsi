package optimizer.strategies;

import optimizer.OptimizationStrategy;
import node.Node;
import node.nodes.Not;
import node.nodes.Parens;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class NotNotStrategy implements OptimizationStrategy<Not> {

    @Override
    public Class<Not> target() {
        return Not.class;
    }

    @Override
    public boolean isAppropriate(Not node) {
        return node.getChild() instanceof Not
                || node.getChild() instanceof Parens && ((Parens) node.getChild()).getChild() instanceof Not;
    }

    @Override
    public Node optimize(Not node) {
        if (node.getChild() instanceof Not) {
            return ((Not) node.getChild()).getChild();
        } else {
            return ((Not) ((Parens) node.getChild()).getChild()).getChild();
        }
    }
}
