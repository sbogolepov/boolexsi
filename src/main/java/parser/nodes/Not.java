package parser.nodes;

import optimizer.NodeVisitor;
import parser.Node;

import java.util.function.Consumer;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Not extends Node {
    private Node child;

    public Not(Node parent, Node child) {
        super(parent);
        this.child = child;
    }

    public static Not invert(Node node, Consumer<Node> childSetter) {
        Not not = new Not(node.getParent(), node);
        node.setParent(not);
        childSetter.accept(not);
        return not;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Not not = (Not) o;

        return child != null ? child.equals(not.child) : not.child == null;
    }

    @Override
    public int hashCode() {
        return child != null ? child.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NOT " + child;
    }

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
