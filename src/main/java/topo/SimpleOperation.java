package topo;

import java.util.List;

/**
 * Created by st0001 on 2017/6/12.
 */
public class SimpleOperation {
    private String name;
    private List<BaseData> input;
    private BaseData output;

    public void setName(String name) {
        this.name = name;
    }

    public void setInput(List<BaseData> input) {
        this.input = input;
    }

    public void setOutput(BaseData output) {
        this.output = output;
    }

    public SimpleOperation(SimpleOperation operation) {
        this.name = operation.name;
    }

    public SimpleOperation() {
    }

    public String getName() {

        return name;
    }

    public List<BaseData> getInput() {
        return input;
    }

    public BaseData getOutput() {
        return output;
    }
}
