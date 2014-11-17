/**
 * Created by Sriram Ravindran on 9/9/2014.
 */
public class Edge {
    public final Vertex target;
    public final double weight;

    public Vertex getTarget(){
        return target;
    }

    public Edge(Vertex argTarget, double argWeight){
        target = argTarget; weight = argWeight;
    }
}