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


public class MVC_read{

	public EdgeWeightedGraph parseEdges(String input_file){
		try{
			//PrintWriter output;
			//output = new PrintWriter(output_file, "UTF-8");

			BufferedReader br = new BufferedReader(new FileReader(input_file));
			String line = br.readLine();
			String[] split = line.split(" ");
			int VerNum = Integer.parseInt(split[0]);
			int EdgeNum = Integer.parseInt(split[1]);
			EdgeWeightedGraph G = new EdgeWeightedGraph(VerNum+1);
			int j = 0;
			while ((line = br.readLine()) != null) {
				j++;
				if(!line.isEmpty()){
					split = line.split(" ");
					int splitLen = split.length;
					for (int i = 0; i < splitLen; i++){
						//System.out.println(j);
						int tempVer = Integer.parseInt(split[i]);
						Edge edgeTemp = new Edge(j, tempVer, 1);
						G.addEdge(edgeTemp);
					}
				}
			}

			//output.close();
			br.close();
			return G;
		}catch(IOException exce){
  			exce.printStackTrace();
  			throw new RuntimeException(exce);
		}
		//return new EdgeWeightedGraph(1);
	}


}