package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/5/15.
 */
public class Canvas extends JFrame {

    List<Point> drawPoints = new ArrayList<Point>();
    List<Point> links = new ArrayList<Point>();
    MainPanel panel;

    public Canvas(){
        this.setSize(600, 400);
        this.setLocation(300, 200);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public void paint(int[][] map, int[] cluster){
        setPoints(2);
        for (int i = 0; i < map.length; i++) {
            for (int j = i+1; j < map.length; j++) {
                if (map[i][j] > 0) links.add(new Point(i, j));
            }
        }
        panel = new MainPanel(drawPoints, links, cluster);
        this.add(panel);
        this.setVisible(true);
    }

    public void setPoints(int i){
        if (i == 1) {
            drawPoints.add(new Point(100, 50));
            drawPoints.add(new Point(50, 80));
            drawPoints.add(new Point(150, 80));
            drawPoints.add(new Point(50, 110));
            drawPoints.add(new Point(150, 110));
            drawPoints.add(new Point(100, 140));
            drawPoints.add(new Point(200, 140));
        } else if (i == 2){
            drawPoints.add(new Point(50, 50));
            drawPoints.add(new Point(100, 50));
            drawPoints.add(new Point(50, 80));
            drawPoints.add(new Point(100, 80));
            drawPoints.add(new Point(150, 65));
            drawPoints.add(new Point(200, 50));
            drawPoints.add(new Point(250, 50));
            drawPoints.add(new Point(200, 80));
            drawPoints.add(new Point(250, 80));
        }
    }

}
