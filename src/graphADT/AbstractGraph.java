package graphADT;

import java.util.Collection;
import java.util.Set;

/**
 * AbstractGraph is a graph storing vertex and edge abstract data types.
 * 
 * This is the common interface for any AbstractGraph class.
 * 
 * @author Drew Reese
 *
 * @param <V> Data type to store as vertices
 * @param <E> Data type to store as edges
 */
public interface AbstractGraph<V, E> {

    /**
     * Adds specified vertex value to this graph if not already present.
     * 
     * @param v - vertex value to be added to this graph.
     * @return <code>true</code> if graph did not already contain this vertex
     *         value, <code>false</code> otherwise.
     * @throws NullPointerException if vertex value <code>v</code> is
     *             <code>null</code>
     */
    boolean addVertex(V v) throws NullPointerException;

    /**
     * Adds new null-labeled edge connecting specified vertices <code>v1</code>
     * and <code>v2</code>.
     * 
     * @param v1 - the first vertex value for edge
     * @param v2 - the second vertex value for edge
     * @return <code>true</code> iff edge was added to graph, <code>false</code>
     *         otherwise
     * @throws NullPointerException if vertex value <code>v1</code> or
     *             <code>v2</code> is <code>null</code>
     */
    boolean addEdge(V v1, V v2) throws NullPointerException;

    /**
     * Adds new labeled edge connecting specified vertices <code>v1</code> and
     * <code>v2</code>.
     * 
     * @param v1 - the first vertex value for edge
     * @param v2 - the second vertex value for edge
     * @param e - object label for edge
     * @return <code>true</code> iff edge was added to graph, <code>false</code>
     *         otherwise
     * @throws NullPointerException if vertex value <code>v1</code> or
     *             <code>v2</code> is <code>null</code>
     */
    boolean addEdge(V v1, V v2, E e) throws NullPointerException;

    /**
     * Returns <code>true</code> if this graph contains the specified vertex
     * value. Formally, returns <code>true</code> iff this graph contains vertex
     * <code>u</code> s.t. <code>u.equals(v)</code>. If vertex v is
     * <code>null</code>, return <code>false</code>.
     * 
     * @param v - vertex value to test if contained in this graph
     * @return <code>true</code> iff this graph contains vertex value
     *         <code>v</code>, <code>false</code> otherwise
     */
    boolean containsVertex(V v);

    /**
     * Returns <code>true</code> if this graph contains the specified edge
     * value. Formally, returns <code>true</code> iff this graph contains edge
     * <code>e2</code> s.t. <code>e2.equals(e)</code>.
     * 
     * @param e - edge to test if contained in this graph
     * @return <code>true</code> iff this graph contains edge <code>e</code>,
     *         <code>false</code> otherwise
     */
    boolean containsEdge(E e);

    /**
     * Returns <code>true</code> if this graph contains an edge between the
     * specified vertex values. Formally, returns <code>true</code> iff this
     * graph contains vertices <code>u1</code> and <code>u2</code> and edge
     * <code>e = {u1,u2}</code> s.t. <code>u1.equals([v1|v2])</code> and
     * <code>u2.equals([v2|v1])</code>, <code>false</code> otherwise.
     * 
     * @param v1 - the first vertex value to test if edge is contained
     * @param v2 - the second vertex value to test if edge is contained
     * @return <code>true</code> iff this graph contains an edge between vertex
     *         values <code>v1</code> and <code>v2</code>, <code>false</code>
     *         otherwise
     * @throws NullPointerException if vertex value <code>v1</code> or
     *             <code>v2</code> is <code>null</code>
     */
    boolean containsEdge(V v1, V v2) throws NullPointerException;

    /**
     * Returns a set view of the vertices contained in this graph.
     * 
     * @return a set of all the vertices contained in this graph
     */
    Set<V> vertexSet();

    /**
     * Returns a set view of the edges contained in this graph.
     * 
     * @return a set view of all the edges contained in this graph
     */
    Set<E> edgeSet();

    /**
     * Returns a set of the edges touching the specified vertex <code>v</code>.
     * If no edges touch <code>v</code>, then an empty set is returned. If
     * <code>v</code> is not a part of this graph, then <code>null</code> is
     * returned.
     * 
     * @param v - the vertex for which a set of connected edges is returned
     * @return a set of all edges touching the specified vertex <code>v</code>,
     *         or null if <code>v</code> not contained in this graph
     * @throws NullPointerException if vertex <code>v</code> is
     *             <code>null</code>
     */
    Set<E> edgeSet(V v) throws NullPointerException;

    /**
     * Returns a set of the edges connecting the specified vertices
     * <code>v1</code> and <code>v2</code>. If vertices <code>v1</code> and
     * <code>v2</code> are not connected, then an empty set is returned. If
     * either <code>v1</code> or <code>v2</code> are not contained in this
     * graph, then <code>null</code> is returned.
     * 
     * @param v1 - the first vertex value to return set of connected edges from
     * @param v2 - the second vertex value to return set of connected edges from
     * @return a set of all edges connecting vertex values <code>v1</code> and
     *         <code>v2</code>, or <code>null</code> if <code>v1</code> or
     *         <code>v2</code> not part of this graph
     * @throws NullPointerException if vertex values <code>v1</code> or
     *             <code>v2</code> are <code>null</code>
     */
    Set<E> edgeSet(V v1, V v2) throws NullPointerException;

    /**
     * Returns an edge connecting the specified vertex values. If vertices
     * <code>v1</code> and <code>v2</code> are not connected, then
     * <code>null</code> is returned. If multiple edges connect vertices
     * <code>v1</code> and <code>v2</code>, then it is undefined which edge is
     * returned.
     * 
     * @param v1 - the first vertex value to return connected edge from
     * @param v2 - the second vertex value to return connected edge from
     * @return edge connecting vertex values <code>v1</code> and
     *         <code>v2</code>, <code>null</code> otherwise
     * @throws NullPointerException if vertex values <code>v1</code> or
     *             <code>v2</code> are <code>null</code>
     */
    E getEdge(V v1, V v2) throws NullPointerException;

    /**
     * Removes all edges in this graph that are also contained in the specified
     * edge collection.
     * 
     * @param edges - the edges to be removed from this graph
     * @return <code>true</code> if this graph was modified, <code>false</code>
     *         otherwise
     * @throws NullPointerException if specified edge collection is
     *             <code>null</code>
     */
    boolean removeAllEdges(Collection<? extends E> edges)
            throws NullPointerException;

    /**
     * Removes all the edges in the graph between vertex <code>v1</code> and
     * <code>v2</code>, and returns the set of the removed edges. Returns
     * <code>null</code> if any of the specified vertices do not exist in the
     * graph, and returns an empty set if both vertices exist but no edge is
     * found.
     * 
     * @param v1 - the first vertex to remove edge from
     * @param v2 - the second vertex to remove edge from
     * @return set of removed edges or <code>null</code> if either vertex is not
     *         part of this graph
     * @throws NullPointerException if vertex values <code>v1</code> or
     *             <code>v2</code> are <code>null</code>
     */
    Set<E> removeAllEdges(V v1, V v2) throws NullPointerException;

    /**
     * Removes all the vertices in this graph that are also contained in the
     * specified vertex collection. This method also removes edges touching
     * vertices before removing.
     * 
     * @param vertices - the vertices to be removed from this graph
     * @return <code>true</code> if this graph was modified, <code>false</code>
     *         otherwise
     * @throws NullPointerException if specified vertex collection is
     *             <code>null</code>
     */
    boolean removeAllVertices(Collection<? extends V> vertices)
            throws NullPointerException;

    /**
     * Removes the specified edge e from this graph if it is present. Formally,
     * this method removes edge <code>e2</code> s.t. <code>e2.equals(e)</code>
     * is <code>true</code>. Returns <code>true</code> if graph contained the
     * specified edge, <code>false</code> otherwise.
     * 
     * @param e - the edge to be removed from this graph
     * @return <code>true</code> iff the graph contained the specified edge,
     *         <code>false</code> otherwise
     */
    boolean removeEdge(E e);

    /**
     * Removes and returns the edge connecting the specified vertex values
     * <code>v1</code> and <code>v2</code>. If <code>v1</code> and
     * <code>v2</code> are not connected, then <code>null</code> is returned. If
     * multiple edges connect vertices <code>v1</code> and <code>v2</code>, then
     * it is undefined which edge is removed and returned.
     * 
     * @param v1 - the first vertex value to remove edge from
     * @param v2 - the second vertex value to remove edge from
     * @return the removed edge, or <code>null</code> if graph was not modified
     * @throws NullPointerException if vertex values <code>v1</code> or
     *             <code>v2</code> are <code>null</code>
     */
    E removeEdge(V v1, V v2) throws NullPointerException;

    /**
     * Removes the specified vertex <code>v</code> from this graph if it is
     * present. Formally, this method removes edge <code>u</code> s.t.
     * <code>u.equals(v)</code> is true. Returns <code>true</code> if graph
     * contained the specified vertex, <code>false</code> otherwise. This call
     * removes all edges that touch vertex <code>u</code>, then removes
     * <code>u</code>.
     * 
     * @param v - the vertex to be removed from this graph
     * @return <code>true</code> if the graph contained the specified vertex,
     *         <code>false</code> otherwise
     */
    boolean removeVertex(V v);

}
