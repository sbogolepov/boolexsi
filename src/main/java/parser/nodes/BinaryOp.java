package parser.nodes;

import optimizer.NodeVisitor;
import parser.Node;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class BinaryOp extends Node {
    public enum Type {
        OR,
        AND
    }

    private Type type;
    private Node leftChild;
    private Node rightChild;

    public BinaryOp(Node parent, Type type, Node leftChild, Node rightChild) {
        super(parent);
        this.type = type;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOp binaryOp = (BinaryOp) o;

        return type == binaryOp.type && (isSymmetricalEquals(binaryOp) || isAsymmetricalEquals(binaryOp));

    }

    private boolean isSymmetricalEquals(BinaryOp binaryOp) {
        return (leftChild != null ? leftChild.equals(binaryOp.leftChild) : binaryOp.leftChild == null)
                && (rightChild != null ? rightChild.equals(binaryOp.rightChild) : binaryOp.rightChild == null);
    }

    private boolean isAsymmetricalEquals(BinaryOp binaryOp) {
        return (leftChild != null ? leftChild.equals(binaryOp.rightChild) : binaryOp.rightChild == null)
                && (rightChild != null ? rightChild.equals(binaryOp.leftChild) : binaryOp.leftChild == null);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (leftChild != null ? leftChild.hashCode() : 0);
        result = 31 * result + (rightChild != null ? rightChild.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return leftChild + " " + type + " " + rightChild;
    }

    @Override
    public <T> T apply(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
