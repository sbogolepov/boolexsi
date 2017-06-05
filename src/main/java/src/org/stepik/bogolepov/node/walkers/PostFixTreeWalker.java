package src.org.stepik.bogolepov.node.walkers;

import src.org.stepik.bogolepov.node.Node;
import src.org.stepik.bogolepov.node.NodeVisitor;
import src.org.stepik.bogolepov.node.nodes.*;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sbogolepov on 09/05/2017.
 * Walks tree in post-order and applies f() for each src.org.stepik.bogolepov.node.
 * if application was successful, it reapplies f()
 */
public class PostFixTreeWalker implements NodeVisitor<Boolean> {

    private Predicate<Node> f;

    public PostFixTreeWalker(Predicate<Node> f) {
        this.f = f;
    }

    @Override
    public Boolean visit(BinaryOp binaryOp) {
        if (f.test(binaryOp)) {
            return true;
        }

        if (binaryOp.getLeftChild().apply(this) || binaryOp.getRightChild().apply(this)) {
            return visit(binaryOp);
        }
        return false;
    }

    @Override
    public Boolean visit(Id id) {
        return f.test(id);
    }

    @Override
    public Boolean visit(Literal literal) {
         return f.test(literal);
    }

    @Override
    public Boolean visit(Not not) {
        if (f.test(not)) {
            return true;
        }

        if (not.getChild().apply(this)) {
            return visit(not);
        }
        return false;
    }

    @Override
    public Boolean visit(Root root) {
        if (root.getChild().apply(this)) {
            visit(root);
        }
        return f.test(root);
    }

    @Override
    public Boolean visit(Parens parens) {
        if (f.test(parens)) {
            return true;
        }
        if (parens.getChild().apply(this)) {
            return visit(parens);
        }
        return false;
    }
}
