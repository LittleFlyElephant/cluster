package lpa;

import presentation.DrawMain;
import presentation.PaintInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raychen on 2017/5/13.
 */
public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> existMap = new HashMap<Integer, Integer>();
        existMap.put(1, 1);
        existMap.put(6, 2);
        int[][] map = {
                {1, 1, 4, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 4, 1, 0, 0, 0, 0},
                {4, 1, 1, 1, 0, 0, 0, 0, 0},
                {1, 4, 1, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 4, 1},
                {0, 0, 0, 0, 0, 1, 1, 1, 4},
                {0, 0, 0, 0, 0, 4, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 4, 1, 1},
        };
        LPA lpa = new LPA();
        double[][] tagMap = lpa.lpa(map, existMap, 4);
        for (int i = 0; i < tagMap.length; i++) {
            System.out.println(Arrays.toString(tagMap[i]));
        }

        // 画图
        int[] vCluster = new int[map.length];
        for (int i = 0; i < tagMap.length; i++) {
            int maxI = 0;
            for (int j = 1; j < tagMap[i].length; j++) {
                if (tagMap[i][j] > tagMap[i][maxI])
                    maxI = j;
            }
            vCluster[i] = maxI;
        }
        PaintInterface paint = new DrawMain();
        paint.drawGraph(map, vCluster);
    }
}
