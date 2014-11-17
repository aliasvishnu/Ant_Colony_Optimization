import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sriram Ravindran on 11/17/2014.
 */
public class Ant {
    public List<Integer> memory;
    public int id;
    Boolean[] visit;
    public int n;

    public Ant(int aid, int n){
        memory = new ArrayList<Integer>();
        this.id = aid;

        visit = new Boolean[100];
        Arrays.fill(visit, Boolean.FALSE);

        this.n = n;
    }

    public void walk(){
        int start = 0;


    }

}
