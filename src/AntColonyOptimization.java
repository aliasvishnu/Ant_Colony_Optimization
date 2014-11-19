import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sriram Ravindran on 11/17/2014.
 */
public class AntColonyOptimization {
    public double bestRoute;
    public List<Integer> bestPath;
    public Graph graph;

    public AntColonyOptimization(){
        this.bestRoute = 1000000;
        this.bestPath = new ArrayList<Integer>();
    }

    public double getPathDistance(List<Integer> path){
        double dist = 0;
        double totDist = 0;
        int first = path.get(0), second;
        for(int i = 1; i < Graph.n-1; i++){
            second = path.get(i);
            totDist += this.graph.distanceMap[first][second];
            first = second;
        }
        return totDist;
    }

    public void updateBestRoute(List<Integer> path){
        double totDist = getPathDistance(path);
        if(totDist < bestRoute){
            bestRoute = totDist;
            System.out.println("Found new best route");
            int limit = path.size();
            bestPath.clear();
            for(int i = 0; i < limit; i++){
                bestPath.add(path.get(i));
            }
        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        AntColonyOptimization aco = new AntColonyOptimization();
        aco.graph = Graph.getGraph(52);
        aco.graph.startGraph();
        List<Integer> order;
        FileWriter wr = new FileWriter("path.txt");

        for(int j = 0; j < 30; j++) {
            for (int i = 0; i < 20; i++) {
                Ant ant = new Ant();
                Thread t = new Thread(ant);
                t.start();
            }

            Thread.sleep(100);
            order = aco.graph.printTour();
            wr.write(order.toString());
            aco.updateBestRoute(order);
            System.out.println(order.toString());
            aco.graph.evaporatePheromone();
        }

        System.out.println("\nThe best possible tour is \n" + aco.bestPath.toString());
        System.out.println("Best distance is " + aco.getPathDistance(aco.bestPath));
        wr.write(aco.bestPath.toString());
        wr.close();
    }
}

