package node;

import node.nodes.*;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public interface NodeVisitor<T> {

    T visit(BinaryOp binaryOp);

    T visit(Id id);

    T visit(Literal literal);

    T visit(Not not);

    T visit(Root root);

    T visit(Parens parens);
}
