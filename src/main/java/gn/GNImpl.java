package gn;

import java.util.List;
import java.util.Queue;

/**
 * Created by raychen on 2017/5/12.
 */
public class GNImpl implements GNInterface {

    List<Node> nodes;
    List<Path> paths;

    public GNImpl(List<Node> nodes, List<Path> paths) {
        this.nodes = nodes;
        this.paths = paths;
    }

    public void cluster() {
        init();
        //以每个节点为源点
        for (int i = 0; i < nodes.size(); i++) {
            Node source = nodes.get(i);
            initSingle();
            source.gnVal = new GNNodeValue(1, 0);
            calNode(source);
        }
    }

    public void calNode(Node source) {
    }

    public void calPath(Node source) {

    }

    private void init(){
        //建图
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < paths.size(); j++) {
                Node node = nodes.get(i);
                if (node.id == paths.get(j).source.id || node.id == paths.get(j).target.id){
                    node.addPath(paths.get(j));
                }
            }
        }
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).gnVal = new GNPathValue();
            paths.get(i).gnVal.sum = 0;
        }
    }

    private void initSingle(){
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).gnVal = null;
        }
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).gnVal.isDel = false;
            paths.get(i).gnVal.singleVal = 0;
        }
    }

}
