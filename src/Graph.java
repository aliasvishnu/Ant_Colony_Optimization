import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;

public class Graph{
    public double[][] distanceMap;
    public double[][] pheromoneMap;
    public List<Vertex> vertices;
    public static int n;
    public static final double ALPHA = 5.6d;
    public static final double BETA = -0.2d;
    public static final double RHO = 0.3d;
    public static final double DELTA = 0.1d;
    public static Graph graph = null;

    class Point{
        public double x;
        public double y;
    }

    public Graph(){
        this.distanceMap = new double[100][100];
        this.pheromoneMap = new double[100][100];

        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                this.distanceMap[i][j] = -1;
                this.pheromoneMap[i][j] = 100;
            }
        }

        this.n = 52;
    }

    public static Graph getGraph(){
        if(Graph.graph == null) Graph.graph = new Graph();
        return Graph.graph;
    }

    public static double getPheromone(int x, int y){
        return Graph.getGraph().pheromoneMap[x][y];
    }

    public static double getDistance(int x, int y){
        return Graph.getGraph().distanceMap[x][y];
    }

    public synchronized void updatePheromone(List<Integer> path){
        int start = path.get(0);
        int next;
        int limit = path.size();
        for(int i = 1; i < limit-1; i++){
            next = path.get(i);
            pheromoneMap[start][next] = pheromoneMap[next][start] = pheromoneMap[next][start]*(1+DELTA);
            start = next;
        }
    }

    public synchronized void evaporatePheromone(){
        for(int i = 0; i < Graph.n; i++){
            for(int j = 0; j < Graph.n; j++){
                pheromoneMap[i][j] = pheromoneMap[j][i] = pheromoneMap[j][i]*(1-RHO);
            }
        }
    }

    public void dijkstra(Vertex source){
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();

        source.minDistance = 0.;
        vertexQueue.add(source);
        this.distanceMap[source.getId()][source.getId()] = 0;

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies){
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;

                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    this.distanceMap[source.getId()][v.getId()] = distanceThroughU;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public void allPairsShortestPath(){
        for(int i = 0; i < n; i++) {
            this.dijkstra(vertices.get(i));
            for(int j = 0; j < n; j++){
                vertices.get(j).reset();
            }
        }
    }

    public double getEulerianDistance(double x, double y){
         return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void startGraph() throws IOException {
        Graph graph = Graph.getGraph();
        graph.vertices = Collections.synchronizedList(new ArrayList<Vertex>());

        for(int i = 0; i < Graph.n; i++){
            graph.vertices.add(new Vertex(i));
        }

        Point[] cities = new Point[100];
        BufferedReader read = new BufferedReader(new FileReader("locations.txt"));
        for(int i = 0; i < Graph.n; i++){
            Scanner scan = new Scanner(read.readLine());
            int id = scan.nextInt()-1;
            cities[id] = new Point();
            cities[id].x = scan.nextDouble();
            cities[id].y = scan.nextDouble();
        }

        for(int i = 0; i < Graph.n; i++){
            for(int j = 0; j < Graph.n; j++){
                double temp = graph.getEulerianDistance(cities[i].x-cities[j].x, cities[i].y - cities[j].y);

                if(i != j){
                    graph.vertices.get(i).adjacencies.add(new Edge(graph.vertices.get(j), temp));
                }

                graph.distanceMap[i][j] = graph.distanceMap[j][i] = temp;
            }
        }

//        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(1), 10));
//        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(2), 15));
//        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(3), 20));
//
//        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(0), 10));
//        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(2), 35));
//        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(3), 20));
//
//        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(0), 15));
//        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(1), 35));
//        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(3), 30));
//
//        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(0), 20));
//        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(1), 20));
//        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(2), 30));

//

        /* Calculate all pairs shortest path problem */
//        graph.allPairsShortestPath();

//        graph.printJust();

//        for (Vertex v : vertices) {
//            System.out.println("Distance to " + v + ": " + v.minDistance);
//            List<Vertex> path = map.getShortestPathTo(v);
//            System.out.println("Path: " + path);
//        }

//        for(int i = 0; i < graph.n; i++){
//            for(int j = 0; j < graph.n; j++){
//                System.out.print(graph.distanceMap[i][j] + "  ");
//            }
//            System.out.println("");
//        }
    }

    public synchronized double printTour(){
        int i = 0;
        int current = 0;
        double totDist = 0d;
        Boolean[] visit = new Boolean[100];
        Arrays.fill(visit, false);
        System.out.print("0");
        visit[0] = true;
        for(; i < n; i++){
            double max = -1;
            int v = 0;
            for(int j = 0; j < n; j++){
                if(j != current && !visit[j]){
                    if(pheromoneMap[current][j] > max){
                        max = pheromoneMap[current][j];
                        v = j;
                    }
                }
            }
            totDist += graph.distanceMap[current][v];
            if(v != 0)visit[v] = true;
            current = v;

            System.out.print(" => " + v);
        }
        System.out.println("");
        return totDist;
    }

    public synchronized void printPheromoneMap(){
        for(int i = 0; i < Graph.n; i++){
            for(int j = 0; j < Graph.n; j++){
                System.out.print(pheromoneMap[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}
