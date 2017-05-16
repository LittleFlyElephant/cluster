package presentation;

/**
 * Created by raychen on 2017/5/15.
 */
public class DrawMain implements PaintInterface{

    public void drawGraph(int[][] map, int[] vCluster) {
        Canvas canvas = new Canvas();
        canvas.paint(map, vCluster);
    }

    public static void main(String[] args) {
        DrawMain drawMain = new DrawMain();
        int[][] map = {
                {0, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 1, 1},
                {0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0}
        };
        int[] cluster = {0, 0, 1, 0, 1, 1, 0};
        drawMain.drawGraph(map, cluster);
    }

}
