package topo;

import java.util.List;

/**
 * Created by st0001 on 2017/6/12.
 */
public class SimpleData {
    private String dataName;
    private List<SimpleOperation> inputOperations;
    private List<SimpleOperation> outputOperations;

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public void setInputOperations(List<SimpleOperation> inputOperations) {
        this.inputOperations = inputOperations;
    }

    public void setOutputOperations(List<SimpleOperation> outputOperations) {
        this.outputOperations = outputOperations;
    }

    public String getDataName() {

        return dataName;
    }

    public List<SimpleOperation> getInputOperations() {
        return inputOperations;
    }

    public List<SimpleOperation> getOutputOperations() {
        return outputOperations;
    }
}
