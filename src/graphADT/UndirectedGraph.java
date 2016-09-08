package graphADT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UndirectedGraph<V,E> represents a basic mutable undirected multi-graph.
 * While it can store more advanced vertex and edge object types, this class
 * only supports the basic graph operations defined in abstract graph interface.
 * This graph stores non-null vertex values and null-able edge values, and is
 * capable of storing multiple non-duplicate edges between vertices but allows
 * duplicate edges. <br/>
 * 
 * <h5>Abstract Invariant:</h5>
 * foreach vertex in graph
 * <ul>
 * <li>vertex is not null</li>
 * <li>vertex value is not null</li>
 * <li>vertex value is unique</li>
 * </ul>
 * foreach edge connecting vertices in graph
 * <ul>
 * <li>edge is not null</li>
 * <li>edge value can be null</li>
 * <li>edge vertices in graph</li>
 * </ul>
 * 
 * @author Drew Reese
 *
 * @param <V> - data type to store as vertices
 * @param <E> - data type to store as edges
 * 
 */

public class UndirectedGraph<V, E> implements AbstractGraph<V,E> {

    // DEBUGGING EXPENSIVE CHECKREP FLAG
    private static final boolean RUN_CHECKREP = false;

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
            if (!(obj instanceof UndirectedGraph.Edge)) {
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

        private UndirectedGraph<?,?> getOuterType() {
            return UndirectedGraph.this;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "(" + v1 + "<->" + v2 + ")";
        }

    }

    /*
     * Internal representation of basic undirected graph
     * 
     * vertexMap - maps vertex value to a set of it's connected edges. edgeMap -
     * maps edge value to a set of edges containing that value.
     */
    private Map<V,Set<Edge>> vertexMap;
    private Map<E,Set<Edge>> edgeMap;

    /*
     * Abstraction Function:
     * 
     * A basic undirected graph is an ADT that contains both vertices and the
     * edges between them. This graph stores a vertex set and an edge set. The
     * vertex set is represented by a Key-Value store where the vertex value is
     * the key and a set of edges connecting that vertex is the value. Likewise,
     * the edge set is represented by a Key-Value store where the edge value is
     * the key and a set of edges representing that edge value is the key.
     */

    // Representation Invariant:
    //
    // vertex set = {v_1, v_2, v_3, ..., v_n}
    // edge set = {e_1, e_2, e_3, ..., e_n}
    // edge value = (e_i, v_j, v_k) where
    // e_i element of edge set
    // v_j and v_k elements of vertex set
    //
    // foreach vertex v in vertexMap
    // v != null
    // v element of vertex set
    //
    // foreach edge e in edgeMap
    // e != null
    // e.v1 in vertexMap
    // e.v2 in vertexMap

    public UndirectedGraph() {
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

        if (!edgeMap.containsKey(e)) {
            edgeMap.put(e, new HashSet<Edge>());
        }
        
        Edge newEdge = new Edge(v1, v2, e);
        boolean modified = edgeMap.get(e).add(newEdge);
        
        if (modified) {
            vertexMap.get(v1).add(newEdge);
            vertexMap.get(v2).add(newEdge);
        } else {
            // adding new edge failed, undo edgeMap changes if just mapped
            if (edgeMap.get(e).isEmpty()) {
                edgeMap.remove(e);
            }
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
        return !intersection(v1, v2).isEmpty();
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
        return extractEdges(intersection(v1, v2));
    }

    @Override
    public E getEdge(V v1, V v2) throws NullPointerException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Vertex value null");
        } else if (!(containsVertex(v1) && containsVertex(v2))) {
            return null;
        }
        Edge edge = getEdge(intersection(v1, v2));
        return (edge != null) ? edge.e : null;
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

        Set<Edge> edges = intersection(v1, v2);

        vertexMap.get(v1).removeAll(edges);
        vertexMap.get(v2).removeAll(edges);

        for (Edge edge : edges) {
            // remove edge from set
            edgeMap.get(edge.e).remove(edge);

            // remove mapping if now empty
            if (edgeMap.get(edge.e).isEmpty()) {
                edgeMap.remove(edge.e);
            }
        }

        checkRep();
        return extractEdges(edges);
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices)
            throws NullPointerException {

        if (vertices == null) {
            throw new NullPointerException("Vertex collection null");
        }

        boolean modified = false;
        for (V vertex : vertices) {
            if (this.removeVertex(vertex)) {
                modified = true;
            }
        }
        checkRep();
        return modified;
    }

    /**
     * {@inheritDoc} <br>
     * 
     * Note: this method removes all duplicate edge values.
     */
    @Override
    public boolean removeEdge(E e) {

        if (!edgeMap.containsKey(e)) {
            return false;
        }

        boolean modified = false;
        Set<Edge> eSet = edgeMap.remove(e);
        if (eSet != null) {
            modified = true;
            for (Edge edge : eSet) {
                vertexMap.get(edge.v1).remove(edge);
                vertexMap.get(edge.v2).remove(edge);
            }
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

        // get a single edge from the intersection of the vertices
        Edge edge = getEdge(intersection(v1, v2));
        if (edge == null) {
            return null;
        }
        
        vertexMap.get(edge.v1).remove(edge);
        vertexMap.get(edge.v2).remove(edge);
        edgeMap.get(edge.e).remove(edge);
        // remove mapping if now empty
        if (edgeMap.get(edge.e).isEmpty()) {
            edgeMap.remove(edge.e);
        }

        checkRep();
        return edge.e;
    }

    @Override
    public boolean removeVertex(V v) {
        if (!vertexMap.containsKey(v)) {
            return false;
        }

        boolean modified = false;
        Set<Edge> edgesToRemove = vertexMap.remove(v);

        if (edgesToRemove != null) {
            modified = true;
            for (Edge edge : edgesToRemove) {
                // remove edge from matching vertex
                if (edge.v1.equals(edge.v2)) {
                    // special self-edge case
                    // do nothing since already removed above
                } else if (v.equals(edge.v1)) {
                    vertexMap.get(edge.v2).remove(edge);
                } else {
                    vertexMap.get(edge.v1).remove(edge);
                }
                // remove edge from edge mapping
                edgeMap.get(edge.e).remove(edge);
                // remove mapping if now empty
                if (edgeMap.get(edge.e).isEmpty()) {
                    edgeMap.remove(edge.e);
                }
            }
        }
        checkRep();
        return modified;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return vertexMap.keySet() + "\n" + edgeMap;
    }

    /**
     * Returns a set of edges between two vertices in the graph.
     * 
     * @param v1 the first vertex value
     * @param v2 the second vertex value
     * @return set of edges between vertices v1 and v2
     */
    private Set<Edge> intersection(V v1, V v2) {
        Set<Edge> intersection = new HashSet<Edge>();

        if (containsVertex(v1) && containsVertex(v2)) {

            if (v1.equals(v2)) {
                for (Edge edge : vertexMap.get(v1)) {
                    if (edge.v1.equals(edge.v2)) {
                        intersection.add(edge);
                    }
                }
            } else {
                intersection.addAll(vertexMap.get(v1));
                intersection.retainAll(vertexMap.get(v2));
            }
        }

        return intersection;
    }

    /**
     * Extracts and returns a set of edge values
     * 
     * @param edges set of edges to extract values from
     * @return
     */
    private Set<E> extractEdges(Set<Edge> edges) {
        Set<E> edgesBetween = new HashSet<E>();
        for (Edge edge : edges) {
            edgesBetween.add(edge.e);
        }
        return edgesBetween;
    }

    /**
     * Returns a single edge from a set of edges
     */
    private Edge getEdge(Set<Edge> edgeSet) {
        List<Edge> edgeList = new ArrayList<Edge>(edgeSet);
        return edgeList.isEmpty() ? null : edgeList.get(0);
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
