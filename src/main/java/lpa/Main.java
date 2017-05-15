package lpa;

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
        existMap.put(6, 0);
        int[][] map = {
                {4, 1, 4, 1, 0, 0, 0, 0, 0},
                {1, 4, 1, 4, 0, 0, 0, 0, 0},
                {4, 1, 4, 1, 0, 0, 0, 0, 0},
                {1, 4, 1, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 1, 4, 1},
                {0, 0, 0, 0, 0, 1, 4, 1, 4},
                {0, 0, 0, 0, 0, 4, 1, 4, 1},
                {0, 0, 0, 0, 0, 1, 4, 1, 4},
        };
        LPA lpa = new LPA();
        double[][] tagMap = lpa.lpa(map, existMap, 2);
        for (int i = 0; i < tagMap.length; i++) {
            System.out.println(Arrays.toString(tagMap[i]));
        }
    }
}
