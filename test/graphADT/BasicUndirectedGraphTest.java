package graphADT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        assertTrue(testGraphStringStringNull.vertexSet().isEmpty());
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());

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
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.addVertex(strings[i]));
            assertTrue(testGraphStringString.addVertex(strings[i]));
            assertTrue(testGraphStringDouble.addVertex(strings[i]));
            assertTrue(testGraphIntegerInteger.addVertex(integers[i]));
        }

        // try graph of more complex objects
        BasicUndirectedGraph<ComplexObject,String> complexObjectGraph;
        complexObjectGraph = new BasicUndirectedGraph<ComplexObject,String>();
        ComplexObject co = new ComplexObject(strings, doubles, integers);
        assertTrue(complexObjectGraph.addVertex(co));

        /*
         * Add duplicate vertices
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.addVertex(strings[i]));
            assertFalse(testGraphStringString.addVertex(strings[i]));
            assertFalse(testGraphStringDouble.addVertex(strings[i]));
            assertFalse(testGraphIntegerInteger.addVertex(integers[i]));
        }
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
            assertEquals("Vertex value null", npe.getMessage());
        }

        try {
            complexObjectGraph.addVertex(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
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

            assertTrue(testGraphStringString.addEdge(strings[i % 3],
                    strings[(i + 1) % 3],
                    strings[i % 3] + "-" + strings[(i + 1) % 3]));

            assertTrue(testGraphStringDouble.addEdge(strings[i % 3],
                    strings[(i + 1) % 3], doubles[i]));

            assertTrue(testGraphIntegerInteger.addEdge(integers[i % 3],
                    integers[(i + 1) % 3], integers[i]));
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
         * Add duplicate edge
         * 
         * adds a duplicate edge already present in graph and asserts addition
         * was unsuccessful
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
         * Add edge with null vertex
         * 
         * adds an edge where vertex 1 or 2 or both are null these should throw
         * NullPointerExceptions
         */
        clearGraph(); // clear and reload vertices
        loadVertices();
        try {
            testGraphStringString.addEdge(strings[0], null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.addEdge(null, strings[0]);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.addEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
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
         * Test negative vertex containment in new unloaded graph
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.containsVertex(strings[i]));
            assertFalse(testGraphStringString.containsVertex(strings[i]));
            assertFalse(testGraphStringDouble.containsVertex(strings[i]));
            assertFalse(testGraphIntegerInteger.containsVertex(integers[i]));
        }

        /*
         * Test positive vertex containment in loaded graph
         */
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.containsVertex(strings[i]));
            assertTrue(testGraphStringString.containsVertex(strings[i]));
            assertTrue(testGraphStringDouble.containsVertex(strings[i]));
            assertTrue(testGraphIntegerInteger.containsVertex(integers[i]));
        }

        /*
         * Test negative vertex containment in loaded graph
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
        ///// TESTING containsEdge(e) /////
        /*
         * Test negative edge containment in new unloaded graph
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.containsEdge(null));
            assertFalse(testGraphStringString
                    .containsEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertFalse(testGraphStringDouble.containsEdge(doubles[i]));
            assertFalse(testGraphIntegerInteger.containsEdge(integers[i]));
        }

        /*
         * Test positive edge value containment for loaded graph
         */
        loadGraphData();
        assertTrue(testGraphStringStringNull.containsEdge(null));
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringString
                    .containsEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringDouble.containsEdge(doubles[i]));
            assertTrue(testGraphIntegerInteger.containsEdge(integers[i]));
        }

        /*
         * Test negative edge value containment for loaded graph
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

        ///// TESTING containsEdge(e) /////
        /*
         * Test negative edge (v1//v2, v2//v1) containment in new graph
         */
        clearGraph();
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertFalse(testGraphStringStringNull
                    .containsEdge(strings[(i + 1) % 3], strings[i % 3]));

            assertFalse(testGraphStringString.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertFalse(testGraphStringString.containsEdge(strings[(i + 1) % 3],
                    strings[i % 3]));

            assertFalse(testGraphStringDouble.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertFalse(testGraphStringDouble.containsEdge(strings[(i + 1) % 3],
                    strings[i % 3]));

            assertFalse(testGraphIntegerInteger.containsEdge(integers[i % 3],
                    integers[(i + 1) % 3]));
            assertFalse(testGraphIntegerInteger
                    .containsEdge(integers[(i + 1) % 3], integers[i % 3]));
        }

        /*
         * Test positive edge (v1//v2, v2//v1) containment in loaded graph
         */
        reloadGraphData();
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
        testGraphStringString.addVertex("four");
        testGraphStringString.addVertex("five");
        testGraphStringDouble.addVertex("four");
        testGraphStringDouble.addVertex("five");
        testGraphIntegerInteger.addVertex(4);
        testGraphIntegerInteger.addVertex(5);

        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(
                    testGraphStringStringNull.containsEdge(strings[i], "four"));
            assertFalse(
                    testGraphStringStringNull.containsEdge("four", strings[i]));
            assertFalse(testGraphStringStringNull.containsEdge("four", "five"));
            assertFalse(testGraphStringStringNull.containsEdge("five", "four"));

            assertFalse(testGraphStringString.containsEdge(strings[i], "four"));
            assertFalse(testGraphStringString.containsEdge("four", strings[i]));
            assertFalse(testGraphStringString.containsEdge("four", "five"));
            assertFalse(testGraphStringString.containsEdge("five", "four"));

            assertFalse(testGraphStringDouble.containsEdge(strings[i], "four"));
            assertFalse(testGraphStringDouble.containsEdge("four", strings[i]));
            assertFalse(testGraphStringDouble.containsEdge("four", "five"));
            assertFalse(testGraphStringDouble.containsEdge("five", "four"));

            assertFalse(testGraphIntegerInteger.containsEdge(integers[i], 4));
            assertFalse(testGraphIntegerInteger.containsEdge(4, integers[i]));
            assertFalse(testGraphIntegerInteger.containsEdge(4, 5));
            assertFalse(testGraphIntegerInteger.containsEdge(5, 4));
        }

        /*
         * Test negative edge (v1, v2) containment where either v1, v2, or both
         * aren't in the graph
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(
                    testGraphStringStringNull.containsEdge(strings[i], "four"));
            assertFalse(
                    testGraphStringStringNull.containsEdge("four", strings[i]));
            assertFalse(testGraphStringStringNull.containsEdge("four", "five"));
            assertFalse(testGraphStringStringNull.containsEdge("five", "four"));

            assertFalse(testGraphStringString.containsEdge(strings[i], "four"));
            assertFalse(testGraphStringString.containsEdge("four", strings[i]));
            assertFalse(testGraphStringString.containsEdge("four", "five"));
            assertFalse(testGraphStringString.containsEdge("five", "four"));

            assertFalse(testGraphStringDouble.containsEdge(strings[i], "four"));
            assertFalse(testGraphStringDouble.containsEdge("four", strings[i]));
            assertFalse(testGraphStringDouble.containsEdge("four", "five"));
            assertFalse(testGraphStringDouble.containsEdge("five", "four"));

            assertFalse(testGraphIntegerInteger.containsEdge(integers[i], 4));
            assertFalse(testGraphIntegerInteger.containsEdge(4, integers[i]));
            assertFalse(testGraphIntegerInteger.containsEdge(4, 5));
            assertFalse(testGraphIntegerInteger.containsEdge(5, 4));
        }

        /*
         * Test NullPointerException thrown when vertex v1, v2, or both are null
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            try {
                testGraphStringString.containsEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringString.containsEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
        }
        try {
            testGraphStringString.containsEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
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
        assertEquals(strings.length,
                testGraphStringStringNull.vertexSet().size());
        assertTrue(testGraphStringStringNull.vertexSet()
                .containsAll(Arrays.asList(strings)));

        assertEquals(strings.length, testGraphStringString.vertexSet().size());
        assertTrue(testGraphStringString.vertexSet()
                .containsAll(Arrays.asList(strings)));

        assertEquals(strings.length, testGraphStringDouble.vertexSet().size());
        assertTrue(testGraphStringDouble.vertexSet()
                .containsAll(Arrays.asList(strings)));

        assertEquals(strings.length,
                testGraphIntegerInteger.vertexSet().size());
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
         * Test immutability of vertex set of graph
         * 
         * (1) Test by removing a vertex from the vertex set and check that the
         * underlying graph is unaffected.
         * 
         * (2) Test removing collection from vertex set and check that the
         * underlying graph is unaffected.
         */
        Set<String> vSet;

        // (1)
        vSet = testGraphStringString.vertexSet();
        vSet.remove(strings[2]);
        assertTrue(testGraphStringString.containsVertex(strings[2]));

        // (2)
        clearGraph();
        loadVertices();
        vSet = testGraphStringString.vertexSet();
        vSet.removeAll(Arrays.asList(strings));
        for (String s : strings) {
            assertTrue(testGraphStringString.containsVertex(s));
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
        assertEquals(1, testGraphStringStringNull.edgeSet().size());
        assertEquals(strings.length, testGraphStringString.edgeSet().size());
        assertEquals(strings.length, testGraphStringDouble.edgeSet().size());
        assertEquals(strings.length, testGraphIntegerInteger.edgeSet().size());

        assertTrue(testGraphStringStringNull.edgeSet().contains(null));
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringString.edgeSet()
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringDouble.edgeSet().contains(doubles[i]));
            assertTrue(testGraphIntegerInteger.edgeSet().contains(integers[i]));
        }

        /*
         * Test negative edge containment from loaded graphs
         */
        reloadGraphData();
        assertFalse(testGraphStringStringNull.edgeSet().contains("four"));
        assertFalse(testGraphStringString.edgeSet().contains("four"));
        assertFalse(testGraphStringDouble.edgeSet().contains(4d));
        assertFalse(testGraphIntegerInteger.edgeSet().contains(4));

        /*
         * Test immutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that the
         * underlying graph is unaffected.
         * 
         * (2) Test removing collection from edge set and check that the
         * underlying graph is unaffected.
         */
        Set<Double> eSet;

        // (1)
        eSet = testGraphStringDouble.edgeSet();
        eSet.remove(doubles[2]);
        assertTrue(testGraphStringDouble.containsEdge(doubles[2]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet();
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertTrue(testGraphStringDouble.containsEdge(d));
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
            assertEquals(1,
                    testGraphStringStringNull.edgeSet(strings[i]).size());
            assertTrue(testGraphStringStringNull.edgeSet(strings[i])
                    .contains(null));

            assertEquals(2, testGraphStringString.edgeSet(strings[i]).size());
            assertTrue(testGraphStringString.edgeSet(strings[i])
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringString.edgeSet(strings[i])
                    .contains(strings[(i + 2) % 3] + "-" + strings[i % 3]));

            assertEquals(2, testGraphStringDouble.edgeSet(strings[i]).size());
            assertTrue(testGraphStringDouble.edgeSet(strings[i])
                    .contains(doubles[i % 3]));
            assertTrue(testGraphStringDouble.edgeSet(strings[i])
                    .contains(doubles[(i + 2) % 3]));

            assertEquals(2,
                    testGraphIntegerInteger.edgeSet(integers[i]).size());
            assertTrue(testGraphIntegerInteger.edgeSet(integers[i])
                    .contains(integers[i % 3]));
            assertTrue(testGraphIntegerInteger.edgeSet(integers[i])
                    .contains(integers[(i + 2) % 3]));
        }

        /*
         * Test negative edge containment from a vertex not loaded in graph
         */
        reloadGraphData();
        assertNull(testGraphStringStringNull.edgeSet("four"));
        assertNull(testGraphStringString.edgeSet("four"));
        assertNull(testGraphStringDouble.edgeSet("four"));
        assertNull(testGraphIntegerInteger.edgeSet(4));

        /*
         * Test NullPointerException thrown if trying to get edge set of a null
         * vertex
         */
        reloadGraphData();
        try {
            testGraphStringStringNull.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringDouble.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.edgeSet(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }

        /*
         * Test negative edge (v) containment v in the graph
         * 
         * Since test graphs all only contain 3 vertices and are thus fully
         * connected, we need to add a few more to each to ensure unconnected
         * vertices exist in graph in order to test properly.
         */
        reloadGraphData();
        testGraphStringStringNull.addVertex("four");
        assertTrue(testGraphStringStringNull.edgeSet("four").isEmpty());

        testGraphStringString.addVertex("four");
        assertTrue(testGraphStringString.edgeSet("four").isEmpty());

        testGraphStringDouble.addVertex("four");
        assertTrue(testGraphStringDouble.edgeSet("four").isEmpty());

        testGraphIntegerInteger.addVertex(4);
        assertTrue(testGraphIntegerInteger.edgeSet(4).isEmpty());

        /*
         * Test immutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that the
         * underlying graph is unaffected.
         * 
         * (2) Test removing collection from edge set and check that the
         * underlying graph is unaffected.
         */

        // (1)
        eSet = testGraphStringDouble.edgeSet(strings[2]);
        eSet.remove(doubles[2]);
        assertTrue(testGraphStringDouble.containsEdge(doubles[2]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[2]);
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertTrue(testGraphStringDouble.containsEdge(d));
        }

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
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertEquals(1, testGraphStringStringNull
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size());
            assertTrue(testGraphStringStringNull
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(null));

            assertEquals(1, testGraphStringString
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size());
            assertTrue(testGraphStringString
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));

            assertEquals(1, testGraphStringDouble
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3]).size());
            assertTrue(testGraphStringDouble
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(doubles[i % 3]));

            assertEquals(1, testGraphIntegerInteger
                    .edgeSet(integers[i % 3], integers[(i + 1) % 3]).size());
            assertTrue(testGraphIntegerInteger
                    .edgeSet(integers[i % 3], integers[(i + 1) % 3])
                    .contains(integers[i % 3]));
        }

        /*
         * Test negative edge containment from vertex v1, v2, or both not loaded
         * in graph
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertNull(testGraphStringStringNull.edgeSet(strings[i], "four"));
            assertNull(testGraphStringStringNull.edgeSet("four", strings[i]));
            assertNull(testGraphStringStringNull.edgeSet("four", "five"));

            assertNull(testGraphStringString.edgeSet(strings[i], "four"));
            assertNull(testGraphStringString.edgeSet("four", strings[i]));
            assertNull(testGraphStringString.edgeSet("four", "five"));

            assertNull(testGraphStringDouble.edgeSet(strings[i], "four"));
            assertNull(testGraphStringDouble.edgeSet("four", strings[i]));
            assertNull(testGraphStringDouble.edgeSet("four", "five"));

            assertNull(testGraphIntegerInteger.edgeSet(integers[i], 4));
            assertNull(testGraphIntegerInteger.edgeSet(4, integers[i]));
            assertNull(testGraphIntegerInteger.edgeSet(4, 5));
        }

        /*
         * Test NullPointerException thrown if trying to get edge set of a null
         * vertex
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            try {
                testGraphStringStringNull.edgeSet(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringStringNull.edgeSet(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringString.edgeSet(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringString.edgeSet(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringDouble.edgeSet(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringDouble.edgeSet(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphIntegerInteger.edgeSet(integers[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphIntegerInteger.edgeSet(null, integers[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
        }
        try {
            testGraphStringStringNull.edgeSet(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.edgeSet(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringDouble.edgeSet(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.edgeSet(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }

        /*
         * Test negative edge (v1, v2) containment with v1 and v2 in the graph
         * 
         * Since test graphs all only contain 3 vertices and are thus fully
         * connected, we need to add a few more to each to ensure unconnected
         * vertices exist in graph in order to test properly.
         */
        reloadGraphData();
        testGraphStringStringNull.addVertex("four");
        testGraphStringString.addVertex("four");
        testGraphStringDouble.addVertex("four");
        testGraphIntegerInteger.addVertex(4);
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.edgeSet(strings[i], "four")
                    .isEmpty());
            assertTrue(testGraphStringStringNull.edgeSet("four", strings[i])
                    .isEmpty());

            assertTrue(testGraphStringString.edgeSet(strings[i], "four")
                    .isEmpty());
            assertTrue(testGraphStringString.edgeSet("four", strings[i])
                    .isEmpty());

            assertTrue(testGraphStringDouble.edgeSet(strings[i], "four")
                    .isEmpty());
            assertTrue(testGraphStringDouble.edgeSet("four", strings[i])
                    .isEmpty());

            assertTrue(
                    testGraphIntegerInteger.edgeSet(integers[i], 4).isEmpty());
            assertTrue(
                    testGraphIntegerInteger.edgeSet(4, integers[i]).isEmpty());
        }

        /*
         * Test immutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that the
         * underlying graph is unaffected.
         * 
         * (2) Test removing collection from edge set and check that the
         * underlying graph is unaffected.
         */

        // (1)
        eSet = testGraphStringDouble.edgeSet(strings[0], strings[2]);
        eSet.remove(doubles[2]);
        assertTrue(testGraphStringDouble.containsEdge(doubles[2]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[0], strings[2]);
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertTrue(testGraphStringDouble.containsEdge(d));
        }
    }

    /**
     * Unit tests for get edge from graph
     */
    @Test
    public void getEdge() {
        /*
         * Test new empty graphs
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            for (int j = 0; j <= 2; j++) { // j = {0, 1, 2}
                assertNull(testGraphStringStringNull.getEdge(strings[i],
                        strings[j]));
                assertNull(
                        testGraphStringString.getEdge(strings[i], strings[j]));
                assertNull(
                        testGraphStringDouble.getEdge(strings[i], strings[j]));
                assertNull(testGraphIntegerInteger.getEdge(integers[i],
                        integers[j]));
            }
        }

        /*
         * Test new vertex only loaded graph
         */
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            for (int j = 0; j <= 2; j++) { // j = {0, 1, 2}
                assertNull(testGraphStringStringNull.getEdge(strings[i],
                        strings[j]));
                assertNull(
                        testGraphStringString.getEdge(strings[i], strings[j]));
                assertNull(
                        testGraphStringDouble.getEdge(strings[i], strings[j]));
                assertNull(testGraphIntegerInteger.getEdge(integers[i],
                        integers[j]));
            }
        }

        /*
         * Test positive edge value in new loaded graph
         */
        loadEdges();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertEquals(null, testGraphStringStringNull.getEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertEquals(strings[i % 3] + "-" + strings[(i + 1) % 3],
                    testGraphStringString.getEdge(strings[i % 3],
                            strings[(i + 1) % 3]));
            assertEquals(doubles[i], testGraphStringDouble
                    .getEdge(strings[i % 3], strings[(i + 1) % 3]));
            assertEquals(integers[i], testGraphIntegerInteger
                    .getEdge(integers[i % 3], integers[(i + 1) % 3]));
        }

        /*
         * Test negative edge value in loaded graph
         * 
         * Since test graphs all only contain 3 vertices and are thus fully
         * connected, we need to add a few more to each to ensure unconnected
         * vertices exist in graph in order to test properly.
         */
        reloadGraphData();
        testGraphStringStringNull.addVertex("four");
        testGraphStringStringNull.addVertex("five");
        testGraphStringString.addVertex("four");
        testGraphStringString.addVertex("five");
        testGraphStringDouble.addVertex("four");
        testGraphStringDouble.addVertex("five");
        testGraphIntegerInteger.addVertex(4);
        testGraphIntegerInteger.addVertex(5);

        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertNull(testGraphStringStringNull.getEdge(strings[i], "four"));
            assertNull(testGraphStringStringNull.getEdge("four", strings[i]));
            assertNull(testGraphStringStringNull.getEdge("four", "five"));

            assertNull(testGraphStringString.getEdge(strings[i], "four"));
            assertNull(testGraphStringString.getEdge("four", strings[i]));
            assertNull(testGraphStringString.getEdge("four", "five"));

            assertNull(testGraphStringDouble.getEdge(strings[i], "four"));
            assertNull(testGraphStringDouble.getEdge("four", strings[i]));
            assertNull(testGraphStringDouble.getEdge("four", "five"));

            assertNull(testGraphIntegerInteger.getEdge(integers[i], 4));
            assertNull(testGraphIntegerInteger.getEdge(4, integers[i]));
            assertNull(testGraphIntegerInteger.getEdge(4, 5));
        }

        /*
         * Test NullPointerException thrown if trying to get edge set of a null
         * vertex
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            try {
                testGraphStringStringNull.getEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringStringNull.getEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringString.getEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringString.getEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringDouble.getEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringDouble.getEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphIntegerInteger.getEdge(integers[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphIntegerInteger.getEdge(null, integers[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
        }
        try {
            testGraphStringStringNull.getEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.getEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringDouble.getEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.getEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }

    }

    /**
     * Tests removing all edges from graph functionality
     */
    @Test
    public void removeAllEdges() {
        ///// TESTING removeAllEdges(Collection) /////
        /*
         * Remove edge collection from new empty graph
         */
        assertFalse(testGraphStringStringNull
                .removeAllEdges(Arrays.asList(strings)));
        assertFalse(
                testGraphStringString.removeAllEdges(Arrays.asList(strings)));
        assertFalse(
                testGraphStringDouble.removeAllEdges(Arrays.asList(doubles)));
        assertFalse(testGraphIntegerInteger
                .removeAllEdges(Arrays.asList(integers)));

        /*
         * Remove full edge collection from loaded graph
         */
        reloadGraphData();
        assertTrue(testGraphStringStringNull
                .removeAllEdges(testGraphStringStringNull.edgeSet()));
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());
        assertTrue(testGraphStringString
                .removeAllEdges(testGraphStringString.edgeSet()));
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        assertTrue(testGraphStringDouble
                .removeAllEdges(testGraphStringDouble.edgeSet()));
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());
        assertTrue(testGraphIntegerInteger
                .removeAllEdges(testGraphIntegerInteger.edgeSet()));
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());

        /*
         * Remove partial edge collection from loaded graph
         */
        reloadGraphData();
        Set<String> removeStringSet = testGraphStringStringNull
                .edgeSet(strings[0]);
        assertTrue(testGraphStringStringNull.removeAllEdges(removeStringSet));
        assertFalse(testGraphStringStringNull.edgeSet()
                .containsAll(removeStringSet));

        removeStringSet = testGraphStringString.edgeSet(strings[0]);
        assertTrue(testGraphStringString.removeAllEdges(removeStringSet));
        assertFalse(
                testGraphStringString.edgeSet().containsAll(removeStringSet));

        Set<Double> removeDoubleSet = testGraphStringDouble.edgeSet(strings[0]);
        assertTrue(testGraphStringDouble.removeAllEdges(removeDoubleSet));
        assertFalse(
                testGraphStringDouble.edgeSet().containsAll(removeDoubleSet));

        Set<Integer> removeIntSet = testGraphIntegerInteger
                .edgeSet(integers[0]);
        assertTrue(testGraphIntegerInteger.removeAllEdges(removeIntSet));
        assertFalse(
                testGraphIntegerInteger.edgeSet().containsAll(removeIntSet));

        /*
         * Test NullPointerException is thrown on null collections
         */
        reloadGraphData();
        try {
            testGraphStringStringNull.removeAllEdges(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Edge collection null", npe.getMessage());
        }
        try {
            testGraphStringString.removeAllEdges(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Edge collection null", npe.getMessage());
        }
        try {
            testGraphStringDouble.removeAllEdges(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Edge collection null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.removeAllEdges(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Edge collection null", npe.getMessage());
        }

        ///// TESTING removeAllEdges(v1, v2) /////
        /*
         * Remove edges between vertices from new empty graph
         */
        clearGraph();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            for (int j = 0; j <= 2; j++) { // j = {0, 1, 2}
                assertNull(testGraphStringStringNull.removeAllEdges(strings[i],
                        strings[j]));
                assertNull(testGraphStringString.removeAllEdges(strings[i],
                        strings[j]));
                assertNull(testGraphStringDouble.removeAllEdges(strings[i],
                        strings[j]));
                assertNull(testGraphIntegerInteger.removeAllEdges(integers[i],
                        integers[j]));
            }
        }

        /*
         * Remove edges between vertices from vertex loaded graph (no edges)
         */
        clearGraph();
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            for (int j = 0; j <= 2; j++) { // j = {0, 1, 2}
                assertTrue(testGraphStringStringNull
                        .removeAllEdges(strings[i], strings[j]).isEmpty());
                assertTrue(testGraphStringString
                        .removeAllEdges(strings[i], strings[j]).isEmpty());
                assertTrue(testGraphStringDouble
                        .removeAllEdges(strings[i], strings[j]).isEmpty());
                assertTrue(testGraphIntegerInteger
                        .removeAllEdges(integers[i], integers[j]).isEmpty());
            }
        }

        /*
         * Remove edges between vertices from fully loaded graph
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull
                    .removeAllEdges(strings[i % 3], strings[(i + 1) % 3])
                    .contains(null));
            assertFalse(testGraphStringStringNull
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(null));

            assertTrue(testGraphStringString
                    .removeAllEdges(strings[i % 3], strings[(i + 1) % 3])
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertFalse(testGraphStringString
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(strings[i % 3] + "-" + strings[(i + 1) % 3]));

            assertTrue(testGraphStringDouble
                    .removeAllEdges(strings[i % 3], strings[(i + 1) % 3])
                    .contains(doubles[i]));
            assertFalse(testGraphStringDouble
                    .edgeSet(strings[i % 3], strings[(i + 1) % 3])
                    .contains(doubles[i]));

            assertTrue(testGraphIntegerInteger
                    .removeAllEdges(integers[i % 3], integers[(i + 1) % 3])
                    .contains(integers[i]));
            assertFalse(testGraphIntegerInteger
                    .edgeSet(integers[i % 3], integers[(i + 1) % 3])
                    .contains(integers[i]));
        }
        // all graphs should now have no edges
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());

        /*
         * Test removing edge set containing self edges
         */
        reloadGraphData();
        testGraphStringString.addEdge(strings[0], strings[0], "self-edge");
        Set<String> selfEdgeTest = testGraphStringString
                .removeAllEdges(strings[0], strings[0]);
        assertEquals(1, selfEdgeTest.size());
        testGraphStringString.addEdge(strings[0], strings[0], "self-edge1");
        testGraphStringString.addEdge(strings[0], strings[0], "self-edge2");
        selfEdgeTest = testGraphStringString.removeAllEdges(strings[0],
                strings[0]);
        assertEquals(2, selfEdgeTest.size());

        /*
         * Test NullPointerException is thrown on null vertices
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            try {
                testGraphStringStringNull.removeAllEdges(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringStringNull.removeAllEdges(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringString.removeAllEdges(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringString.removeAllEdges(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringDouble.removeAllEdges(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringDouble.removeAllEdges(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphIntegerInteger.removeAllEdges(integers[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphIntegerInteger.removeAllEdges(null, integers[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
        }
        try {
            testGraphStringStringNull.removeAllEdges(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.removeAllEdges(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringDouble.removeAllEdges(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.removeAllEdges(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }

    }

    @Test
    public void removeAllVertices() {
        List<String> tempStrings = new ArrayList<String>(
                Arrays.asList(strings));
        tempStrings.add("four");

        /*
         * Remove vertex collection from new empty graph
         */
        assertFalse(testGraphStringStringNull
                .removeAllVertices(Arrays.asList(strings)));
        assertFalse(testGraphStringStringNull
                .removeAllVertices(tempStrings));
        
        assertFalse(testGraphStringString
                .removeAllVertices(Arrays.asList(strings)));
        assertFalse(testGraphStringString
                .removeAllVertices(tempStrings));
        
        assertFalse(testGraphStringDouble
                .removeAllVertices(Arrays.asList(strings)));
        assertFalse(testGraphStringDouble
                .removeAllVertices(tempStrings));
        
        assertFalse(testGraphIntegerInteger
                .removeAllVertices(Arrays.asList(integers)));
        
        /*
         * Remove vertex collection from loaded graph
         */
        reloadGraphData();
        assertTrue(testGraphStringStringNull
                .removeAllVertices(Arrays.asList(strings)));
        reloadGraphData();
        assertTrue(testGraphStringStringNull
                .removeAllVertices(tempStrings));
        
        reloadGraphData();
        assertTrue(testGraphStringString
                .removeAllVertices(Arrays.asList(strings)));
        reloadGraphData();
        assertTrue(testGraphStringString
                .removeAllVertices(tempStrings));
        
        reloadGraphData();
        assertTrue(testGraphStringDouble
                .removeAllVertices(Arrays.asList(strings)));
        reloadGraphData();
        assertTrue(testGraphStringDouble
                .removeAllVertices(tempStrings));
        
        reloadGraphData();
        assertTrue(testGraphIntegerInteger
                .removeAllVertices(Arrays.asList(integers)));
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

        @SuppressWarnings("unused")
        String[]  s;
        @SuppressWarnings("unused")
        Double[]  d;
        @SuppressWarnings("unused")
        Integer[] i;

        public ComplexObject(String[] s, Double[] d, Integer[] i) {
            this.s = s.clone();
            this.d = d.clone();
            this.i = i.clone();
        }

    }

}
