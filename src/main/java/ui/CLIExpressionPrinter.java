package ui;


import node.nodes.*;

/**
 * Created by sbogolepov on 14/05/2017.
 */
public class CLIExpressionPrinter implements node.NodeVisitor<Void> {
    @Override
    public Void visit(BinaryOp binaryOp) {
        binaryOp.getLeftChild().apply(this);
        String opSymbol = binaryOp.getType() == BinaryOp.Type.OR ? "OR" : "AND";
        System.out.print(" " + opSymbol +  " ");
        binaryOp.getRightChild().apply(this);
        return null;
    }

    @Override
    public Void visit(Id id) {
        System.out.print(id.getName());
        return null;
    }

    @Override
    public Void visit(Literal literal) {
        System.out.print(literal.value() ? "TRUE" : "FALSE");
        return null;
    }

    @Override
    public Void visit(Not not) {
        System.out.print("NOT ");
        not.getChild().apply(this);
        return null;
    }

    @Override
    public Void visit(Root root) {
        root.getChild().apply(this);
        System.out.println();
        return null;
    }

    @Override
    public Void visit(Parens parens) {
        System.out.print("(");
        parens.getChild().apply(this);
        System.out.print(")");
        return null;
    }
}
