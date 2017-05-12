package gn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/5/12.
 */
public class Node {
    public int id;
    public List<Path> links;
    public GNNodeValue gnVal;

    public Node(int id) {
        this.id = id;
        links = new ArrayList<Path>();
    }

    public void addPath(Node target, int w){
        Path p = new Path(w, this, target);
        links.add(p);
    }

    public void addPath(Path p){
        links.add(p);
    }
}
