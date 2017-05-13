package optimizer.strategies;

import optimizer.OptimizationStrategy;
import parser.Node;
import parser.nodes.Not;

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
        return node.getChild() instanceof Not;
    }

    @Override
    public Node optimize(Not node) {
        return ((Not) node.getChild()).getChild();
    }
}
