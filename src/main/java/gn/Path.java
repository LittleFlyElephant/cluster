package gn;

/**
 * Created by raychen on 2017/5/12.
 */
public class Path {
    public int w;
    public Node source;
    public Node target;
    public GNPathValue gnVal;

    public Path(int w, Node source, Node target) {
        this.w = w;
        this.source = source;
        this.target = target;
    }
}
