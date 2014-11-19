/**
 * Created by sainath on 19/11/14.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
public class GUI_Graph {
    int n;
    double x[];
    double y[];
    double distance[][];
    double pheromone[][];
    double edges[][];
    public GUI_Graph(String filename) throws NumberFormatException,FileNotFoundException,IOException{
        FileInputStream in=new FileInputStream(filename);
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        n=Integer.parseInt(br.readLine());
        x=new double[n+1];
        y=new double[n+1];
        distance=new double[n+1][n+1];
        pheromone=new double[n+1][n+1];
        edges=new double[n+1][n+1];
        for(int i=1;i<=n;i++){
            String s[]=br.readLine().split(" ");
            x[i]=Double.parseDouble(s[0]);
            y[i]=Double.parseDouble(s[1]);
        }

        for(int i=1;i<=n;i++){
            String s[]=br.readLine().split(" ");
            for(int j=1;j<=n;j++){
                distance[i][j]=Double.parseDouble(s[j-1]);
                edges[i][j]=distance[i][j];
                if(distance[i][j]>0.00){
                    pheromone[i][j]=0.8d;
                }
            }
        }

        for(int k=1;k<=n;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    if(distance[i][k]+distance[k][j]<distance[i][j]){
                        distance[i][j]=distance[i][k]+distance[k][j];
                    }
                }
            }
        }
        br.close();
    }

}

