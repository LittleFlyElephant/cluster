package topo;

import java.util.List;

/**
 * Created by raychen on 2017/6/13.
 */
public class MicroService {
    private SimpleOperation operation;
    private AbstractData outputData;
    private List<BaseData> inputDatas;

    public SimpleOperation getOperation() {
        return operation;
    }

    public AbstractData getOutputData() {
        return outputData;
    }

    public List<BaseData> getInputDatas() {
        return inputDatas;
    }

    public void setOperation(SimpleOperation operation) {

        this.operation = operation;
    }

    public void setOutputData(AbstractData outputData) {
        this.outputData = outputData;
    }

    public void setInputDatas(List<BaseData> inputDatas) {
        this.inputDatas = inputDatas;
    }
}
