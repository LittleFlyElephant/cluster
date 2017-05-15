package lpa;

import java.util.Map;

/**
 * Created by raychen on 2017/5/13.
 */
public class LPA {

    private static double e = 0.001;

    public double[][] lpa(int[][] map, Map<Integer, Integer> existTag, int k){
        double[][] tagMap = new double[map.length][k];
        // 初始化标签
        for (int i = 0; i < tagMap.length; i++) {
            for (int j = 0; j < k; j++) {
                tagMap[i][j] = 0.0;
            }
        }
        for (int i = 0; i < tagMap.length; i++) {
            if (existTag.get(i) != null){
                tagMap[i][existTag.get(i)] = 1.0;
            } else {
                double sum = 0;
                for (int j = 0; j < k; j++) {
                    tagMap[i][j] = Math.random();
                    sum += tagMap[i][j];
                }
                for (int j = 0; j < k; j++) {
                    tagMap[i][j] /= sum;
                }
            }
        }
        // 收敛
        double[][] lastMap = new double[map.length][k];
        do {
            // 保存旧的结果
            for (int i = 0; i < tagMap.length; i++) {
                for (int j = 0; j < k; j++) {
                    lastMap[i][j] = tagMap[i][j];
                }
            }
            // 更新
            for (int i = 0; i < tagMap.length; i++) {
                if (existTag.get(i) != null) continue;
                double sum = 0;
                for (int j = 0; j < k; j++) {
                    double val = 0;
                    for (int l = 0; l < map.length; l++) {
                        val += (double) map[i][l] * tagMap[l][j];
                    }
                    tagMap[i][j] = val;
                    sum += val;
                }
                for (int j = 0; j < k; j++) {
                    tagMap[i][j] /= sum;
                }
            }
        } while (!check(tagMap, lastMap));
        return tagMap;
    }

    private boolean check(double[][] now, double[][] old){
        for (int i = 0; i < now.length; i++) {
            for (int j = 0; j < now[i].length; j++) {
                if (Math.abs(now[i][j] - old[i][j])>e)
                    return false;
            }
        }
        return true;
    }
}
