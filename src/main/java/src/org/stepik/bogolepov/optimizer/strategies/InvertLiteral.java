package src.org.stepik.bogolepov.optimizer.strategies;

import src.org.stepik.bogolepov.optimizer.OptimizationStrategy;
import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.nodes.Literal;
import src.org.stepik.bogolepov.node.nodes.Not;

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
