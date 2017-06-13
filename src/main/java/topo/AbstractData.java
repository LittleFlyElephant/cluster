package topo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/6/13.
 */
public class AbstractData extends BaseData {
    private List<BaseData> datas;

    public AbstractData(int dataId) {
        super();
        this.dataId = dataId;
        this.datas = new ArrayList<BaseData>();
    }

    public List<BaseData> getDatas() {
        return datas;
    }

    public void setDatas(List<BaseData> datas) {
        this.datas = datas;
    }
}
