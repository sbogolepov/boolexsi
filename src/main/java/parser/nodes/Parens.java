package parser.nodes;

import optimizer.NodeVisitor;
import parser.Node;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by sbogolepov on 14/05/2017.
 * useful only for printing
 */
public class Parens extends Node {

    private Node child;

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Parens(Node parent, Node child) {
        super(parent);
        this.child = child;
    }

    public static Parens wrap(Node node, Consumer<Node> childSetter) {
        Parens parens = new Parens(node.getParent(), node);
        node.setParent(parens);
        childSetter.accept(parens);
        return parens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parens parens = (Parens) o;

        return child != null ? child.equals(parens.child) : parens.child == null;
    }

    @Override
    public int hashCode() {
        return child.hashCode();
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "(" + child + ")";
    }
}
