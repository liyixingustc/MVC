import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;


public class LS_SA {
	//Define Output Path
		private static String OutputPath;
		private static String OutputTracePath;
		private static PrintWriter Output;
		private static PrintWriter OutputTrace;
		
		public static void Simulated_Annealing(String inputFile, String method, int cutoff, int randomSeed) throws IOException{
			String myDir = System.getProperty("user.dir");
			String File_Path = myDir+"/Data/" +inputFile + ".graph";
			OutputPath = myDir+"/Output/SA_Output/"+inputFile +"_" +method +"_"+Integer.toString(cutoff)+"_"+ Integer.toString(randomSeed)+".sol";
			OutputTracePath = myDir+"/Output/SA_Output/"+inputFile +"_" +method +"_" +Integer.toString(cutoff)+"_"+Integer.toString(randomSeed)+".trace";
			System.out.println(OutputPath);
			Output = new PrintWriter(OutputPath);
			OutputTrace = new PrintWriter(OutputTracePath);
			
			MVC_read newrun = new MVC_read();
			//Parse the Graph
			EdgeWeightedGraph G = newrun.parseEdges(File_Path); // G: bag structure [][][]...[]
			ArrayList<Integer> vc = sa_calculate(G,cutoff,randomSeed);
			
			Output.printf("%d%n",vc.size());
			for (int i = 0; i < vc.size(); i++) {
				Output.printf("%d",vc.get(i));
				if (i<vc.size()-1) {
					Output.printf(",");
				}
			}
			Output.close();
			OutputTrace.close();
			
		}
		
		public static ArrayList<Integer> sa_calculate(EdgeWeightedGraph G, double cutoff, int seed){
			long start_time = System.currentTimeMillis();
			long end_time;
			float run_time =0;
			double temp = 10000;
			double absTemp = 0.00001;
			double cooling = 0.9999;
			float delta = 0;
			int[] para = new int[1];
			int[] check = new int[1];
			Random rand =new Random();
            rand.setSeed(seed);
			
			Bag<Edge> edges = new Bag<Edge>();
			for(int i=0;i<G.V;i++) {
				 for(Edge e : G.adj[i]) {
					 edges.add(e);
				 }
			}
			
			Bag<Integer> vc_initial = initialVC(edges, seed);  
			//Sort the vertex bag by degree
			Bag<Integer> vc_sort = sortVertex(G,vc_initial);
			ArrayList<Integer> vc_cur = new ArrayList<Integer>();
			for(Integer v:vc_sort) {
				vc_cur.add(v);
			}
			int cost_cur = vc_cur.size();
		    //System.out.println(cost_cur);
			
			
			while (temp>absTemp && run_time <cutoff) {
            
				 ArrayList<Integer> vc_neighbor= searchNeighbor(G,vc_cur,rand,para,check);
				 if(para[0] !=-1) {
					 delta = para[0]-cost_cur;
					 
					 if((cost_cur>0 && Math.exp(-Math.abs(delta)/temp)>rand.nextDouble())||(delta<0)) {
						 vc_cur = vc_neighbor;
						 cost_cur = para[0]; 
					 }
				 }
				 temp*=cooling;
				 end_time = System.currentTimeMillis();
				 run_time = (end_time-start_time)/1000F;
				 
				 if (check[0]==1) {
					 OutputTrace.printf("%.3f, %d%n", run_time, vc_cur.size());
					 
				 }
				 
			}
			return vc_cur;
	}
		public static ArrayList<Integer> searchNeighbor(EdgeWeightedGraph G, ArrayList<Integer> vc, Random rand, int[]para,int[] check){
			int k=1;
			ArrayList<Integer> vc_copy = new ArrayList<Integer>(vc);
			int index = rand.nextInt(vc_copy.size());
			int vertex = vc_copy.get(index);
			ArrayList<Edge> curr = new ArrayList<Edge>();
			for (Edge e:G.adj[vertex]) {
				curr.add(e);
			}
			for(int j =0; j<G.adj[vertex].size();j++) {
				if(!vc_copy.contains(curr.get(j).w)) {
					k=0;
					break;
				}
			}
			
			if(k==1) {
				vc_copy.remove(index);
				para[0] = G.adj[vertex].size();
			}
			else {
				para[0] =-1;
			}
			
			check[0] = k;
			return vc_copy;
		}
		
		public static Bag<Integer> initialVC (Bag<Edge> edges, int seed){
			long start_time=System.currentTimeMillis();
			long end_time;
			float run_time= 0;
			
			Bag<Integer> initial_vc = new Bag<Integer>();
			ArrayList<Edge> copy_edges = new ArrayList<Edge>();
			for (Edge e:edges) {
				copy_edges.add(e);
			}
		
			Edge Temp;
			int Position = seed % copy_edges.size();
		//	System.out.println(copy_edges.size());
			while(!copy_edges.isEmpty()) {
				Temp = copy_edges.get(Position);
				initial_vc.add(Temp.v);
				initial_vc.add(Temp.w);
				deleteVertex(copy_edges,Temp.v);
				deleteVertex(copy_edges,Temp.w);
				if(Position>=copy_edges.size()) {
					Position = 0;
				}
			}
			end_time = System.currentTimeMillis();
			run_time = (end_time-start_time)/1000F;
			OutputTrace.printf("%.3f, %d%n", run_time, initial_vc.size());
			return initial_vc;
		}
		
		 public static void deleteVertex(ArrayList<Edge> edge, int a) {
			  ArrayList<Edge> tempEdge = new ArrayList<Edge>(edge);
			  for(Edge e: tempEdge) {
				if(e.v==a || e.w==a) {
					edge.remove(e);
				}
				}
			}
		
		public static Bag<Integer> sortVertex(EdgeWeightedGraph G, Bag<Integer> vc){
			Bag<Integer> vc_sorted = new Bag<Integer>();
			ArrayList<Integer> vc_copy = new ArrayList<Integer>();
			for(Integer v: vc) {
				vc_copy.add(v);
			}
			
			Integer Temp =vc_copy.get(0);
			while(!vc_copy.isEmpty()) {
				Temp =vc_copy.get(0);
			for(int i=0;i<vc_copy.size();i++) {
				if(G.adj[vc_copy.get(i)].size()<G.adj[Temp].size()) {
					Temp=vc_copy.get(i);
				}
			}
			vc_sorted.add(Temp);
			vc_copy.remove(Temp);
			}	
			return vc_sorted;
		}
}

