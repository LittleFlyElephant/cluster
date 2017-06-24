package topo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/6/13.
 */
public class JsonUtil {
    public static DataFlowGraph getGraphFromJson(String fileName){
        DataFlowGraph graph = new DataFlowGraph();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            jsonObject = gson.fromJson(reader, JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<BaseData> baseDatas = new ArrayList<BaseData>();
        List<SimpleOperation> operations = new ArrayList<SimpleOperation>();
        JsonArray json_datas = jsonObject.getAsJsonArray("data");
        JsonArray json_ops = jsonObject.getAsJsonArray("operations");
        for (int i = 0; i < json_datas.size(); i++) {
            JsonObject json_data = json_datas.get(i).getAsJsonObject();
            BaseData baseData = new BaseData();
            baseData.setDataName(json_data.get("name").getAsString());
            baseDatas.add(baseData);
        }
        for (int i = 0; i < json_ops.size(); i++) {
            JsonObject op = json_ops.get(i).getAsJsonObject();
            String op_name = op.get("name").getAsString();
            JsonArray inputs = op.getAsJsonArray("input");
            String output = op.get("output").getAsString();

            SimpleOperation operation = new SimpleOperation();
            List<BaseData> inputList = new ArrayList<BaseData>();
            for (int j = 0; j < inputs.size(); j++) {
                String input_name = inputs.get(j).getAsString();
                BaseData inputData = findByName(input_name, baseDatas);
                inputData.getOutputOperations().add(operation);
                inputList.add(inputData);
            }
            BaseData outputData = findByName(output, baseDatas);
            outputData.getInputOperations().add(operation);
            operation.setName(op_name);
            operation.setOutput(outputData);
            operation.setInput(inputList);
            operations.add(operation);
        }
        graph.setOperations(operations);
        graph.setDatas(baseDatas);
        return graph;
    }

    public static void printGraph(DataFlowGraph graph){
        System.out.println("------------------------graph:--------------------------");
        for (BaseData data: graph.getDatas()) {
            if (data instanceof AbstractData){
                System.out.print(data.getDataId()+".[");
                AbstractData abstractData = (AbstractData) data;
                for (BaseData subData : abstractData.getDatas()) {
                    System.out.print(subData.getDataName()+",");
                }
                System.out.println("]");
            } else {
                System.out.println(data.getDataId()+"."+data.getDataName());
            }
        }
        for (SimpleOperation operation: graph.getOperations()) {
            System.out.print("{inputs:");
            List<BaseData> inputs = operation.getInput();
            for (BaseData input: inputs) {
                System.out.print("[");
                if (input instanceof AbstractData) System.out.print(input.getDataId());
                else System.out.print(input.getDataName());
                System.out.print("],");
            }
            System.out.print("}---"+operation.getName()+"--->{outputs:[");
            BaseData output = operation.getOutput();
            if (output instanceof AbstractData) System.out.print(output.getDataId());
            else System.out.print(output.getDataName());
            System.out.println("]}");
        }
    }

    private static BaseData findByName(String name, List<BaseData> baseDatas){
        for (BaseData baseData: baseDatas) {
            if (baseData.getDataName().equals(name)) return baseData;
        }
        return null;
    }

    public static void main(String[] args) {
        DataFlowGraph graph = JsonUtil.getGraphFromJson("src/main/resources/Sample4.json");
        JsonUtil.printGraph(graph);
        CombineAlgorithm algorithm = new CombineAlgorithm();
        DataFlowGraph newGraph = algorithm.divide(graph);
        JsonUtil.printGraph(newGraph);
    }
}
