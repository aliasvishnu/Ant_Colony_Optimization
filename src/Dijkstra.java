import java.util.*;

public class Dijkstra{
    double[][] distanceMap;

    public void computePaths(Vertex source){
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
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

    public  List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Dijkstra map = new Dijkstra();
        map.distanceMap = new double[100][100];
        int n, m, x, y;
        double weight;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of vertices and edges");
//        n = input.nextInt();
//        m = input.nextInt();
        n = 5;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                map.distanceMap[i][j] = -1;
            }
        }

        List<Vertex> vertices = new ArrayList<Vertex>();

        for(int i = 0; i < n; i++){
            vertices.add(new Vertex(i));
        }

        vertices.get(0).adjacencies.add(new Edge(vertices.get(1), 5));
        vertices.get(0).adjacencies.add(new Edge(vertices.get(2), 10));
        vertices.get(0).adjacencies.add(new Edge(vertices.get(3), 8));

        vertices.get(1).adjacencies.add(new Edge(vertices.get(0), 5));
        vertices.get(1).adjacencies.add(new Edge(vertices.get(2), 3));
        vertices.get(1).adjacencies.add(new Edge(vertices.get(4), 7));

        vertices.get(2).adjacencies.add(new Edge(vertices.get(0), 10));
        vertices.get(2).adjacencies.add(new Edge(vertices.get(1), 3));

        vertices.get(3).adjacencies.add(new Edge(vertices.get(0), 8));
        vertices.get(3).adjacencies.add(new Edge(vertices.get(4), 2));

        vertices.get(4).adjacencies.add(new Edge(vertices.get(1), 7));
        vertices.get(4).adjacencies.add(new Edge(vertices.get(3), 2));

//        for(int i = 0; i < m; i++){
//            x = input.nextInt();
//            y = input.nextInt();
//            weight = input.nextDouble();
//            vertices.get(x).adjacencies.add(new Edge(vertices.get(y), weight));
//            vertices.get(y).adjacencies.add(new Edge(vertices.get(x), weight));
//        }

        for(int i = 0; i < n; i++) {
            map.computePaths(vertices.get(i));
            for(int j = 0; j < n; j++){
                vertices.get(j).reset();
            }
        }
//        for (Vertex v : vertices) {
//            System.out.println("Distance to " + v + ": " + v.minDistance);
//            List<Vertex> path = map.getShortestPathTo(v);
//            System.out.println("Path: " + path);
//        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(map.distanceMap[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}
