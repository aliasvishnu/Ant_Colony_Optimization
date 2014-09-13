import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sriram Ravindran on 9/9/2014.
 */
public class Vertex implements Comparable<Vertex> {
    public final int id;
    //public Edge[] adjacencies;
    public List<Edge> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(int argId) {
        id = argId;
        adjacencies = new ArrayList<Edge>();
    }

    public String toString() {
        return new Integer(id).toString();
    }

    public int compareTo(Vertex other){
        return Double.compare(minDistance, other.minDistance);
    }
}