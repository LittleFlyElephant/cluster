package topo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/6/13.
 */
public class CombineAlgorithm {

    public DataFlowGraph combine(DataFlowGraph originGraph){
        DataFlowGraph abstractGraph = new DataFlowGraph();
        List<SimpleOperation> newOpList = new ArrayList<SimpleOperation>();
        List<BaseData> abstractDatas = new ArrayList<BaseData>();
        List<SimpleOperation> operations = originGraph.getOperations();
        int dataId = 1;
        for (SimpleOperation operation: operations) {
            int index = findIsIn(operation, newOpList);
            if (index<0){
                //如果有一个新操作
                SimpleOperation newOp = new SimpleOperation(operation);
                //寻找新的输入data
                List<BaseData> abstractInputs = new ArrayList<BaseData>();
                List<BaseData> inputs = operation.getInput();
                for (BaseData data: inputs) {
                    AbstractData abstractInputData = null;
                    if (data.getDataId()>0) {
                        abstractInputData = findDataById(data.getDataId(), abstractDatas);
                    } else {
                        data.setDataId(dataId);
                        abstractInputData = new AbstractData(dataId);
                        //随便设置了一个名字
                        abstractInputData.setDataName(data.getDataName());
                        abstractDatas.add(abstractInputData);
                        //把base数据加入抽象数据中
                        abstractInputData.getDatas().add(data);
                        dataId++;
                    }
                    abstractInputData.getOutputOperations().add(newOp);
                    abstractInputs.add(abstractInputData);
                }
                newOp.setInput(abstractInputs);
                //寻找新的输出data
                //TODO 这里需要考虑是不是生成不同输出的操作需要作为不同操作,或者可能有多个输出生成
                AbstractData abstractOutputData = null;
                if (operation.getOutput().getDataId()>0){
                     abstractOutputData = findDataById(operation.getOutput().getDataId(), abstractDatas);
                } else {
                    operation.getOutput().setDataId(dataId);
                    abstractOutputData = new AbstractData(dataId);
                    //随便设置名字
                    abstractOutputData.setDataName(operation.getOutput().getDataName());
                    abstractDatas.add(abstractOutputData);
                    //把base数据加入抽象数据中
                    abstractOutputData.getDatas().add(operation.getOutput());
                    dataId++;
                }
                abstractOutputData.getInputOperations().add(newOp);
                newOp.setOutput(abstractOutputData);
                newOpList.add(newOp);
            } else {
                //如果是一个已有操作
                SimpleOperation existOp = newOpList.get(index);
                List<BaseData> inputDatas = existOp.getInput();
                for (int i = 0; i < inputDatas.size(); i++) {
                    //对应设置
                    BaseData input = operation.getInput().get(i);
                    if (input.getDataId()<=0){
                        input.setDataId(inputDatas.get(i).getDataId());
                        ((AbstractData)inputDatas.get(i)).getDatas().add(input);
                    } else {
                        //TODO 如何处理一个数据可能属于多个抽象层次
                    }
                }
                BaseData output = operation.getOutput();
                if (output.getDataId()<=0){
                    output.setDataId(existOp.getOutput().getDataId());
                    ((AbstractData)existOp.getOutput()).getDatas().add(output);
                } else {
                    //TODO 如何处理一个数据可能属于多个抽象层次
                }
            }
        }
        abstractGraph.setDatas(abstractDatas);
        abstractGraph.setOperations(newOpList);
        return abstractGraph;
    }

    private int findIsIn(SimpleOperation op, List<SimpleOperation> list){
        for (int i = 0; i < list.size(); i++) {
            SimpleOperation existOp = list.get(i);
            if (existOp.getName().equals(op.getName())
                    && existOp.getInput().size() == op.getInput().size()){
                return i;
            }
        }
        return -1;
    }

    private AbstractData findDataById(int dataId, List<BaseData> list){
        for (BaseData data: list) {
            if (data.getDataId() == dataId)
                return (AbstractData)data;
        }
        return null;
    }
}
