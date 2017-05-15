package gn;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by raychen on 2017/5/12.
 */
public class GNImpl implements GNInterface {

    List<Node> nodes;
    List<Path> paths;
    int id = 0;
    List<Path> delPaths = new ArrayList<Path>();
    List<Double> qList = new ArrayList<Double>();
    private static int maxQueueSize = 1000;

    public GNImpl(List<Node> nodes, List<Path> paths) {
        this.nodes = nodes;
        this.paths = paths;
    }

    public void cluster() {
        init();
        while (true){
            //初始化边介数
            for (Path link: paths){
                link.sum = 0;
            }
            //以每个节点为源点
            for (int i = 0; i < nodes.size(); i++) {
                Node source = nodes.get(i);
                initSingle();
                source.d = 0;
                source.w = 1;
                calNode(source);
                calPath(source);
                //加到总的里面
                for (int j = 0; j < paths.size(); j++) {
                    Path p = paths.get(i);
                    p.sum += p.single;
                }
            }
            // 寻找最大边权比并删除边
            Path maxPath = paths.get(0);
            for (Path link: paths){
                double avg = link.sum / link.w;
                if (avg > maxPath.sum / maxPath.w) maxPath = link;
            }
            // 已划分完毕
            if (maxPath.sum == 0) break;
            // 删除2条边
            maxPath.isDel = true;
            maxPath.reverse.isDel = true;
            delPaths.add(maxPath);
            // 寻找社区
            setVisFalse();
            for (Node node: nodes){
                if (!node.isVisited){
                    id ++;
                    dfs(node, id);
                }
            }
            // 计算Q值
            calQ();
        }
        // 寻找最大q值
        for (int i = 0; i < qList.size(); i++) {
            System.out.println(qList.get(i));
            System.out.println(delPaths.get(i).source.id + " " + delPaths.get(i).target.id);
        }
    }

    public void calNode(Node source) {
        //宽度优先遍历
        Queue<Node> queue = new ArrayBlockingQueue<Node>(maxQueueSize);
        queue.add(source);
        while (!queue.isEmpty()){
            Node head = queue.poll();
            List<Path> paths = head.links;
            for (int i = 0; i < paths.size(); i++) {
                //已删除的边
                if (paths.get(i).isDel) continue;
                Node target = paths.get(i).target;
                if (target.d == -1){
                    target.d = head.d + 1;
                    target.w = head.w;
                    queue.add(target);
                } else {
                    if (target.d == head.d + 1){
                        target.w ++;
                    }
                }
            }
        }
    }

    public void calPath(Node source) {
        Queue<Node> queue = new ArrayBlockingQueue<Node>(maxQueueSize);
        //寻找叶子节点的d
        int maxD = -1;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.d > maxD)
                maxD = node.d;
        }
        //寻找叶子节点
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.d == maxD)
                queue.add(node);
        }
        //构造path
        setVisFalse();
        while (!queue.isEmpty()){
            Node head = queue.poll();
            List<Path> links = head.links;
            double sum = 0;
            for (Path link: links) {
                if (link.isDel) continue;
                if (link.target.d == head.d + 1)
                    sum += link.single;
            }
            for (Path link: links){
                if (link.isDel) continue;
                if (link.target.d == head.d - 1){
                    link.single = (sum+1)*((double) link.target.w/head.w);
                    link.reverse.single = link.single;
                    if (!link.target.isVisited){
                        link.target.isVisited = true;
                        queue.add(link.target);
                    }
                }
            }
        }
    }

    public void calQ() {
        double Q = 0;
        double M = 0;
        for (Node node: nodes){
            node.sum = calPathSum(node);
        }
        for (Path link: paths){
            M += link.w;
        }
        M /= 2;
        for (Node node: nodes){
            setVisFalse();
            for (Path path: node.links){
                if (!path.isDel){
                    Node tar = path.target;
                    if (node.belong == tar.belong)
                        Q += (path.w - node.sum*tar.sum/(2.0*M));
                    tar.isVisited = true;
                }
            }
            for (Node tar: nodes){
                if (!tar.isVisited && tar.belong == node.belong)
                    Q += -node.sum*tar.sum/(2.0*M);
            }
        }
        Q /= (2.0*M);
        qList.add(Q);
    }

    // 初始化建图
    private void init(){
        //建图
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < paths.size(); j++) {
                Node node = nodes.get(i);
                if (node.id == paths.get(j).source.id){
                    node.addPath(paths.get(j));
                }
            }
        }
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).isDel = false;
            paths.get(i).sum = 0;
        }
        // 加入对应边
        for (Path p1: paths){
            for (Path p2: paths){
                if (p1.source.id == p2.target.id && p1.target.id == p2.source.id){
                    p1.reverse = p2;
                    p2.reverse = p1;
                }
            }
        }
    }

    // 每次计算单源路径后重新初始化
    private void initSingle(){
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).d = -1;
        }
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).single = 0;
        }
    }

    // 初始化点访问列表
    private void setVisFalse(){
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).isVisited = false;
        }
    }

    // dfs点，划分社群
    private void dfs(Node node, int id){
        node.belong = id;
        for (Path link: node.links){
            Node target = link.target;
            if (!target.isVisited) {
                target.isVisited = true;
                dfs(target, id);
            }
        }
    }

    // 计算所有路径和
    private double calPathSum(Node node){
        double sum = 0;
        for (Path link: node.links){
            if (!link.isDel) sum += link.w;
        }
        return sum;
    }

}
