package src.org.stepik.bogolepov.optimizer;

import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.walkers.PostFixTreeWalker;
import src.org.stepik.bogolepov.optimizer.strategies.ChainReducerStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sbogolepov on 08/05/2017.
 * applies appropriate optimization strategies to the given src.org.stepik.bogolepov.node
 */
public class Optimizer {
    private Map<Class<? extends Node>, List<OptimizationStrategy<? extends Node>>> strategyMap;

    public Optimizer(List<OptimizationStrategy<? extends Node>> strategies) {
        strategyMap = strategies.stream().collect(Collectors.groupingBy(OptimizationStrategy::target));
    }

    private <T extends Node> boolean apply(T node) {
        boolean hasSuccess = false;
        List<OptimizationStrategy<? extends Node>> optimizationStrategies = strategyMap.get(node.getClass());
        if (optimizationStrategies == null) return false;
        for (OptimizationStrategy<? extends Node> strategy : optimizationStrategies) {
            OptimizationStrategy<T> s = (OptimizationStrategy<T>) strategy;
            if (s.isAppropriate(node)) {
                Node optimize = s.optimize(node);
                if (s.isSuccessful(node, optimize)) {
                    node.getParent().apply(new ReplacementVisitor(node, optimize));
                    hasSuccess = true;
                }
            }
        }
        return hasSuccess;
    }

    public void optimize(Node root) {
        root.apply(new PostFixTreeWalker(this::apply));
    }
}
