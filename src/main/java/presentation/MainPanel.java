package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raychen on 2017/5/15.
 */
public class MainPanel extends JPanel {

    List<Point> points;
    List<Point> links;
    List<Color> colors = new ArrayList<Color>();
    int[] cluster;

    public MainPanel(List<Point> points, List<Point> links, int[] cluster){
        this.points = points;
        this.links = links;
        this.cluster = cluster;

        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("piant()被调用!");
        //先画一个圆
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            g.setColor(colors.get(cluster[i]));
            g.fillOval(p.x, p.y, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(i), p.x+5, p.y+15);
        }
        for (int i = 0; i < links.size(); i++) {
            Point link = links.get(i);
            Point node1 = points.get(link.x);
            Point node2 = points.get(link.y);
            g.setColor(Color.BLACK);
            g.drawLine(node1.x+10, node1.y+10, node2.x+10, node2.y+10);
        }
        //画直线
        //g.drawLine(10, 20, 100, 20);
        //画矩形 第一个参表示与当前窗体的左上角x轴距离,第二个参数表示与当前窗体的左上角y轴距离,第三第四表示矩形的宽高
        //g.drawRect(0, 0, 50, 60);
        //填充矩形色
//        g.setColor(Color.GREEN);
//        g.fillRect(0, 0, 50, 60);
    }
}
