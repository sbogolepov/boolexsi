package node.nodes;

import node.NodeVisitor;
import node.Node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Id extends Node {
    private String name;

    public Id(Node parent, String name) {
        super(parent);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id = (Id) o;

        return name != null ? name.equals(id.name) : id.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
