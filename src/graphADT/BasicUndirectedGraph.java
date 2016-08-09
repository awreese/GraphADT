package graphADT;

import java.util.Collection;
import java.util.Set;

public class UndirectedGraph<V, E> implements Graph<V,E> {

    @Override
    public boolean addVertex(V v) throws NullPointerException {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addEdge(V v1, V v2) throws NullPointerException {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addEdge(V v1, V v2, E e) throws NullPointerException {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsVertex(V v) {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsEdge(E e) {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsEdge(V v1, V v2) throws NullPointerException {

        // TODO Auto-generated method stub
        return false;
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


}
