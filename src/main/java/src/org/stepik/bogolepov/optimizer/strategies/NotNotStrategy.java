package src.org.stepik.bogolepov.optimizer.strategies;

import src.org.stepik.bogolepov.optimizer.OptimizationStrategy;
import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.nodes.Not;
import src.org.stepik.bogolepov.node.nodes.Parens;

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
