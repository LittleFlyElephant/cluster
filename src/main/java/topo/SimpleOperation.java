package topo;

/**
 * Created by st0001 on 2017/6/12.
 */
public class SimpleOperation {
    private String operationName;
    private SimpleData inputData;
    private SimpleData outputData;

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public void setInputData(SimpleData inputData) {
        this.inputData = inputData;
    }

    public void setOutputData(SimpleData outputData) {
        this.outputData = outputData;
    }

    public String getOperationName() {

        return operationName;
    }

    public SimpleData getInputData() {
        return inputData;
    }

    public SimpleData getOutputData() {
        return outputData;
    }
}
