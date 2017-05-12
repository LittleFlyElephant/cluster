package gn;

/**
 * Created by raychen on 2017/5/12.
 */
public class Path {
    public double w;
    public Node source;
    public Node target;
    public double single; //单次边界数
    public double sum; //边阶数
    public boolean isDel; //是否被删除
    public Path reverse;

    public Path(double w, Node source, Node target) {
        this.w = w;
        this.source = source;
        this.target = target;
    }
}
