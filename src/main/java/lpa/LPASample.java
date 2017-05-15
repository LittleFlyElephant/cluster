package lpa; /**
 * Created by raychen on 2017/5/12.
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LPASample {

    public static float sigma = 1;
    public static int tag_num = 2;

    public static void main(String[] args) {

        float[][] data = {
                {1,1},
                {1,2},
                {2,1},
                {2,2},
                {4,4},
                {6,6},
                {6,7},
                {7,6},
                {7,7}
        };

        Map<Integer, Integer> tag_map = new HashMap<Integer, Integer>();
        tag_map.put(1, 1);
        tag_map.put(6, 0);

        float[][] weight = new float[data.length][data.length];

        for(int i = 0; i < weight.length; i++) {
            float sum = 0f;
            for(int j = 0; j < weight[i].length; j++) {
                weight[i][j] = (float) Math.exp( - distance(data[i], data[j]) / Math.pow(sigma, 2));
                sum += weight[i][j];
            }
            for(int j = 0; j < weight[i].length; j++) {
                weight[i][j] /= sum;
            }
        }

        System.out.println("=============");
        for(int i = 0; i < weight.length; i++) {
            System.out.println(Arrays.toString(weight[i]));
        }
        System.out.println("=============");

        float[][] tag_matrix = new float[data.length][tag_num];
        for(int i = 0; i < tag_matrix.length; i++) {
            if(tag_map.get(i) != null) {
                tag_matrix[i][tag_map.get(i)] = 1;
            } else {
                float sum = 0;
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    tag_matrix[i][j] = (float) Math.random();
                    sum += tag_matrix[i][j];
                }
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    tag_matrix[i][j] /= sum;
                }
            }
        }

        for(int it = 0; it < 100; it++) {
            for(int i = 0; i < tag_matrix.length; i++) {
                if(tag_map.get(i) != null) {
                    continue;
                }
                float all_sum = 0;
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    float sum = 0;
                    for(int k = 0; k < weight.length; k++) {
                        sum += weight[i][k] * tag_matrix[k][j];
                    }
                    tag_matrix[i][j] = sum;
                    all_sum += sum;
                }
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    tag_matrix[i][j] /= all_sum;
                }
            }
            System.out.println("=============");
            for(int i = 0; i < tag_matrix.length; i++) {
                System.out.println(Arrays.toString(tag_matrix[i]));
            }
            System.out.println("=============");
        }
    }

    public static float distance(float[] a, float[] b) {

        float dis = 0;
        for(int i = 0; i < a.length; i++) {
            dis = (float) Math.pow(b[i] - a[i], 2);
        }
        return dis;
    }
}