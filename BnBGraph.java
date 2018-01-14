import java.util.*;

// BnBGraph class especially for bnb
// add usedVertex, unusedVertex, coveredEdges and unCoveredEdges for easier bnb application

public class BnBGraph{
    int vertexNum;
    int edgeNum;
    ArrayList<BEdge> edges;
    ArrayList<Integer> vertex;

    // adjacent vertex list array
    HashSet<Integer>[] adjVertex;
    HashSet<BEdge>[] listArrOfEdge;

    //used vertex and unusedvertex to keep track of 
    ArrayList<Integer> usedVertex;
    ArrayList<Integer> unusedVertex;
    //coveredEdges and unCovered edges to keep track of 
    ArrayList<BEdge> coveredEdges;
    ArrayList<BEdge> unCoveredEdges;


    public BnBGraph(int v, int e, ArrayList<BEdge> edges, ArrayList<Integer> vertex, HashSet<Integer>[] adjVertex, HashSet<BEdge>[] listArrOfEdge) {
        this.vertexNum = v;
        this.edgeNum   = e;
        this.listArrOfEdge = Arrays.copyOf(listArrOfEdge, listArrOfEdge.length);
        this.adjVertex     = Arrays.copyOf(adjVertex, adjVertex.length);
        this.edges         = new ArrayList<BEdge>(edges);
        this.vertex        = new ArrayList<Integer>(vertex);

        this.usedVertex = new ArrayList<Integer>();
        this.unusedVertex = new ArrayList<Integer>();
        for (Integer i: vertex) {
            this.unusedVertex.add(new Integer(i));
        }
        
        this.coveredEdges = new ArrayList<BEdge>();
        this.unCoveredEdges = new ArrayList<BEdge>();

        unCoveredEdges = edges;

        Collections.sort(unCoveredEdges, new Comparator<BEdge>(){
            public int compare(BEdge b1, BEdge b2)
            {
                return b1.v1 - b2.v1;
            }
        });

    }
    public BnBGraph(BnBGraph g) {
        this.vertexNum = g.vertexNum;
        this.edgeNum = g.edgeNum;
        
        this.unCoveredEdges = new ArrayList<BEdge>();
        for (BEdge e: g.unCoveredEdges) {
            this.unCoveredEdges.add(new BEdge(e));
        }

        this.coveredEdges = new ArrayList<BEdge>();
        for (BEdge e: g.coveredEdges) {
            this.coveredEdges.add(new BEdge(e));
        }

        this.vertex = new ArrayList<Integer>();
        for (Integer i: g.vertex) {
            this.vertex.add(new Integer(i));
        }

        this.edges = new ArrayList<BEdge>();
        for (BEdge e: g.edges) {
            this.edges.add(new BEdge(e));
        }

        this.usedVertex = new ArrayList<Integer>();
        for (Integer v: g.usedVertex) {
            this.usedVertex.add(new Integer(v));
        }

        this.unusedVertex = new ArrayList<Integer>();
        for (Integer u: g.unusedVertex) {
            this.unusedVertex.add(new Integer(u));
        }
    }

    public void setCntEdges(int e) {
        this.edgeNum = e;
    }
    public int cntEdges(){
        return edgeNum;
    }
    public int cntVetex(){
        return vertexNum;
    }
    public ArrayList<BEdge> getEdges(){
        return this.edges;
    }
    public HashSet<BEdge>[] getAdj(){
        return this.listArrOfEdge;
    }
    public ArrayList<Integer> getVertex()
    {
        return this.vertex;
    }
    public ArrayList<Integer> getAdjVertex(int v){
        // System.out.println("vertex: ");
        // System.out.println(v);
        ArrayList<Integer> adjVertex = new ArrayList<>();
        for (BEdge e : listArrOfEdge[v]){
            adjVertex.add(e.getVertex(v));
        }
        return adjVertex;
    }
    public int getDegreeOfVertex(int v){
        return this.listArrOfEdge[v].size();
    }

    public int getHighestDegree(HashSet<Integer>[] adjV) {
        Integer res = null;
        int maxDegree = 0;
        for (Integer v: unusedVertex) {
            // System.out.println("error");
            // System.out.println(v);
            HashSet<Integer> temp = new HashSet<Integer>();

            // System.out.println(adjVertex[v]);

            for (Integer i: adjV[v]) {
                // System.out.println(i);
                temp.add(new Integer(i));
            }
            temp.removeAll(usedVertex);
            
            if (temp.size() > maxDegree) {
                res = new Integer(v);
                // adjV[res] = temp;
                maxDegree = temp.size();
            }
        }
        // System.out.println(res);
        return res;
    }
    public static int getLowerBound(BnBGraph g) {
        // System.out.println("right");

        ArrayList<Integer> output = new ArrayList<Integer>();
        ArrayList<BEdge> unused = new ArrayList<BEdge>();
        Collections.sort(g.unCoveredEdges, new Comparator<BEdge>(){
            public int compare(BEdge b1, BEdge b2)
            {
                return b1.v1 - b2.v1;
            }
        });
        for (BEdge a: g.unCoveredEdges) {
            unused.add(a);
        }
        Collections.sort(unused, new Comparator<BEdge>(){
            public int compare(BEdge b1, BEdge b2)
            {
                return b1.v1 - b2.v1;
            }
        });
        // System.out.println("inside");
        // System.out.println(unused.size());
        int count = 0;
        while (unused.size() != 0) {
            count++;
            BEdge toRemove = unused.get(0);
            int[] arr = toRemove.getTwoVertex();
            // System.out.println(arr);
            output.add(arr[0]);
            output.add(arr[1]);
            ArrayList<BEdge> edgesToRemove = new ArrayList<BEdge>();
            for (BEdge e: unused) {
                if ((e.contains(arr[0])) || (e.contains(arr[1]))) {
                    edgesToRemove.add(e);
                }
            }

            unused.removeAll(edgesToRemove);
        }

        return output.size();
        
    }

   
    public Integer changeVertexToUsed(Integer vertex)
    {
        this.usedVertex.add(vertex);
        this.unusedVertex.remove(vertex);

        ArrayList<BEdge> tempEdges = new ArrayList<BEdge>();
        for (BEdge e : this.unCoveredEdges) {
            if (e.contains(vertex)) {
                tempEdges.add(e);
            }
        }
        unCoveredEdges.removeAll(tempEdges);
        // System.out.println(unCoveredEdges);
        coveredEdges.addAll(tempEdges);
        // System.out.println(coveredEdges);
        return vertex;
    }
    public String toString()
    {
        return String.valueOf(this.vertexNum);
    }

}