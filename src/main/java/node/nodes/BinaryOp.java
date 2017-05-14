package node.nodes;

import node.NodeVisitor;
import node.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public BinaryOp(Node parent, Type type) {
        this(parent, type, null, null);
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

        return type == binaryOp.type
                && getChainOperands(this).equals(binaryOp.getChainOperands(binaryOp));
    }

    private Set<Node> getChainOperands(BinaryOp binaryOp) {
        Set<Node> operands = new HashSet<>();
        if (binaryOp.leftChild instanceof BinaryOp && ((BinaryOp) binaryOp.leftChild).getType() == binaryOp.getType()) {
            operands.addAll(getChainOperands((BinaryOp) binaryOp.leftChild));
        } else {
            operands.add(binaryOp.leftChild);
        }
        if (binaryOp.rightChild instanceof BinaryOp && ((BinaryOp) binaryOp.rightChild).getType() == binaryOp.getType()) {
            operands.addAll(getChainOperands((BinaryOp) binaryOp.rightChild));
        } else {
            operands.add(binaryOp.rightChild);
        }
        return operands;
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
