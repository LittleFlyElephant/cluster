package topo;

import java.util.List;

/**
 * Created by st0001 on 2017/6/12.
 */
public class DataFlowGraph {
    private List<BaseData> datas;
    private List<SimpleOperation> operations;

    public void setOperations(List<SimpleOperation> operations) {
        this.operations = operations;
    }

    public void setDatas(List<BaseData> datas) {
        this.datas = datas;
    }

    public List<BaseData> getDatas() {
        return datas;
    }

    public List<SimpleOperation> getOperations() {
        return operations;
    }
}
