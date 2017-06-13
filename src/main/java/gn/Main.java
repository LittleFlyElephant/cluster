package gn;

import data.MyData;
import presentation.DrawMain;
import presentation.PaintInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/5/12.
 */
public class Main {
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<Node>();
        List<Path> paths = new ArrayList<Path>();
        MyData.GNData2(nodes, paths);
        GNImpl gn = new GNImpl(nodes, paths);
        gn.cluster();


        // 图片展示
        int[][] map = {
                {1, 1, 1, 0, 0, 1, 1, 1, 0},
                {1, 1, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 0, 1, 0},
                {0, 0, 1, 1, 1, 0, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 1, 0, 1, 1, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 1}
        };
//        for (int i = 0; i <= gn.maxQIndex; i++) {
//            Path p = gn.delPaths.get(i);
//            map[p.source.id][p.target.id] = 0;
//            map[p.target.id][p.source.id] = 0;
//        }
        int[] vCluster = new int[map.length];
        for (int i = 0; i < map.length; i++) {
            vCluster[i] = nodes.get(i).belong;
        }
        PaintInterface paint = new DrawMain();
        paint.drawGraph(map, vCluster);
    }
}
