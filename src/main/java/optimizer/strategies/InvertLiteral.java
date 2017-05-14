package optimizer.strategies;

import optimizer.OptimizationStrategy;
import node.Node;
import node.nodes.Literal;
import node.nodes.Not;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class InvertLiteral implements OptimizationStrategy<Not> {
    @Override
    public Class<Not> target() {
        return Not.class;
    }

    @Override
    public boolean isAppropriate(Not node) {
        return node.getChild() instanceof Literal;
    }

    @Override
    public Node optimize(Not node) {
        boolean value = ((Literal) node.getChild()).value();
        return new Literal(node.getParent(), !value);
    }
}
