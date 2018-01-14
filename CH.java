/*
CSE6140 HW1
This is an example of how your experiments should look like.
Feel free to use and modify the code below, or write your own experimental code, 
as long as it produces the desired output.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;


public class CH{

	private static String OutputPath;
	//private static String OutputTracePath;
	private static PrintWriter Output;
	//private static PrintWriter OutputTrace;

	public static void CH(String inputFile, String method, int cutoff, int randomSeed) throws IOException{
		String myDir = System.getProperty("user.dir");
		String File_Path = myDir+"/Data/" +inputFile + ".graph";
		OutputPath = myDir+"/Output/CH_Output/"+inputFile +"_" +method +"_"+Integer.toString(cutoff)+"_"+ Integer.toString(randomSeed)+".sol";
		//OutputTracePath = myDir+"/Output/HillClimbing_Output/"+inputFile +"_" +method +"_" +Integer.toString(cutoff)+"_"+Integer.toString(randomSeed)+".trace";
		Output = new PrintWriter(OutputPath);
		MVC_read newrun = new MVC_read();
		//Parse the Graph

		EdgeWeightedGraph G = newrun.parseEdges(File_Path); // G: bag structure [][][]...[]
		//Calculate Minimum Vertex Cover
		ArrayList<Integer> vc = constructiveHeuristic(G, cutoff);
	    //System.out.println(vc.size());
		//Output solution file
		Output.printf("%d%n",vc.size());
		for (int i = 0; i < vc.size(); i++) {
			Output.printf("%d",vc.get(i));
			if (i<vc.size()-1) {
				Output.printf(",");
			}
		}
		Output.close();

	}

    public static ArrayList<Integer>  constructiveHeuristic(EdgeWeightedGraph G, int cutoff){
	ArrayList<Integer> VC = new ArrayList<Integer>();
	int graphsize = G.V();
	int visitedver = 0;
	int i = 0, j = 0;
	int v = 0, w = 0;
	int [] returnvalue = new int [graphsize];
	boolean[] visited = new boolean [graphsize];
	//	Arrays.fill(visited, false);
	while(visitedver <  graphsize - 1){
	    //choose an edge
	    outerloop:
	    for(i = 1; i < graphsize; i++){
		for(Edge cEdge : G.adj[i]){
		    //unvisited
		    if(cEdge.weight() == 1 && !visited[cEdge.other(i)]){
			v = cEdge.either();
			w = cEdge.other(v);
			//System.out.println("correct "+v+" "+w);
			break outerloop;
		    }
		}
	    }
	    if(i == graphsize){
		break;
	    }
	    
	    //i and j chosen
	    //enlist them
	    VC.add(v);
	    VC.add(w);
	    //clear up
	    visitedver = visitedver + 2;
	    visited[v] = true;
	    visited[w] = true;
	    for(Edge cEdge : G.adj[v]){
		cEdge.weight = 0;
	    }
	    for(Edge cEdge : G.adj[w]){
		cEdge.weight = 0;
	    }
	}
	return VC;
    }


}
