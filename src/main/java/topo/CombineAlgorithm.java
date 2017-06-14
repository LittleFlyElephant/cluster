package topo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/6/13.
 */
public class CombineAlgorithm {

    private int dataId;
    private List<BaseData> abstractDatas;
    private List<SimpleOperation> newOpList;

    public DataFlowGraph divide(DataFlowGraph originGraph){
        DataFlowGraph abstractGraph = new DataFlowGraph();
        //init
        abstractDatas = new ArrayList<BaseData>();
        newOpList = new ArrayList<SimpleOperation>();
        dataId = 1;
        //divide
        List<SimpleOperation> operations = originGraph.getOperations();
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
                        abstractInputData = newAbstractData(data);
                    }
                    abstractInputData.getOutputOperations().add(newOp);
                    abstractInputs.add(abstractInputData);
                }
                newOp.setInput(abstractInputs);
                //寻找新的输出data
                AbstractData abstractOutputData = null;
                if (operation.getOutput().getDataId()>0){
                     abstractOutputData = findDataById(operation.getOutput().getDataId(), abstractDatas);
                } else {
                    abstractOutputData = newAbstractData(operation.getOutput());
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
                    AbstractData abInput = (AbstractData)inputDatas.get(i);
                    combine(input, abInput);
                }
                BaseData output = operation.getOutput();
                AbstractData abOutput = (AbstractData)existOp.getOutput();
                combine(output, abOutput);
            }
        }
        abstractGraph.setDatas(abstractDatas);
        abstractGraph.setOperations(newOpList);
        return abstractGraph;
    }

    private void combine(BaseData baseData, AbstractData abData){
        if (baseData.getDataId()<=0){
            baseData.setDataId(abData.getDataId());
            abData.getDatas().add(baseData);
        } else {
            //TODO 如何处理一个数据可能属于多个抽象层次
            AbstractData d1 = findDataById(baseData.getDataId(), abstractDatas);
            //本来就一样
            if (d1.getDataId() == abData.getDataId()) return;
            //d1 -> abData
            for (BaseData data : d1.getDatas()) {
                data.setDataId(abData.getDataId());
                abData.getDatas().add(data);
            }
            d1.getDatas().clear();
            //处理输入
            List<SimpleOperation> inputOps = d1.getInputOperations();
            for (SimpleOperation inputOp : inputOps) {
                inputOp.setOutput(abData);
            }
            //处理输出
            List<SimpleOperation> outputOps = d1.getOutputOperations();
            for (SimpleOperation outputOp : outputOps) {
                List<BaseData> inputDatas = outputOp.getInput();
                inputDatas.remove(d1);
                inputDatas.add(abData);
            }
        }
    }

    private AbstractData newAbstractData(BaseData data){
        data.setDataId(dataId);
        AbstractData abstractData = new AbstractData(dataId);
        //随便设置了一个名字
        abstractData.setDataName(data.getDataName());
        abstractDatas.add(abstractData);
        //把base数据加入抽象数据中
        abstractData.getDatas().add(data);
        dataId++;
        return abstractData;
    }

    private int findIsIn(SimpleOperation op, List<SimpleOperation> list){
        //TODO 这里需要考虑是不是生成不同输出的操作需要作为不同操作,或者可能有多个输出生成
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
