package optimizer;

import parser.Node;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sbogolepov on 08/05/2017.
 * applies appropriate optimization strategies to the given node
 */
public class Optimizer {
    private Map<Class<? extends Node>, List<OptimizationStrategy<? extends Node>>> strategyMap;

    public Optimizer(List<OptimizationStrategy<? extends Node>> strategies) {
        strategyMap = strategies.stream().collect(Collectors.groupingBy(OptimizationStrategy::target));
    }

    // TODO: fix typing
    private <T extends Node> boolean apply(T node) {
        List<OptimizationStrategy<? extends Node>> optimizationStrategies = strategyMap.get(node.getClass());
        if (optimizationStrategies == null) return false;
        for (OptimizationStrategy<? extends Node> strategy : optimizationStrategies) {
            OptimizationStrategy<T> s = (OptimizationStrategy<T>) strategy;
            if (s.isAppropriate(node)) {
                Node optimize = s.optimize(node);
                if (!optimize.equals(node)) {
                    node.getParent().apply(new ReplacementVisitor(node, optimize));
                    return true;
                }
            }
        }
        return false;
    }

    public void optimize(Node root) {
        root.apply(new PostFixTreeWalker(this::apply));
    }
}
