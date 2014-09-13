import java.util.*;

public class Dijkstra{
    int[][] distanceMap;

    public static void computePaths(Vertex source){
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

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
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int[][] distanceMap = new int[100][100];
        int n, m;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of vertices and edges");
//        n = input.nextInt();
//        m = input.nextInt();

        Vertex v0 = new Vertex("Redvile");
        Vertex v1 = new Vertex("Blueville");
        Vertex v2 = new Vertex("Greenville");
        Vertex v3 = new Vertex("Orangeville");
        Vertex v4 = new Vertex("Purpleville");
        List<Vertex> vertices = new ArrayList<Vertex>();

        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);

        v0.adjacencies.add(new Edge(v1, 5));
        v0.adjacencies.add(new Edge(v2, 10));
        v0.adjacencies.add(new Edge(v3, 8));
        v1.adjacencies.add(new Edge(v0, 5));
        v1.adjacencies.add(new Edge(v2, 3));
        v1.adjacencies.add(new Edge(v4, 7));
        v2.adjacencies.add(new Edge(v0, 10));
        v2.adjacencies.add(new Edge(v1, 3));
        v3.adjacencies.add(new Edge(v0, 8));
        v3.adjacencies.add(new Edge(v4, 2));
        v4.adjacencies.add(new Edge(v1, 7));
        v4.adjacencies.add(new Edge(v3, 2));

        computePaths(v0);
        for (Vertex v : vertices) {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            List<Vertex> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
}
