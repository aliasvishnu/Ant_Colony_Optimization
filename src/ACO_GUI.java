/**
 * Created by sainath on 19/11/14.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
public class ACO_GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    GUI_Graph graph;
    public ACO_GUI() {
        try {
            graph=new GUI_Graph("Graphs/1.txt");
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setTitle("ACO");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);

    }
    @Override
    public void paint(Graphics g){
        synchronized (rootPane) {
            g.setColor(Color.red);
            for(int i=1;i<=graph.n;i++){
                g.drawOval((int)graph.x[i]-15,(int)graph.y[i]-15,30,30);
                g.fillOval((int)graph.x[i]-15,(int)graph.y[i]-15,30,30);
                try {
                    rootPane.wait(400);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            g.setColor(Color.black);
            for(int i=1;i<=graph.n;i++){
                for(int j=1;j<=graph.n;j++){
                    if(graph.edges[i][j]>0.0){
                        g.drawLine((int)graph.x[i],(int)graph.y[i],(int)graph.x[j],(int)graph.y[j]);
                        try {
                            rootPane.wait(400);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
    public static void main(String[] args) {

        ACO_GUI ex = new ACO_GUI();
        for(int i=1;i<=ex.graph.n;i++){
            for(int j=1;j<=ex.graph.n;j++){
                System.out.print(ex.graph.distance[i][j]+" ");
            }
            System.out.println();
        }
        ex.setVisible(true);
    }
}