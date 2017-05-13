package parser.nodes;

import optimizer.NodeVisitor;
import parser.Node;

/**
 * Created by sbogolepov on 09/05/2017.
 */
public class Root extends Node {

    private Node child;

    public Root(Node child) {
        super(null);
        this.child = child;
    }

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
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

        Root root = (Root) o;

        return child != null ? child.equals(root.child) : root.child == null;
    }

    @Override
    public int hashCode() {
        return child != null ? child.hashCode() : 0;
    }

    @Override
    public String toString() {
        return child.toString();
    }
}
