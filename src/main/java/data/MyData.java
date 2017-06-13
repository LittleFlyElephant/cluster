package data;

import gn.Node;
import gn.Path;
import lpa.LPA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raychen on 2017/5/18.
 */
public class MyData {

    public static void GNData1(List<Node> nodes, List<Path> paths){
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
    }

    public static void GNData2(List<Node> nodes, List<Path> paths){
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);
        nodes.add(n7);
        nodes.add(n8);
        nodes.add(n9);
        paths.add(new Path(1, n1, n2));
        paths.add(new Path(1, n1, n3));
        paths.add(new Path(1, n1, n6));
        paths.add(new Path(1, n1, n7));
        paths.add(new Path(1, n1, n8));
        paths.add(new Path(1, n2, n6));
        paths.add(new Path(1, n3, n4));
        paths.add(new Path(1, n3, n6));
        paths.add(new Path(1, n3, n8));
        paths.add(new Path(1, n4, n5));
        paths.add(new Path(1, n4, n7));
        paths.add(new Path(1, n5, n6));
        paths.add(new Path(1, n6, n7));
        paths.add(new Path(1, n6, n9));

        paths.add(new Path(1, n2, n1));
        paths.add(new Path(1, n3, n1));
        paths.add(new Path(1, n6, n1));
        paths.add(new Path(1, n7, n1));
        paths.add(new Path(1, n8, n1));
        paths.add(new Path(1, n6, n2));
        paths.add(new Path(1, n4, n3));
        paths.add(new Path(1, n6, n3));
        paths.add(new Path(1, n8, n3));
        paths.add(new Path(1, n5, n4));
        paths.add(new Path(1, n7, n4));
        paths.add(new Path(1, n6, n5));
        paths.add(new Path(1, n7, n6));
        paths.add(new Path(1, n9, n6));
    }
}
