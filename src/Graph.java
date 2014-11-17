import java.util.*;

public class Graph{
    public double[][] distanceMap;
    public double[][] pheromoneMap;
    public List<Vertex> vertices;
    public static int n;
    public static final double ALPHA = -0.2d;
    public static final double BETA = 9.6d;

    public Graph(){
        this.distanceMap = new double[100][100];
        this.pheromoneMap = new double[100][100];

        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                this.distanceMap[i][j] = -1;
                this.pheromoneMap[i][j] = 0.8d;
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

    public int getNoOfCities(){
        return this.n;
    }
    public  List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public void allPairsShortestPath(){
        for(int i = 0; i < n; i++) {
            this.dijkstra(vertices.get(i));
            for(int j = 0; j < n; j++){
                vertices.get(j).reset();
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the number of vertices and edges");
//        n = input.nextInt();
//        m = input.nextInt();

        graph.n = 5;

        graph.vertices = new ArrayList<Vertex>();
        for(int i = 0; i < graph.n; i++){
            graph.vertices.add(new Vertex(i));
        }

        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(1), 5));
        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(2), 10));
        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(3), 8));

        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(0), 5));
        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(2), 3));
        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(4), 7));

        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(0), 10));
        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(1), 3));

        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(0), 8));
        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(4), 2));

        graph.vertices.get(4).adjacencies.add(new Edge(graph.vertices.get(1), 7));
        graph.vertices.get(4).adjacencies.add(new Edge(graph.vertices.get(3), 2));

//        for(int i = 0; i < m; i++){
//            x = input.nextInt();
//            y = input.nextInt();
//            weight = input.nextDouble();
//            vertices.get(x).adjacencies.add(new Edge(vertices.get(y), weight));
//            vertices.get(y).adjacencies.add(new Edge(vertices.get(x), weight));
//        }

        /* Calculate all pairs shortest path problem */
        graph.allPairsShortestPath();

//        for (Vertex v : vertices) {
//            System.out.println("Distance to " + v + ": " + v.minDistance);
//            List<Vertex> path = map.getShortestPathTo(v);
//            System.out.println("Path: " + path);
//        }

        for(int i = 0; i < graph.n; i++){
            for(int j = 0; j < graph.n; j++){
                System.out.print(graph.distanceMap[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}