import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
// class to run branch and bound


public class BnB {
    long timeGone;
    long start;
    int cutoff;
    int lowerBound;
    String traceName;
    String solName;
    FileWriter traceWriter;
    FileWriter solWriter;
    ArrayList<Integer> resList;
    HashSet<Integer>[] adjV;


    public BnB(BnBGraph graph, int cutoff, String filename) throws IOException {
        //resList contains the candidates
        resList   = new ArrayList<Integer>();
        solName   =  System.getProperty("user.dir") + "/Output/BnB_Output/" + filename + "_BnB_" + Integer.toString(cutoff) + ".sol";
        traceName =  System.getProperty("user.dir") + "/Output/BnB_Output/" + filename + "_BnB_" + Integer.toString(cutoff) + ".trace";
        
        traceWriter = new FileWriter(traceName);
        solWriter   = new FileWriter(solName);
        adjV        = graph.adjVertex;
        lowerBound  = BnBGraph.getLowerBound(graph);
        // start record the time
        this.cutoff = (cutoff * 1000);
        start = System.currentTimeMillis();

    }
    
    // recusively call this function // branch and bound
    public void run(BnBGraph g) throws IOException {

        timeGone = System.currentTimeMillis() - start;
        if (timeGone - cutoff >= 0) {
            System.out.println("Time's Up");
            writeSolution();
            traceWriter.close();
            solWriter.close();
            System.exit(0);
        }

        if (g.usedVertex.size() >= lowerBound) {
            return;
        }
        
        if (g.unCoveredEdges.size() == 0) {
            resList = g.usedVertex;
            System.out.println("found it! the MVC of size " + resList.size());
            lowerBound = resList.size();
            writeTrace();
            return;
        }
        //return highest degree node 
        int vertex_to_include = g.getHighestDegree(adjV);
        // divide and conquer
        // left tree pick the highest degree vertex
        BnBGraph left = new BnBGraph(g);

        // right tree
        BnBGraph right = new BnBGraph(g);

        //decision 1: 
        // change the vertex for left graph
        left.changeVertexToUsed(vertex_to_include);
        //decision 2: 
        // right graph take neighbors
        for (Integer z: adjV[vertex_to_include]) {
            // for each vertex's neighbor, right graph is gonna take it
            right.changeVertexToUsed(z);  
        }
        int lowerboundForLeftGraph = BnBGraph.getLowerBound(left); 
        int lowerboundForRightGraph = BnBGraph.getLowerBound(right); 

        int rightVal = right.usedVertex.size() + lowerboundForRightGraph;
        int leftVal =  left.usedVertex.size() + lowerboundForLeftGraph;

        if (rightVal <= lowerBound && leftVal <= lowerBound) {
            if (leftVal < rightVal) {
                run(left);
                run(right);
            } else {
                run(right);
                run(left);
            }
        } else if (leftVal / 2 <= lowerBound) {
            run(left);
        } else if ( rightVal / 2 <= lowerBound){
            run(right);
        }
        return;                  
    }
        
    public void writeSolution() throws IOException {
        solWriter.append(Integer.toString(lowerBound) + "\n");
        StringBuilder vertex_ans = new StringBuilder();
        for (int i = 0; i < resList.size() - 1 ; i++) {
            vertex_ans.append(Integer.toString(resList.get(i)) + ", " );
        }
        vertex_ans.append(Integer.toString(resList.get(resList.size() - 1)));
        solWriter.append(vertex_ans.toString());
    }
    
    public void writeTrace() {
        long current = System.currentTimeMillis();
        double time = (current - start) / 1000.0;
        String trace_add = Double.toString(time) + ", " + Integer.toString(lowerBound);
        System.out.println(trace_add);
        try {
            traceWriter.append(trace_add + "\n");
        } catch (Exception e) {
        }
    }
}