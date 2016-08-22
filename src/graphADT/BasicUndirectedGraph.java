package graphADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

public class BasicUndirectedGraph<V, E> implements AbstractGraph<V,E> {

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

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "(" + v1 + "<->" + v2 + ")";
        }

    }

    /* internal representation of basic undirected graph */
    private Map<V,Set<Edge>> vertexMap;
    private Map<E,Set<Edge>> edgeMap;

    // Abstraction Function:
    // A basic undirected graph is an ADT that contains both vertexSet and the
    // edges between them. This graph stores a set of vertexSet and a set of
    // edges.

    // Representation Invariant:
    // foreach vertex v in vertexSet, v != null
    // foreach edge e in edges, e != null
    // vertex v1 in vertexSet
    // vertex v2 in vertexSet

    public BasicUndirectedGraph() {
        this.vertexMap = new HashMap<V,Set<Edge>>();
        this.edgeMap = new HashMap<E,Set<Edge>>();
        checkRep();
    }

    @Override
    public boolean addVertex(V v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException("Vertex value null");
        }

        if (vertexMap.containsKey(v)) {
            return false;
        } else {
            vertexMap.put(v, new HashSet<Edge>());
            checkRep();
            return true;
        }
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
        if (!(containsVertex(v1) && containsVertex(v2))) {
            return false;
        }

        // boolean result = edgeSet.add(new Edge(v1, v2, e));
        // checkRep();
        // return result;

        if (!edgeMap.containsKey(e)) {
            edgeMap.put(e, new HashSet<Edge>());
        }
        Edge newEdge = new Edge(v1, v2, e);
        boolean modified = edgeMap.get(e).add(newEdge);
        if (modified) {
            vertexMap.get(v1).add(newEdge);
            vertexMap.get(v2).add(newEdge);
        }
        checkRep();
        return modified;
    }

    @Override
    public boolean containsVertex(V v) {
        return vertexMap.containsKey(v);
    }

    @Override
    public boolean containsEdge(E e) {
        return edgeMap.containsKey(e);
    }

    @Override
    public boolean containsEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        }
        return !edgesBetween(v1, v2).isEmpty();
    }

    @Override
    public Set<V> vertexSet() {
        return new HashSet<V>(vertexMap.keySet());
    }

    @Override
    public Set<E> edgeSet() {
        return new HashSet<E>(edgeMap.keySet());
    }

    @Override
    public Set<E> edgeSet(V v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!containsVertex(v)) {
            return null;
        }

        Set<E> returnEdgeSet = new HashSet<E>();
        for (Edge e : vertexMap.get(v)) {
            returnEdgeSet.add(e.e);
        }
        return returnEdgeSet;
    }

    @Override
    public Set<E> edgeSet(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!(containsVertex(v1) && containsVertex(v2))) {
            return null;
        }
        return edgesBetween(v1, v2);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (containsVertex(v1) && containsVertex(v2)) {
            Set<E> edges = edgesBetween(v1, v2);
            if (!edges.isEmpty()) {
                return (E) edges.toArray()[0];
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
        for (E e : edges) {
            if (this.removeEdge(e)) {
                modified = true;
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

        Set<E> removedEdgeSet = edgesBetween(v1, v2);
        removeAllEdges(removedEdgeSet);
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

        // for (V vertex : vertices) {
        // if (removeVertex(vertex)) {
        // modified = true;
        // }
        // }

        for (V vertex : vertices) {
            for (Edge edge : vertexMap.remove(vertex)) {
                // E e = edge.e;
                // V v2 = edge.v2;
                vertexMap.get(edge.v2).remove(edge);
                edgeMap.get(edge.e).remove(edge);
            }
        }

        return modified;
    }

    /**
     * {@inheritDoc} <br>
     * 
     * Note: this method removes all duplicate edge values.
     */
    @Override
    public boolean removeEdge(E e) {
        boolean modified = false;
        Set<Edge> eSet = edgeMap.remove(e);
        if (eSet != null) {
            for (Edge edge : eSet) {
                vertexMap.get(edge.v1).remove(edge);
                vertexMap.get(edge.v2).remove(edge);
            } 
            modified = true;
        }

        checkRep();
        return modified;
    }

    @Override
    public E removeEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!(containsVertex(v1) && containsVertex(v2))) {
            return null;
        }

        E returnValue = null;
        // Iterator<Edge> edgeItr = this.edgeSet.iterator();
        //
        // while (edgeItr.hasNext()) {
        // Edge edge = edgeItr.next();
        //
        // if ((edge.v1.equals(v1) && edge.v2.equals(v2))
        // || (edge.v1.equals(v2) && edge.v2.equals(v1))) {
        // returnValue = edge.e;
        // edgeItr.remove();
        // break;
        // }
        // }

        checkRep();
        return returnValue;
    }

    @Override
    public boolean removeVertex(V v) {
        // gather up and remove any Edges touching v
        // if v == null or v !exist in graph, internal edge set is empty
        // edgeSet.remove(internalEdgeSet(v));
        // boolean result = vertexSet.remove(v);

        if (!vertexMap.containsKey(v)) {
            return false;
        } else {
            // boolean result = false;
            for (Edge edge : vertexMap.remove(v)) {
                vertexMap.get(edge.v2).remove(edge);
                edgeMap.get(edge.e).remove(edge);
            }

            checkRep();
            return true;
        }

        // checkRep();
        // return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return vertexMap.keySet().toString() + "\n" + edgeMap.toString();
    }

    /**
     * Returns a set of edge values between two vertices in the graph.
     * 
     * @param v1 the first vertex value
     * @param v2 the second vertex value
     * @return set of edge values between vertices v1 and v2
     */
    private Set<E> edgesBetween(V v1, V v2) {
        Set<E> edgesBetween = new HashSet<E>();

        if (containsVertex(v1) && containsVertex(v2)) {
            Set<Edge> intersection = new HashSet<Edge>(vertexMap.get(v1));
            intersection.retainAll(vertexMap.get(v2));
            for (Edge edge : intersection) {
                edgesBetween.add(edge.e);
            }
        }

        return edgesBetween;
    }

    /**
     * Checks that the rep invariant holds
     */
    private void checkRep() {

        if (RUN_CHECKREP) {
            // check vertices
            for (V v : this.vertexMap.keySet()) {
                assert (v != null) : "Null vertex";
            }

            // check edges
            for (Map.Entry<E,Set<Edge>> entry : this.edgeMap.entrySet()) {
                for (Edge e : entry.getValue()) {
                    assert (e != null) : "Null edge";
                    assert (vertexMap
                            .containsKey(e.v1)) : "Vertex 1 not in graph";
                    assert (vertexMap
                            .containsKey(e.v2)) : "Vertex 2 not in graph";
                }
            }
        }
    }

}
