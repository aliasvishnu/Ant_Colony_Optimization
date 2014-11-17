import java.util.*;

public class Graph{
    public double[][] distanceMap;
    public double[][] pheromoneMap;
    public List<Vertex> vertices;
    public static int n;
    public static final double ALPHA = -0.2d;
    public static final double BETA = 9.6d;
    public static final double RHO = 0.05d;
    public static final double DELTA = 0.1d;
    public static Graph graph = null;

    public Graph(){
        this.distanceMap = new double[100][100];
        this.pheromoneMap = new double[100][100];

        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                this.distanceMap[i][j] = -1;
                this.pheromoneMap[i][j] = 0.8d;
            }
        }

        this.n = 4;
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

    public void printJust(){
        System.out.println(Graph.getGraph().vertices.toString());
    }

    public void startGraph() {
        Graph graph = Graph.getGraph();
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the number of vertices and edges");
//        n = input.nextInt();
//        m = input.nextInt();

        graph.n = 4;
        graph.vertices = Collections.synchronizedList(new ArrayList<Vertex>());

        for(int i = 0; i < graph.n; i++){
            graph.vertices.add(new Vertex(i));
        }

        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(1), 10));
        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(2), 15));
        graph.vertices.get(0).adjacencies.add(new Edge(graph.vertices.get(3), 20));

        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(0), 10));
        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(2), 35));
        graph.vertices.get(1).adjacencies.add(new Edge(graph.vertices.get(3), 20));

        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(0), 15));
        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(1), 35));
        graph.vertices.get(2).adjacencies.add(new Edge(graph.vertices.get(3), 30));

        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(0), 20));
        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(1), 20));
        graph.vertices.get(3).adjacencies.add(new Edge(graph.vertices.get(2), 30));


//        System.out.println(graph.vertices.toString());

//        for(int i = 0; i < m; i++){
//            x = input.nextInt();
//            y = input.nextInt();
//            weight = input.nextDouble();
//            vertices.get(x).adjacencies.add(new Edge(vertices.get(y), weight));
//            vertices.get(y).adjacencies.add(new Edge(vertices.get(x), weight));
//        }

        /* Calculate all pairs shortest path problem */
        graph.allPairsShortestPath();

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

    public synchronized void printTour(){
        int i = 0;
        int current = 0;
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
            if(v != 0)visit[v] = true;
            current = v;
            System.out.print(" => " + v);
        }
        System.out.println("");
    }
}
