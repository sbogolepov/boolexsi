package src.org.stepik.bogolepov.node.nodes;

import src.org.stepik.bogolepov.node.NodeVisitor;
import src.org.stepik.bogolepov.node.Node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Not extends Node {
    private Node child;

    public Not(Node parent, Node child) {
        super(parent);
        this.child = child;
    }

    public Not(Node child) {
        this(child.getParent(), child);
    }

    public static Not invert(Node child) {
        Not not = new Not(child);
        child.setParent(not);
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
        return "NOT " + "(" + child + ")";
    }

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
