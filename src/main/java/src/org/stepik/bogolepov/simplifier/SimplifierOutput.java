package src.org.stepik.bogolepov.simplifier;

import src.org.stepik.bogolepov.node.Node;

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
        private final String input;

        public Fail(String input, Error error) {
            this.input = input;
            this.error = error;
        }

        @Override
        public void process(SimplificationOutputProcessor processor) {
            processor.process(this);
        }

        public Error getError() {
            return error;
        }

        public String getInput() {
            return input;
        }
    }
}
