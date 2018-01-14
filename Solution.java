import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// helper file to parse graph for bnb and test/run bnb
public class Solution{

    public static BnBGraph parseGraph (String file_path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file_path));
        String line = br.readLine();
        String[] split_array = line.split(" ");
        int numVertex = Integer.parseInt(split_array[0]);
        int numEdges = Integer.parseInt(split_array[1]);

        ArrayList<BEdge> edges = new ArrayList<>();
        ArrayList<Integer> vertex = new ArrayList<>();
        HashSet<Integer>[] adjVertex = new HashSet[numVertex + 1];
        HashSet<BEdge>[] listArrOfEdge = new HashSet[numVertex + 1];


        for (int i = 1; i <= numVertex; i++){
            line = br.readLine();
            if(!line.equals("")) {
                split_array = line.split(" ");
                for (String v : split_array) {
                    BEdge newEdge = new BEdge(i, Integer.parseInt(v));

                    if(listArrOfEdge[i] == null)
                    {
                        listArrOfEdge[i] = new HashSet<BEdge>();
                    }
                    listArrOfEdge[i].add(newEdge);
                    edges.add(newEdge);

                    // ArrayList<Integer> al1 = adjVertex[i];
                    if(adjVertex[i] == null)
                    {
                        adjVertex[i] = new HashSet<Integer>();
                    }

                    // ArrayList<Integer> al2 = adjVertex[Integer.parseInt(v)];
                    if(adjVertex[Integer.parseInt(v)] == null)
                    {
                        adjVertex[Integer.parseInt(v)] = new HashSet<Integer>();
                    }
                    adjVertex[i].add(Integer.parseInt(v));
                    adjVertex[Integer.parseInt(v)].add(i);
                }
            }
        }
        // add to the vertex array
        for (int i = 1; i <= numVertex; i++){
            vertex.add(i);
        }
        BnBGraph g = new BnBGraph(numVertex, numEdges, edges, vertex, adjVertex, listArrOfEdge);
        br.close();
        return g;
    }


}