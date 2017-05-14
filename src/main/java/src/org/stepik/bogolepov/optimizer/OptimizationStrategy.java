package src.org.stepik.bogolepov.optimizer;

import src.org.stepik.bogolepov.node.Node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public interface OptimizationStrategy<T extends Node> {

    Class<T> target();

    default boolean isAppropriate(T node) {
        return true;
    }

    /**
     * Replace given src.org.stepik.bogolepov.node with better one
     * @param node
     * @return src.org.stepik.bogolepov.node that should be attached to the param's parent
     */
    Node optimize(T node);
}
