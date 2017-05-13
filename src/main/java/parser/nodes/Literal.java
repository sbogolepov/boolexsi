package parser.nodes;

import optimizer.NodeVisitor;
import parser.Node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Literal extends Node {
    private boolean value;

    public Literal(Node parent, boolean value) {
        super(parent);
        this.value = value;
    }

    public boolean value() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        return value == literal.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
