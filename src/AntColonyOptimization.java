import java.io.IOException;

/**
 * Created by Sriram Ravindran on 11/17/2014.
 */
public class AntColonyOptimization {

    public static void main(String[] args) throws InterruptedException, IOException {

        Graph graph = Graph.getGraph();
        graph.startGraph();

        for(int j = 0; j < 100; j++) {
            for (int i = 0; i < 20; i++) {
                Ant ant = new Ant();
                Thread t = new Thread(ant);
                t.start();
            }

            Thread.sleep(100);
            System.out.println("After " + "all" + " Iterations => Distance is " + graph.printTour());
            graph.evaporatePheromone();
        }
    }
}
