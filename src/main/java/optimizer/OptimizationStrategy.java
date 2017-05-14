package optimizer;

import node.Node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public interface OptimizationStrategy<T extends Node> {

    Class<T> target();

    default boolean isAppropriate(T node) {
        return true;
    }

    /**
     * Replace given node with better one
     * @param node
     * @return node that should be attached to the param's parent
     */
    Node optimize(T node);
}
