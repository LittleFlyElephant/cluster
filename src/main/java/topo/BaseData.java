package topo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/6/13.
 */
public class BaseData {
    protected int dataId;
    protected String dataName;
    protected List<SimpleOperation> inputOperations;
    protected List<SimpleOperation> outputOperations;

    public BaseData() {
        this.inputOperations = new ArrayList<SimpleOperation>();
        this.outputOperations = new ArrayList<SimpleOperation>();
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getDataId() {

        return dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setInputOperations(List<SimpleOperation> inputOperations) {
        this.inputOperations = inputOperations;
    }

    public void setOutputOperations(List<SimpleOperation> outputOperations) {
        this.outputOperations = outputOperations;
    }

    public List<SimpleOperation> getInputOperations() {
        return inputOperations;
    }

    public List<SimpleOperation> getOutputOperations() {
        return outputOperations;
    }
}
