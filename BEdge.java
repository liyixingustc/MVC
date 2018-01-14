// edge class for bnb algorithm
// integer represents the vertex id
// contains getVertex of an edge, two edge whether equals, whether edge contains certain vertex
public class BEdge {
    int v1;
    int v2;
    
    public int[] getTwoVertex()
    {
        return new int[]{v1, v2};
    }

    public BEdge(int v1, int v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public BEdge(BEdge e) {
        this.v1 = new Integer(e.v1);
        this.v2 = new Integer(e.v2);
    }
    
    public int getVertex(int v)
    {
        return v == v1 ? v2 : v1;
    }

    public int getVertex()
    {
        return v1;
    }

    public boolean contains(int i) {
        return((v1 == i) || (v2 == i));
    }
        

    @Override
    public int hashCode(){
        return this.v1 * 100 + this.v2 * 50;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof BEdge) {
            BEdge e = (BEdge) o;
            return ((this.v1 == e.v1  && e.v2 == this.v2) || 
                    (this.v1 == e.v2 && this.v2 == e.v1));
        } else {
            return false;
        }
    }
    public String toString() {
        return ( this.v1 + " connects with " + this.v2);
    }

}