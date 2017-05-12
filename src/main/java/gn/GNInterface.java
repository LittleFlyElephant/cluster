package gn;

import java.util.List;

/**
 * Created by raychen on 2017/5/12.
 */
public interface GNInterface {
    void cluster();
    void calNode(Node source);
    void calPath(Node source);
}
