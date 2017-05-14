package node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public abstract class Node {
    private Node parent;

    public Node(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public abstract <T> T apply(NodeVisitor<T> visitor);
}
