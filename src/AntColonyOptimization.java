/**
 * Created by Sriram Ravindran on 11/17/2014.
 */
public class AntColonyOptimization {

    public static void main(String[] args) throws InterruptedException {
        Graph graph = Graph.getGraph();
        graph.startGraph();

        for(int j = 0; j < 3; j++) {
            for (int i = 0; i < 10000; i++) {
                Ant ant = new Ant();
                Thread t = new Thread(ant);
                t.start();
            }
            Thread.sleep(2000);
            graph.evaporatePheromone();
            System.out.println("\nAfter " + j+1 + " Iterations => Path is ");
            graph.printTour();
        }
    }
}
