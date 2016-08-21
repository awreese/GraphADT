package graphADT;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BasicUndirectedGraphTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // test graphs
    BasicUndirectedGraph<String,String>   testGraphStringString;
    BasicUndirectedGraph<String,String>   testGraphStringStringNull;
    BasicUndirectedGraph<String,Double>   testGraphStringDouble;
    BasicUndirectedGraph<Integer,Integer> testGraphIntegerInteger;

    // test data sets
    String[]  strings  = { "one", "two", "three" };
    Double[]  doubles  = { 1d, 2d, 3d };
    Integer[] integers = { 1, 2, 3 };

    /**
     * Construct new graphs before each test
     */
    @Before
    public void setUp() {
        testGraphStringStringNull = new BasicUndirectedGraph<String,String>();
        testGraphStringString = new BasicUndirectedGraph<String,String>();
        testGraphStringDouble = new BasicUndirectedGraph<String,Double>();
        testGraphIntegerInteger = new BasicUndirectedGraph<Integer,Integer>();
    }

    /**
     * Unit test for BasicUndirectedGraph constructor
     */
    @Test
    public void constructor() {
        // setup run before test which constructed new graphs
        assertTrue(testGraphStringString.vertexSet().isEmpty());
        assertTrue(testGraphStringString.edgeSet().isEmpty());

        assertTrue(testGraphStringDouble.vertexSet().isEmpty());
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());

        assertTrue(testGraphIntegerInteger.vertexSet().isEmpty());
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());
    }

    /**
     * Unit test for adding vertices to graphs.
     */
    @Test
    public void addVertex() {
        /*
         * Add vertices
         */
        // String into String type vertex
        for (String s : strings) {
            assertTrue(testGraphStringString.addVertex(s));
        }

        // try graph of more complex objects
        BasicUndirectedGraph<ComplexObject,String> complexObjectGraph;
        complexObjectGraph = new BasicUndirectedGraph<ComplexObject,String>();
        ComplexObject co = new ComplexObject(strings, doubles, integers);
        assertTrue(complexObjectGraph.addVertex(co));

        /*
         * Add duplicate vertices
         */
        assertFalse(testGraphStringString.addVertex(strings[0]));
        assertFalse(complexObjectGraph.addVertex(co));

        /*
         * Add null vertex
         */
        // thrown.expect(NullPointerException.class);
        // thrown.expectMessage("Vertex value null");
        // testGraphStringString.addVertex(null);

        try {
            // I like this style better, it allows for testing exceptions
            // individually multiple times within tests
            testGraphStringString.addVertex(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }

        try {
            complexObjectGraph.addVertex(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }

    }

    /**
     * Unit test for adding edges between vertices in graphs.
     */
    @Test
    public void addEdge() {
        /*
         * Add edges adds edge data and asserts edge addition was successful
         */
        loadVertices(); // graph constructed, load vertices
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.addEdge(strings[i % 3],
                    strings[(i + 1) % 3]));

            assertTrue(testGraphStringStringNull.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            System.out.println(strings[i % 3] + " - " + strings[(i + 1) % 3]
                    + ": " + testGraphStringStringNull.getEdge(strings[i % 3],
                            strings[(i + 1) % 3]));

            assertTrue(testGraphStringString.addEdge(strings[i % 3],
                    strings[(i + 1) % 3],
                    strings[i % 3] + "-" + strings[(i + 1) % 3]));

            System.out.println(strings[i % 3] + " - " + strings[(i + 1) % 3]
                    + ": " + testGraphStringString.getEdge(strings[i % 3],
                            strings[(i + 1) % 3]));

            assertTrue(testGraphStringDouble.addEdge(strings[i % 3],
                    strings[(i + 1) % 3], doubles[i]));

            System.out.println(doubles[i % 3] + " - " + doubles[(i + 1) % 3] + ": " + testGraphStringDouble
                    .getEdge(strings[i % 3], strings[(i + 1) % 3]));

            assertTrue(testGraphIntegerInteger.addEdge(integers[i % 3],
                    integers[(i + 1) % 3], integers[i]));

            System.out.println(integers[i % 3] + " - " + integers[(i + 1) % 3] + ": " + testGraphIntegerInteger
                    .getEdge(integers[i % 3], integers[(i + 1) % 3]));
        }

        /*
         * Add self edges adds edge data to create self edges for each vertex
         * and asserts addition was successful
         */
        clearGraph(); // clear and reload vertices
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(
                    testGraphStringStringNull.addEdge(strings[i], strings[i]));
            assertTrue(testGraphStringString.addEdge(strings[i], strings[i],
                    strings[i] + "-" + strings[i]));
            assertTrue(testGraphStringDouble.addEdge(strings[i], strings[i],
                    doubles[i]));
            assertTrue(testGraphIntegerInteger.addEdge(integers[i], integers[i],
                    integers[i]));
        }

        /*
         * Add duplicate edge adds a duplicate edge already present in graph and
         * asserts addition was unsuccessful
         * 
         * This will test internal edge implementation (hashcode, equals)
         */
        reloadGraphData(); // clear and reload graph data
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.addEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertFalse(testGraphStringStringNull.addEdge(strings[(i + 1) % 3],
                    strings[i % 3]));

            assertFalse(testGraphStringString.addEdge(strings[i % 3],
                    strings[(i + 1) % 3],
                    strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertFalse(testGraphStringString.addEdge(strings[(i + 1) % 3],
                    strings[i % 3],
                    strings[i % 3] + "-" + strings[(i + 1) % 3]));

            assertFalse(testGraphStringDouble.addEdge(strings[i % 3],
                    strings[(i + 1) % 3], doubles[i]));
            assertFalse(testGraphStringDouble.addEdge(strings[(i + 1) % 3],
                    strings[i % 3], doubles[i]));

            assertFalse(testGraphIntegerInteger.addEdge(integers[i % 3],
                    integers[(i + 1) % 3], integers[i]));
            assertFalse(testGraphIntegerInteger.addEdge(integers[(i + 1) % 3],
                    integers[i % 3], integers[i]));
        }

        /*
         * Add edge with null vertex adds an edge where vertex 1 or 2 or both
         * are null these should throw NullPointerExceptions
         */
        clearGraph(); // clear and reload vertices
        loadVertices();
        try {
            testGraphStringString.addEdge(strings[0], null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringString.addEdge(null, strings[0]);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringString.addEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }

        /*
         * Add edge with non-existent graph vertices adds an edge where vertex 1
         * or 2 or both don't exist in graph
         */
        assertFalse(testGraphStringString.addEdge(strings[0], "four"));
        assertFalse(testGraphStringString.addEdge("four", strings[0]));
        assertFalse(testGraphStringString.addEdge("four", "five"));
    }

    /**
     * Unit test to check if vertex contained in a graph
     */
    @Test
    public void containsVertex() {
        /*
         * Test positive containment
         */
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.containsVertex(strings[i]));
            assertTrue(testGraphStringString.containsVertex(strings[i]));
            assertTrue(testGraphStringDouble.containsVertex(strings[i]));
            assertTrue(testGraphIntegerInteger.containsVertex(integers[i]));
        }

        /*
         * Test negative containment
         */
        assertFalse(testGraphStringStringNull.containsVertex("four"));
        assertFalse(testGraphStringString.containsVertex("four"));
        assertFalse(testGraphStringDouble.containsVertex("four"));
        assertFalse(testGraphIntegerInteger.containsVertex(4));

        /*
         * Test null containment
         */
        assertFalse(testGraphStringStringNull.containsVertex(null));
        assertFalse(testGraphStringString.containsVertex(null));
        assertFalse(testGraphStringDouble.containsVertex(null));
        assertFalse(testGraphIntegerInteger.containsVertex(null));
    }

    /**
     * Unit test to check if edge contained in a graph
     */
    @Test
    public void containsEdge() {
        loadGraphData();

        /*
         * Test positive edge value containment
         */
        assertTrue(testGraphStringStringNull.containsEdge(null));
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringString
                    .containsEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringDouble.containsEdge(doubles[i]));
            assertTrue(testGraphIntegerInteger.containsEdge(integers[i]));
        }

        /*
         * Test negative edge value containment
         */
        assertFalse(testGraphStringStringNull.containsEdge(strings[0]));
        assertFalse(testGraphStringString.containsEdge("four"));
        assertFalse(testGraphStringDouble.containsEdge(4d));
        assertFalse(testGraphIntegerInteger.containsEdge(4));

        /*
         * Test null edge value containment
         */
        assertFalse(testGraphStringString.containsEdge(null));
        assertFalse(testGraphStringDouble.containsEdge(null));
        assertFalse(testGraphIntegerInteger.containsEdge(null));

        /*
         * Test positive edge (v1//v2, v2//v1) containment
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertTrue(testGraphStringStringNull
                    .containsEdge(strings[(i + 1) % 3], strings[i % 3]));

            assertTrue(testGraphStringString.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertTrue(testGraphStringString.containsEdge(strings[(i + 1) % 3],
                    strings[i % 3]));

            assertTrue(testGraphStringDouble.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertTrue(testGraphStringDouble.containsEdge(strings[(i + 1) % 3],
                    strings[i % 3]));

            assertTrue(testGraphIntegerInteger.containsEdge(integers[i % 3],
                    integers[(i + 1) % 3]));
            assertTrue(testGraphIntegerInteger
                    .containsEdge(integers[(i + 1) % 3], integers[i % 3]));
        }

        /*
         * Test negative edge (v1, v2) containment v1 and v2 in the graph
         * 
         * Since test graphs all only contain 3 vertices and are thus fully
         * connected, we need to add a few more to each to ensure unconnected
         * vertices exist in graph in order to test properly.
         */
        testGraphStringStringNull.addVertex("four");
        testGraphStringStringNull.addVertex("five");
        assertFalse(testGraphStringStringNull.containsEdge(strings[2], "four"));
        assertFalse(testGraphStringStringNull.containsEdge("four", strings[2]));
        assertFalse(testGraphStringStringNull.containsEdge("four", "five"));
        assertFalse(testGraphStringStringNull.containsEdge("five", "four"));

        testGraphStringString.addVertex("four");
        testGraphStringString.addVertex("five");
        assertFalse(testGraphStringString.containsEdge(strings[2], "four"));
        assertFalse(testGraphStringString.containsEdge("four", strings[2]));
        assertFalse(testGraphStringString.containsEdge("four", "five"));
        assertFalse(testGraphStringString.containsEdge("five", "four"));

        testGraphStringDouble.addVertex("four");
        testGraphStringDouble.addVertex("five");
        assertFalse(testGraphStringDouble.containsEdge(strings[2], "four"));
        assertFalse(testGraphStringDouble.containsEdge("four", strings[2]));
        assertFalse(testGraphStringDouble.containsEdge("four", "five"));
        assertFalse(testGraphStringDouble.containsEdge("five", "four"));

        testGraphIntegerInteger.addVertex(4);
        testGraphIntegerInteger.addVertex(5);
        assertFalse(testGraphIntegerInteger.containsEdge(integers[2], 4));
        assertFalse(testGraphIntegerInteger.containsEdge(4, integers[2]));
        assertFalse(testGraphIntegerInteger.containsEdge(4, 5));
        assertFalse(testGraphIntegerInteger.containsEdge(5, 4));

        /*
         * Test negative edge (v1, v2) containment where either v1, v2, or both
         * aren't in the graph
         */
        reloadGraphData();
        assertFalse(testGraphStringStringNull.containsEdge(strings[2], "four"));
        assertFalse(testGraphStringStringNull.containsEdge("four", strings[2]));
        assertFalse(testGraphStringStringNull.containsEdge("four", "five"));
        assertFalse(testGraphStringStringNull.containsEdge("five", "four"));

        assertFalse(testGraphStringString.containsEdge(strings[2], "four"));
        assertFalse(testGraphStringString.containsEdge("four", strings[2]));
        assertFalse(testGraphStringString.containsEdge("four", "five"));
        assertFalse(testGraphStringString.containsEdge("five", "four"));

        assertFalse(testGraphStringDouble.containsEdge(strings[2], "four"));
        assertFalse(testGraphStringDouble.containsEdge("four", strings[2]));
        assertFalse(testGraphStringDouble.containsEdge("four", "five"));
        assertFalse(testGraphStringDouble.containsEdge("five", "four"));

        assertFalse(testGraphIntegerInteger.containsEdge(integers[2], 4));
        assertFalse(testGraphIntegerInteger.containsEdge(4, integers[2]));
        assertFalse(testGraphIntegerInteger.containsEdge(4, 5));
        assertFalse(testGraphIntegerInteger.containsEdge(5, 4));

        /*
         * Test NullPointerException thrown when vertex v1, v2, or both are null
         */
        try {
            testGraphStringString.containsEdge(strings[2], null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringString.containsEdge(null, strings[2]);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringString.containsEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
    }

    /**
     * Unit test for vertex set from graph
     */
    @Test
    public void vertexSet() {
        /*
         * Test new empty graphs
         */
        assertTrue(testGraphStringStringNull.vertexSet().isEmpty());
        assertTrue(testGraphStringString.vertexSet().isEmpty());
        assertTrue(testGraphStringDouble.vertexSet().isEmpty());
        assertTrue(testGraphIntegerInteger.vertexSet().isEmpty());

        /*
         * Test positive vertex containment from loaded graphs
         */
        loadVertices();
        assertEquals(testGraphStringStringNull.vertexSet().size(),
                strings.length);
        assertTrue(testGraphStringStringNull.vertexSet()
                .containsAll(Arrays.asList(strings)));

        assertEquals(testGraphStringString.vertexSet().size(), strings.length);
        assertTrue(testGraphStringString.vertexSet()
                .containsAll(Arrays.asList(strings)));

        assertEquals(testGraphStringDouble.vertexSet().size(), strings.length);
        assertTrue(testGraphStringDouble.vertexSet()
                .containsAll(Arrays.asList(strings)));

        assertEquals(testGraphIntegerInteger.vertexSet().size(),
                integers.length);
        assertTrue(testGraphIntegerInteger.vertexSet()
                .containsAll(Arrays.asList(integers)));

        /*
         * Test negative vertex containment from loaded graphs
         */
        assertFalse(testGraphStringStringNull.vertexSet().contains("four"));
        assertFalse(testGraphStringString.vertexSet().contains("four"));
        assertFalse(testGraphStringDouble.vertexSet().contains(4d));
        assertFalse(testGraphIntegerInteger.vertexSet().contains(4));

        /*
         * Test mutability of vertex set of graph
         * 
         * (1) Test by removing a vertex from the vertex set and check that it
         * effected the underlying graph.
         * 
         * (2) Test by removing/adding vertex from/to the graph and check that
         * it effected the vertex set.
         * 
         * (3) Test removing collection from vertex set and check the change
         * effects the underlying graph.
         */
        Set<String> vSet;

        // (1)
        vSet = testGraphStringString.vertexSet();
        vSet.remove(strings[2]);
        assertFalse(testGraphStringString.containsVertex(strings[2]));

        // (2)
        clearGraph();
        loadVertices();
        vSet = testGraphStringString.vertexSet();
        testGraphStringString.removeVertex(strings[2]);
        assertFalse(vSet.contains(strings[2]));
        testGraphStringString.addVertex(strings[2]);
        assertTrue(vSet.contains(strings[2]));

        // (3)
        clearGraph();
        loadVertices();
        vSet = testGraphStringString.vertexSet();
        vSet.removeAll(Arrays.asList(strings));
        for (String s : strings) {
            assertFalse(testGraphStringString.containsVertex(s));
        }
    }

    /**
     * Unit test for edge set from graph
     */
    @Test
    public void edgeSet() {
        ///// TESTING edgeSet() /////

        /*
         * Test new empty graphs
         */
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());

        /*
         * Test positive edge containment from loaded graphs
         */
        loadGraphData();
        assertEquals(testGraphStringStringNull.edgeSet().size(), 1);
        assertTrue(testGraphStringStringNull.edgeSet().contains(null));

        assertEquals(testGraphStringString.edgeSet().size(), strings.length);
        assertEquals(testGraphStringDouble.edgeSet().size(), strings.length);
        assertEquals(testGraphIntegerInteger.edgeSet().size(), integers.length);
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringString.edgeSet()
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringDouble.edgeSet().contains(doubles[i]));
            assertTrue(testGraphIntegerInteger.edgeSet().contains(integers[i]));
        }

        /*
         * Test negative edge containment from loaded graphs
         */
        assertFalse(testGraphStringStringNull.edgeSet().contains("four"));
        assertFalse(testGraphStringString.edgeSet().contains("four"));
        assertFalse(testGraphStringDouble.edgeSet().contains(4d));
        assertFalse(testGraphIntegerInteger.edgeSet().contains(4));

        /*
         * Test mutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that it
         * effected the underlying graph.
         * 
         * (2) Test by removing/adding edge from/to the graph and check that it
         * effected the edge set.
         * 
         * (3) Test removing collection from edge set and check the change
         * effects the underlying graph.
         */
        Set<Double> eSet;

        // (1)
        eSet = testGraphStringDouble.edgeSet();
        eSet.remove(doubles[2]);
        assertFalse(testGraphStringDouble.containsEdge(doubles[2]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet();
        testGraphStringDouble.removeEdge(doubles[0]);
        assertFalse(eSet.contains(doubles[0]));
        testGraphStringDouble.addEdge(strings[0], strings[1], doubles[0]);
        assertTrue(eSet.contains(doubles[0]));

        // (3)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet();
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertFalse(testGraphStringDouble.containsEdge(d));
        }

        ///// TESTING edgeSet(v) /////

        /*
         * Test edge containment from a vertex of new graph
         */
        clearGraph();
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.edgeSet(strings[i]).isEmpty());
            assertTrue(testGraphStringString.edgeSet(strings[i]).isEmpty());
            assertTrue(testGraphStringDouble.edgeSet(strings[i]).isEmpty());
            assertTrue(testGraphIntegerInteger.edgeSet(integers[i]).isEmpty());
        }

        /*
         * Test positive edge containment from a vertex of a loaded graph
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertEquals(testGraphStringStringNull.edgeSet(strings[i]).size(),
                    1);
            assertTrue(testGraphStringStringNull.edgeSet(strings[i])
                    .contains(null));

            assertEquals(testGraphStringString.edgeSet(strings[i]).size(), 2);
            assertTrue(testGraphStringString.edgeSet(strings[i])
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringString.edgeSet(strings[i])
                    .contains(strings[(i + 2) % 3] + "-" + strings[i % 3]));

            assertEquals(testGraphStringDouble.edgeSet(strings[i]).size(), 2);
            assertTrue(testGraphStringDouble.edgeSet(strings[i])
                    .contains(doubles[i % 3]));
            assertTrue(testGraphStringDouble.edgeSet(strings[i])
                    .contains(doubles[(i + 2) % 3]));

            assertEquals(testGraphIntegerInteger.edgeSet(integers[i]).size(),
                    2);
            assertTrue(testGraphIntegerInteger.edgeSet(integers[i])
                    .contains(integers[i % 3]));
            assertTrue(testGraphIntegerInteger.edgeSet(integers[i])
                    .contains(integers[(i + 2) % 3]));
        }

        /*
         * Test negative edge containment from a vertex not loaded in graph
         */
        assertNull(testGraphStringStringNull.edgeSet("four"));
        assertNull(testGraphStringString.edgeSet("four"));
        assertNull(testGraphStringDouble.edgeSet("four"));
        assertNull(testGraphIntegerInteger.edgeSet(4));

        /*
         * Test NullPointerException thrown if trying to get edge set of a null
         * vertex
         */
        try {
            testGraphStringStringNull.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringString.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringDouble.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            testGraphStringDouble.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }

        /*
         * Test negative edge (v) containment v in the graph
         * 
         * Since test graphs all only contain 3 vertices and are thus fully
         * connected, we need to add a few more to each to ensure unconnected
         * vertices exist in graph in order to test properly.
         */
        testGraphStringStringNull.addVertex("four");
        assertTrue(testGraphStringStringNull.edgeSet("four").isEmpty());

        testGraphStringString.addVertex("four");
        assertTrue(testGraphStringString.edgeSet("four").isEmpty());

        testGraphStringDouble.addVertex("four");
        assertTrue(testGraphStringDouble.edgeSet("four").isEmpty());

        testGraphIntegerInteger.addVertex(4);
        assertTrue(testGraphIntegerInteger.edgeSet(4).isEmpty());

        ///// TESTING edgeSet(v1, v2) /////

        /*
         * Test edge containment from two vertices in new graph
         */
        clearGraph();
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).isEmpty());
            assertTrue(testGraphStringString
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).isEmpty());
            assertTrue(testGraphStringDouble
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).isEmpty());
            assertTrue(testGraphIntegerInteger
                    .edgeSet(integers[i % 3], integers[(i + 1) % 3]).isEmpty());
        }

        /*
         * Test positive edge containment from two vertices in a loaded graph
         */
        reloadGraphData();
        // System.out.println("Whole set: " +
        // testGraphStringStringNull.edgeSet());
        // System.out.println(
        // "one set: " + testGraphStringStringNull.edgeSet(strings[0]));
        // System.out.println(
        // "two set: " + testGraphStringStringNull.edgeSet(strings[1]));
        // System.out.println(
        // "three set: " + testGraphStringStringNull.edgeSet(strings[2]));
        // System.out.println("one-two set: "
        // + testGraphStringStringNull.edgeSet(strings[0], strings[1]));
        // System.out.println("two-three set: "
        // + testGraphStringStringNull.edgeSet(strings[1], strings[2]));
        // System.out.println("one-three set: "
        // + testGraphStringStringNull.edgeSet(strings[0], strings[2]));
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            // System.out.println(testGraphStringStringNull.edgeSet(strings[i %
            // 3],
            // strings[(i + 1) % 3]));
            // System.out.println(testGraphStringStringNull
            // .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size());
            assertEquals(testGraphStringStringNull
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size(), 1);
            assertTrue(testGraphStringStringNull
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(null));

            // System.out.println(testGraphStringString
            // .edgeSet(strings[i % 3], strings[(i + 1) % 3]));
            assertEquals(testGraphStringString
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size(), 1);
            assertTrue(testGraphStringString
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));

            assertEquals(testGraphStringDouble
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size(), 1);
            assertTrue(testGraphStringDouble
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(doubles[i % 3]));

            assertEquals(testGraphIntegerInteger
                    .edgeSet(integers[i % 3], integers[(i + 1) % 3]).size(), 1);
            assertTrue(testGraphIntegerInteger
                    .edgeSet(integers[i % 3], integers[(i + 1) % 3])
                    .contains(integers[i % 3]));
        }
    }

    /**
     * Clears graph data
     */
    private void clearGraph() {
        setUp();
    }

    /**
     * Clears and reloads graph data (vertices & edges)
     */
    private void reloadGraphData() {
        clearGraph();
        loadGraphData();
    }

    /**
     * Loads data (vertices & edges) into graphs for use in tests
     */
    private void loadGraphData() {
        loadVertices();
        loadEdges();
    }

    /**
     * Loads vertex data into graphs
     */
    private void loadVertices() {
        // String type vertices
        for (String s : strings) {
            testGraphStringStringNull.addVertex(s);
            testGraphStringString.addVertex(s);
            testGraphStringDouble.addVertex(s);
        }

        // Integer type vertices
        for (Integer i : integers) {
            testGraphIntegerInteger.addVertex(i);
        }
    }

    /**
     * Loads edge data into graphs
     */
    private void loadEdges() {
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            // String/String/null edge value
            testGraphStringStringNull.addEdge(strings[i % 3],
                    strings[(i + 1) % 3]);

//            System.out.println(testGraphStringStringNull.getEdge(strings[i % 3],
//                    strings[(i + 1) % 3]));

            // String/String
            testGraphStringString.addEdge(strings[i % 3], strings[(i + 1) % 3],
                    strings[i % 3] + "-" + strings[(i + 1) % 3]);

            // String/Double
            testGraphStringDouble.addEdge(strings[i % 3], strings[(i + 1) % 3],
                    doubles[i]);

            // Integer/Integer
            testGraphIntegerInteger.addEdge(integers[i % 3],
                    integers[(i + 1) % 3], integers[i]);
        }
    }

    private class ComplexObject {

        String[]  s;
        Double[]  d;
        Integer[] i;

        public ComplexObject(String[] s, Double[] d, Integer[] i) {
            this.s = s.clone();
            this.d = d.clone();
            this.i = i.clone();
        }

    }

}
