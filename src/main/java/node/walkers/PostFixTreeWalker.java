package node.walkers;

import node.Node;
import node.NodeVisitor;
import node.nodes.*;

import java.util.function.Function;

/**
 * Created by sbogolepov on 09/05/2017.
 * Walks tree in post-order and applies f() for each node.
 * if application was successful, it reapplies f()
 */
public class PostFixTreeWalker implements NodeVisitor<Boolean> {

    private Function<Node, Boolean> f;

    public PostFixTreeWalker(Function<Node, Boolean> f) {
        this.f = f;
    }

    @Override
    public Boolean visit(BinaryOp binaryOp) {
        if (binaryOp.getLeftChild().apply(this) || binaryOp.getRightChild().apply(this)) {
            visit(binaryOp);
        }
        return f.apply(binaryOp);
    }

    @Override
    public Boolean visit(Id id) {
        return f.apply(id);
    }

    @Override
    public Boolean visit(Literal literal) {
         return f.apply(literal);
    }

    @Override
    public Boolean visit(Not not) {
        if (not.getChild().apply(this)) {
            visit(not);
        }
        return f.apply(not);
    }

    @Override
    public Boolean visit(Root root) {
        if (root.getChild().apply(this)) {
            visit(root);
        }
        return f.apply(root);
    }

    @Override
    public Boolean visit(Parens parens) {
        if (parens.getChild().apply(this)) {
            visit(parens);
        }
        return f.apply(parens);
    }
}
