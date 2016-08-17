package graphADT;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
 * <li>foreach edge e between vertices in graph, e is not null</li>
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
    private static final boolean RUN_CHECKREP = true;

    /**
     * Edge class stores vertex pairing that defines an edge in the parent
     * graph.
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

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((e == null) ? 0 : e.hashCode());
            result = prime * result + ((v1 == null) ? 0 : v1.hashCode())
                    + ((v2 == null) ? 0 : v2.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof BasicUndirectedGraph.Edge)) {
                return false;
            }

            @SuppressWarnings("unchecked")
            Edge other = (Edge) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }

            if (!((v1.equals(other.v1) && v2.equals(other.v2))
                    || (v1.equals(other.v2) && v2.equals(other.v1)))) {
                return false;
            }

            if (e == null) {
                if (other.e != null) {
                    return false;
                }
            } else if (!e.equals(other.e)) {
                return false;
            }

            return true;
        }

        private BasicUndirectedGraph<?,?> getOuterType() {
            return BasicUndirectedGraph.this;
        }

    }

    /* internal representation of basic undirected graph */
    private Set<V>    vertexSet;
    private Set<Edge> edgeSet;

    // Abstraction Function:
    // A basic undirected graph is an ADT that contains both vertexSet and the
    // edges between them. This graph stores a set of vertexSet and a set of
    // edges.

    // Representation Invariant:
    // foreach vertex v in vertexSet, v != null
    // foreach edge e in edges, e != null
    //         vertex v1 in vertexSet
    //         vertex v2 in vertexSet

    public BasicUndirectedGraph() {
        this.vertexSet = new HashSet<V>();
        this.edgeSet = new HashSet<Edge>();
        checkRep();
    }

    @Override
    public boolean addVertex(V v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException("Vertex value null");
        }

        boolean result = vertexSet.add(v);
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
        if (!(vertexSet.contains(v1) && vertexSet.contains(v2))) {
            return false;
        }

        boolean result = edgeSet.add(new Edge(v1, v2, e));
        checkRep();
        return result;
    }

    @Override
    public boolean containsVertex(V v) {
        if (v == null) {
            return false;
        } else {
            return vertexSet.contains(v);
        }
    }

    @Override
    public boolean containsEdge(E e) {
        if (e == null) {
            return false;
        } else {
            for (Edge edge : edgeSet) {
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

        // if both vertexSet in graph, check for edge
        if (containsVertex(v1) && containsVertex(v2)) {
            for (Edge e : edgeSet) {
                if ((e.v1.equals(v1) && e.v2.equals(v2))
                        || (e.v1.equals(v2) && e.v2.equals(v1))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<? extends V> vertexSet() {
        return new HashSet<V>(vertexSet);
    }

    @Override
    public Set<? extends E> edgeSet() {
        Set<E> returnEdgeSet = new HashSet<E>();
        for (Edge edge : edgeSet) {
            returnEdgeSet.add(edge.e);
        }
        return returnEdgeSet;
    }

    @Override
    public Set<? extends E> edgeSet(V v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!containsVertex(v)) {
            return null;
        }

        Set<E> returnEdgeSet = new HashSet<E>();
        for (Edge edge : edgeSet) {
            if (edge.v1.equals(v) || edge.v2.equals(v)) {
                returnEdgeSet.add(edge.e);
            }
        }
        return returnEdgeSet;
    }

    @Override
    public Set<? extends E> edgeSet(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!(containsVertex(v1) && containsVertex(v2))) {
            return null;
        }

        Set<E> returnEdgeSet = new HashSet<E>();
        for (Edge edge : edgeSet) {
            if ((edge.v1.equals(v1) && edge.v2.equals(v2))
                    || (edge.v1.equals(v2) && edge.v2.equals(v1))) {
                returnEdgeSet.add(edge.e);
            }
        }
        return returnEdgeSet;
    }

    @Override
    public E getEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (containsVertex(v1) && containsVertex(v2)) {
            for (Edge edge : edgeSet) {
                if ((edge.v1.equals(v1) && edge.v2.equals(v2))
                        || (edge.v1.equals(v2) && edge.v2.equals(v1))) {
                    return edge.e;
                }
            }
        }
        return null;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> edges)
            throws NullPointerException {

        if (edges == null) {
            throw new NullPointerException("Edge collection null");
        }

        boolean modified = false;
        Iterator<Edge> edgeItr = this.edgeSet.iterator();
        while (edgeItr.hasNext()) {
            Edge edge = edgeItr.next();

            for (E edgeToRemove : edges) {
                if (edge.e.equals(edgeToRemove)) {
                    edgeItr.remove();
                    modified = true;
                    break;
                }
            }
        }
        checkRep();
        return modified;
    }

    @Override
    public Set<E> removeAllEdges(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!(containsVertex(v1) && containsVertex(v2))) {
            return null;
        }

        Set<E> removedEdgeSet = new HashSet<E>();
        Iterator<Edge> edgeItr = this.edgeSet.iterator();

        while (edgeItr.hasNext()) {
            Edge edge = edgeItr.next();

            if ((edge.v1.equals(v1) && edge.v2.equals(v2))
                    || (edge.v1.equals(v2) && edge.v2.equals(v1))) {
                removedEdgeSet.add(edge.e);
                edgeItr.remove();
            }
        }
        checkRep();
        return removedEdgeSet;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices)
            throws NullPointerException {

        if (vertices == null) {
            throw new NullPointerException("Vertex collection null");
        }

        boolean modified = false;

        for (V vertex : vertices) {
            if (removeVertex(vertex)) {
                modified = true;
            }
        }

        return modified;
    }

    /**
     * {@inheritDoc} <br>
     * <br>
     * Note: this method moves all duplicate edge values.
     */
    @Override
    public boolean removeEdge(E e) {
        boolean removed = false;
        Iterator<Edge> edgeItr = this.edgeSet.iterator();

        while (edgeItr.hasNext()) {
            Edge edge = edgeItr.next();
            if (edge.e.equals(e)) {
                edgeItr.remove();
                removed = true;
            }
        }

        checkRep();
        return removed;
    }

    @Override
    public E removeEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!(containsVertex(v1) && containsVertex(v2))) {
            return null;
        }

        E returnValue = null;
        Iterator<Edge> edgeItr = this.edgeSet.iterator();

        while (edgeItr.hasNext()) {
            Edge edge = edgeItr.next();

            if ((edge.v1.equals(v1) && edge.v2.equals(v2))
                    || (edge.v1.equals(v2) && edge.v2.equals(v1))) {
                returnValue = edge.e;
                edgeItr.remove();
                break;
            }
        }

        checkRep();
        return returnValue;
    }

    @Override
    public boolean removeVertex(V v) {
        // gather up and remove any Edges touching v
        // if v == null or v !exist in graph, internal edge set is empty
        edgeSet.remove(internalEdgeSet(v));
        boolean result = vertexSet.remove(v);
        checkRep();
        return result;
    }

    /**
     * Returns a set of Edges that represent the edges that touch vertex v.
     * 
     * @param v - the vertex to return a set of touching edges from
     * @return set of Edges that represent the edges that touch v
     */
    private Set<Edge> internalEdgeSet(V v) {
        Set<Edge> returnEdgeSet = new HashSet<Edge>();
        for (Edge edge : edgeSet) {
            if (edge.v1.equals(v) || edge.v2.equals(v)) {
                returnEdgeSet.add(edge);
            }
        }
        return returnEdgeSet;
    }

    /**
     * Checks that the rep invariant holds
     */
    private void checkRep() {

        if (RUN_CHECKREP) {
            // check vertexSet
            for (V v : this.vertexSet) {
                assert (v != null) : "Null vertex";
            }

            // check edges
            for (Edge e : this.edgeSet) {
                assert (e != null) : "Null edge";
                assert (vertexSet.contains(e.v1)) : "Vertex 1 not in graph";
                assert (vertexSet.contains(e.v2)) : "Vertex 2 not in graph";
            }
        }
    }

}
