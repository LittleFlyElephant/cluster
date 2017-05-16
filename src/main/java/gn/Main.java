package gn;

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
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        nodes.add(n0);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);
        List<Path> paths = new ArrayList<Path>();
        paths.add(new Path(1, n0, n1));
        paths.add(new Path(1, n0, n2));
        paths.add(new Path(1, n1, n3));
        paths.add(new Path(1, n2, n3));
        paths.add(new Path(1, n2, n4));
        paths.add(new Path(1, n3, n5));
        paths.add(new Path(1, n4, n5));
        paths.add(new Path(1, n4, n6));

        paths.add(new Path(1, n1, n0));
        paths.add(new Path(1, n2, n0));
        paths.add(new Path(1, n3, n1));
        paths.add(new Path(1, n3, n2));
        paths.add(new Path(1, n4, n2));
        paths.add(new Path(1, n5, n3));
        paths.add(new Path(1, n5, n4));
        paths.add(new Path(1, n6, n4));
        GNImpl gn = new GNImpl(nodes, paths);
        gn.cluster();

        // 输出
        int[][] map = {
                {0, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 1, 1},
                {0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0}
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
