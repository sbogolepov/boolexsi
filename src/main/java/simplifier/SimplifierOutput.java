package simplifier;

import node.Node;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public interface SimplifierOutput {

    void process(SimplificationOutputProcessor processor);

    final class Success implements SimplifierOutput {
        private final Node node;

        public Success(Node node) {
            this.node = node;
        }

        @Override
        public void process(SimplificationOutputProcessor processor) {
            processor.process(this);
        }

        public Node getNode() {
            return node;
        }
    }

    final class Fail implements SimplifierOutput {

        private final Error error;

        public Fail(Error error) {

            this.error = error;
        }

        @Override
        public void process(SimplificationOutputProcessor processor) {
            processor.process(this);
        }

        public Error getError() {
            return error;
        }
    }
}
