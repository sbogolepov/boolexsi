package src.org.stepik.bogolepov.optimizer.strategies;

import src.org.stepik.bogolepov.node.nodes.Parens;
import src.org.stepik.bogolepov.optimizer.OptimizationStrategy;
import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.nodes.BinaryOp;
import src.org.stepik.bogolepov.node.nodes.Literal;
import src.org.stepik.bogolepov.node.nodes.Not;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbogolepov on 10/05/2017.
 */
public class ChainReducerStrategy implements OptimizationStrategy<BinaryOp> {

    private boolean hasDuplicates = false;

    @Override
    public Class<BinaryOp> target() {
        return BinaryOp.class;
    }

    @Override
    public boolean isAppropriate(BinaryOp node) {
        return true;
    }

    @Override
    public Node optimize(BinaryOp node) {
        hasDuplicates = false;
        BinaryOp.Type type = node.getType();

        Map<Node, Boolean> children = new HashMap<>();

        if (collectChainChildren(node, type, children)) {
            return new Literal(node.getParent(), type == BinaryOp.Type.OR);
        }

        return rebuildChain(children, type);
    }

    @Override
    public boolean isSuccessful(BinaryOp from, Node to) {
        return hasDuplicates;
    }

    private boolean collectChainChildren(BinaryOp node, BinaryOp.Type type, Map<Node, Boolean> children) {
        boolean result = false;
        if (isChild(node.getLeftChild(), type)) {
            if (processChild(node.getLeftChild(), children)) {
                return true;
            }
        } else {
            result = collectChainChildren((BinaryOp) node.getLeftChild(), type, children);
        }
        if (isChild(node.getRightChild(), type)) {
            if (processChild(node.getRightChild(), children)) {
                return true;
            }
        } else {
            result = collectChainChildren((BinaryOp) node.getRightChild(), type, children);
        }
        return result;
    }


    // build subtree from bottom to the top
    private Node rebuildChain(Map<Node, Boolean> children, BinaryOp.Type type) {
        Node currentNode = null;
        for (Map.Entry<Node, Boolean> nodeEntry : children.entrySet()) {
            if (currentNode == null) {
                currentNode = wrapNode(nodeEntry.getKey(), nodeEntry.getValue());
            } else {
                BinaryOp op = new BinaryOp(null, type, currentNode, null);
                Node rightChild = wrapNode(nodeEntry.getKey(), nodeEntry.getValue());
                rightChild.setParent(op);
                op.setRightChild(rightChild);
                currentNode = op;
            }
        }
        return currentNode;
    }

    private Node wrapNode(Node node, boolean isPositive) {
        if (!isPositive) {
            return new Not(node);
        } else {
            return node;
        }
    }

    private static boolean isChild(Node node, BinaryOp.Type type) {
        return !(node instanceof BinaryOp) || ((BinaryOp) node).getType() != type;
    }

    private boolean processChild(Node node, Map<Node, Boolean> children) {
        if (node instanceof Not) {

            if (children.containsKey(((Not) node).getChild())) {
                hasDuplicates = true;
                if (children.get(((Not) node).getChild())) {
                    return true;
                } else {
                    children.put(((Not) node).getChild(), false);
                    return false;
                }
            } else {
                children.put(((Not) node).getChild(), false);
                return false;
            }
        } else {
            if (children.containsKey(node)) {
                hasDuplicates = true;
                if (!children.get(node)) {
                    return true;
                } else {
                    children.put(node, true);
                    return false;
                }
            } else {
                children.put(node, true);
                return false;
            }
        }
    }
}
