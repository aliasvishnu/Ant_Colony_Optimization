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
    private double pGenSum;
    public boolean loop;
    public static final double ALPHA = -0.2;
    public static final double BETA = -9.6;

    public Ant(){
        memory = Collections.synchronizedList(new ArrayList<Integer>());

        visit = new Boolean[100];
        Arrays.fill(visit, Boolean.FALSE);

        this.pGenSum = 0;
        this.loop = false;

        this.n = Graph.n;
        this.graph = Graph.getGraph();
        this.vertices = graph.vertices;
        this.vertices = Collections.synchronizedList(this.vertices);
    }

    public double pGen(double pheromone, double dist){
        double val = Math.pow(pheromone, ALPHA)*Math.pow(dist, BETA);
        this.pGenSum += val;
        return val;
    }

    public void pGenDivide(List<Double> probabilities){
        int len = probabilities.size();
        double tot = 0d;
        for(int i = 0; i < len; i++){
           probabilities.set(i, probabilities.get(i)/pGenSum);
            tot += probabilities.get(i);
        }
        // System.out.println("Total Probability is " + tot);
    }

    public int randomSelector(List<Double> probabilities){
        int ans = 0;
        int len = probabilities.size();

        List<Integer> modified = new ArrayList<Integer>();
        for(int i = 0;i < len; i++){
            modified.add((int)(probabilities.get(i)*10000));
        }

        int randomNumber = ((new Random()).nextInt(10000));

        for(int i = 0; i < len; i++){
            if(randomNumber > modified.get(i))   {
                randomNumber -= modified.get(i);
            }
            else return i;
        }
        return ans;
    }

    public Vertex select(List<Vertex> adjacencies, List<Double> probabilities){
        int randomReturn = randomSelector(probabilities);

//        double temp = probabilities.get(0);
//        int randomReturn = 0;
//        int neighbourCount = adjacencies.size();
//        /* Max */
//        for(int i = 0; i < neighbourCount; i++){
//            if(temp < probabilities.get(i)){
//                randomReturn = i;
//                temp = probabilities.get(i);
//            }
//        }
        return adjacencies.get(randomReturn);
    }

    public void run(){
        int current = 0;
        int noOfVisitedVertices = 0;
        memory.add(current);
        this.loop = false;

        while(noOfVisitedVertices < this.n){
            visit[current] = true;
            noOfVisitedVertices++;
            this.pGenSum = 0;
            Vertex currentVertex = this.vertices.get(current);

            /* Adjacent Vertices and corresponding probabilities */
            List<Vertex> adjacentVertex = new ArrayList<Vertex>();
            List<Double> probabilities = new ArrayList<Double>();


            int neighbourCount = this.vertices.get(current).adjacencies.size();
            //System.out.println("For Vertex " + current + " : ");

            for(int i = 0; i < neighbourCount; i++) {
                if (!visit[currentVertex.getNeighbour(i).getId()]) {

                    adjacentVertex.add(currentVertex.getNeighbour(i));
                    probabilities.add(this.pGen(Graph.getPheromone(current, currentVertex.getNeighbour(i).getId()),
                                                Graph.getDistance(current, currentVertex.getNeighbour(i).getId())));
                }
            }

            this.pGenDivide(probabilities);

            if(adjacentVertex.size() == 0) break;
            Vertex choice = this.select(adjacentVertex, probabilities);

//            System.out.println("Moving from Vertex " + current + " to Vertex:" + choice.getId());

            current = choice.getId();
            memory.add(current);
            noOfVisitedVertices++;

            if(noOfVisitedVertices == this.n && !this.loop){
                this.loop = true;
                visit[0] = false;
                noOfVisitedVertices--;
            }

        }

        Graph.getGraph().updatePheromone(memory);
//        System.out.println(Thread.currentThread().getName() + "Success");
    }

}
