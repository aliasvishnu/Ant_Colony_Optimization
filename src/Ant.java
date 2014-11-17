import java.util.*;

/**
 * Created by Sriram Ravindran on 11/17/2014.
 */

public class Ant implements Runnable{
    public List<Integer> memory;
    public List<Vertex> vertices;
    public int id;
    Boolean[] visit;
    public int n;
    public Graph graph;
    public static final double ALPHA = 9.6d;
    public static final double BETA = -0.2d;

    public Ant(){
        memory = Collections.synchronizedList(new ArrayList<Integer>());
//        this.id = aid;

        visit = new Boolean[100];
        Arrays.fill(visit, Boolean.FALSE);

        this.n = Graph.n;
        this.graph = Graph.getGraph();
        this.vertices = graph.vertices;
        this.vertices = Collections.synchronizedList(this.vertices);
    }



    public double pGen(double pheromone, double dist){
//        System.out.println("Pheromone: " + pheromone + " and Distance: " + dist);
        return Math.pow(pheromone, ALPHA) + Math.pow(dist, BETA);
    }

    public Vertex select(List<Vertex> adjacencies, List<Double> probabilities){
        int neighbourCount = adjacencies.size();

        Double temp = probabilities.get(0);
        int randomReturn = 0;

        /* Max */
        for(int i = 0; i < neighbourCount; i++){
            if(temp < probabilities.get(i)){
                randomReturn = i;
                temp = probabilities.get(i);
            }
        }
        return adjacencies.get(randomReturn);
    }

    public void run(){
        int current = 0;
        int noOfVisitedVertices = 1;
        this.visit[current] = true;
        memory.add(current);

        while(noOfVisitedVertices < this.n){
            Boolean loop = false;
            Vertex currentVertex = this.vertices.get(current);
            List<Vertex> adjacentVertex = new ArrayList<Vertex>();
            List<Double> probabilities = new ArrayList<Double>();

            int neighbourCount = this.vertices.get(current).adjacencies.size();
//            System.out.println("For Vertex " + current + " : ");

            for(int i = 0; i < neighbourCount; i++) {
                if (!visit[currentVertex.getNeighbour(i).getId()]) {
                    adjacentVertex.add(currentVertex.getNeighbour(i));
//                    System.out.println(" " + currentVertex.getNeighbour(i) + " : ");
                    probabilities.add(this.pGen(Graph.getPheromone(current, currentVertex.getNeighbour(i).getId()),
                                      Graph.getDistance(current, currentVertex.getNeighbour(i).getId())));
                }
            }

            if(adjacentVertex.size() == 0) break;
            Vertex choice = this.select(adjacentVertex, probabilities);
//            System.out.println("Moving from Vertex " + current + " to Vertex:" + choice.getId());
            current = choice.getId();
            visit[current] = true;
            memory.add(current);
            noOfVisitedVertices++;

        }

        Graph.getGraph().updatePheromone(memory);
//        System.out.println(Thread.currentThread().getName() + "Success");
    }

}
