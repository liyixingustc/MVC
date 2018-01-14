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


public class MVC{

	public static void main(String []args)throws IOException{
		String inputFile, method;
		int cutoff, randomSeed;
		
		// Judge whether the input command is correct
		if(args.length !=4) {
			System.out.println("Invalid parameter, please input again.");
			return;
		}
		if(args[0].equals("-star.graph") || args[0].equals("-star2.graph") || args[0].equals("-jazz.graph") || args[0].equals("-power.graph") || 
		   args[0].equals("-karate.graph") || args[0].equals("-football.graph") || args[0].equals("-hep-th.graph") || args[0].equals("-email.graph")||
		   args[0].equals("-as-22july06.graph") || args[0].equals("-delaunay_n10.graph") || args[0].equals("-netscience.graph")) {
			inputFile = args[0].substring(1,args[0].length()-6);		
		}
		else {
			System.out.println("Please input a existing file.");
			return;
		}
		if(!args[1].equals("-LS1") && !args[1].equals("-LS2") && !args[1].equals("-CH") && !args[1].equals("-BnB")) {
			System.out.println("Please input a valid method.");
			return;
		}
		else {
			method = args[1].substring(1,args[1].length());
		}
		
		if(Integer.parseInt(args[2].substring(1,args[2].length()))<=0) {
			System.out.println("The cut-off time should be positive.");
			return;
		}
		else {
			cutoff=Integer.parseInt(args[2].substring(1,args[2].length()));
		}
		
		if(Integer.parseInt(args[3].substring(1,args[3].length()))<0) {
			System.out.println("The random seed should be more than 0.");
			return;
		}
		else {
			randomSeed=Integer.parseInt(args[3].substring(1,args[3].length()));
		}

		// HC main fucntion
		if(args[1].equals("-LS1")){
			LS_HillClimbing newClimb = new LS_HillClimbing();
			newClimb.Hill_Climbing(inputFile, method, cutoff, randomSeed);
		}
		if(args[1].equals("-LS2")){
			LS_SA newSA = new LS_SA();
			newSA.Simulated_Annealing(inputFile, method, cutoff, randomSeed);
		}
		if(args[1].equals("-CH")){
			long start_DP = System.nanoTime();
			//System.out.println("CH");
			CH newApprox = new CH();
			newApprox.CH(inputFile, method, cutoff, randomSeed);
			long finish_DP = System.nanoTime();
			double DP_total = (finish_DP - start_DP)/1000000000.0;
			System.out.println(DP_total);
		}
		if(args[1].equals("-BnB")){
			//cutoff = 600;
			String myDir = System.getProperty("user.dir");
			String File_Path = myDir+"/Data/" +inputFile + ".graph";
			//System.out.println(file_Path);
			Solution sl = new Solution();
			BnBGraph g = sl.parseGraph(File_Path);
	        BnB bnb = new BnB(g, cutoff, inputFile);
	        bnb.run(g);
	        bnb.writeSolution();
	        bnb.traceWriter.close();
	        bnb.solWriter.close();
		}
		//Hill_Climbing(inputFile,method,cutoff,randomSeed);
	}


}