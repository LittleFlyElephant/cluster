package topo;

import java.util.List;

/**
 * Created by st0001 on 2017/6/12.
 */
public class DataFlowGraph {
    private List<SimpleData> datas;
    private List<SimpleOperation> operations;

    public void setOperations(List<SimpleOperation> operations) {
        this.operations = operations;
    }

    public void setDatas(List<SimpleData> datas) {

        this.datas = datas;
    }

    public List<SimpleData> getDatas() {

        return datas;
    }

    public List<SimpleOperation> getOperations() {
        return operations;
    }
}
