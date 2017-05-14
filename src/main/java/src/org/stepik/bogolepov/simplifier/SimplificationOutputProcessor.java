package src.org.stepik.bogolepov.simplifier;

/**
 * Created by sbogolepov on 13/05/2017.
 */
public interface SimplificationOutputProcessor {
    void process(SimplifierOutput.Success success);

    void process(SimplifierOutput.Fail fail);
}
