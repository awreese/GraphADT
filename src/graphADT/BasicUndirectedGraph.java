package graphADT;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * BasicUndirectedGraph<V,E> represents a basic mutable undirected multi-graph.
 * While it can store more advanced vertex and edge object types, this class
 * only supports the basic graph operations defined in graph interface. This
 * graph is capable of storing multiple edges between vertices, but doesn't
 * allow for duplicate edges.<br/>
 * 
 * <h5>Abstract Invariant:</h5>
 * <ul>
 * <li>foreach vertex v in graph, v is not null</li>
 * <li>foreach edge e between vertices, e is not null</li>
 * </ul>
 * 
 * @author Drew Reese
 *
 * @param <V> - data type to store as vertices
 * @param <E> - data type to store as edges
 * 
 */

public class BasicUndirectedGraph<V, E> implements Graph<V,E> {

    // DEBUGGING EXPENSIVE CHECKREP FLAG
    private static final boolean RUN_CHECKREP = false;

    /**
     * Edge class stores vertex pairing and edge value that defines an edge in
     * the parent graph.
     * 
     * While edge values e are allowed to be null, by definition the graph
     * vertices must not be null. The vertices are guaranteed to not be null as
     * this is enforced by the graph.
     * 
     * @author Drew Reese
     */
    private class Edge {

        private V v1;
        private V v2;
        private E e;

        public Edge(V v1, V v2, E e) {
            this.v1 = v1;
            this.v2 = v2;
            this.e = e;
        }
    }

    /* internal representation of basic undirected graph */
    private Set<V>    vertices;
    private Set<Edge> edges;

    // Abstraction Function:
    // A basic undirected graph is an ADT that contains both vertices and the
    // edges between them. This graph stores a set of vertices and a set of
    // edges.

    // Representation Invariant:
    // foreach vertex v in vertices, v != null
    // foreach edge e in edges, e != null

    public BasicUndirectedGraph() {
        this.vertices = new HashSet<V>();
        this.edges = new HashSet<Edge>();
        checkRep();
    }

    @Override
    public boolean addVertex(V v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException("Vertex value null");
        }

        boolean result = vertices.add(v);
        checkRep();
        return result;
    }

    @Override
    public boolean addEdge(V v1, V v2) throws NullPointerException {
        return addEdge(v1, v2, null);
    }

    @Override
    public boolean addEdge(V v1, V v2, E e) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        }
        
        boolean result = edges.add(new Edge(v1, v2, e));
        checkRep();
        return result;
    }

    @Override
    public boolean containsVertex(V v) {
        if (v == null) {
            return false;
        } else {
            return vertices.contains(v);
        }
    }

    @Override
    public boolean containsEdge(E e) {
        if (e == null) {
            return false;
        } else {
            for (Edge edge : edges) {
                if (edge.e.equals(e)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean containsEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        }
        
        // if either vertex is not in graph, return false
        if (!(containsVertex(v1) && containsVertex(v2))) {
            return false;
        } else {
            for (Edge e : edges) {
                if ((e.v1.equals(v1) && e.v2.equals(v2)) ||
                    (e.v1.equals(v2) && e.v2.equals(v1))   ) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public Set<? extends V> vertexSet() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<? extends E> edgeSet() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<? extends E> edgeSet(V v) throws NullPointerException {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<? extends E> edgeSet(V v1, V v2) throws NullPointerException {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E getEdge(V v1, V v2) throws NullPointerException {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> edges)
            throws NullPointerException {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<E> removeAllEdges(V v1, V v2) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices)
            throws NullPointerException {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeEdge(E e) {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E removeEdge(V v1, V v2) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeVertex(V v) {

        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Checks that the rep invariant holds
     */
    private void checkRep() {

        if (RUN_CHECKREP) {
            // check vertices
            for (V v : this.vertices) {
                assert (v != null) : "Null vertex";
            }

            // check edges
            for (Edge e : this.edges) {
                assert (e != null) : "Null edge";
            }
        }
    }

}
