package graphADT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UndirectedGraphTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // test graphs
    UndirectedGraph<String,String>   testGraphStringString;
    UndirectedGraph<String,String>   testGraphStringStringNull;
    UndirectedGraph<String,Double>   testGraphStringDouble;
    UndirectedGraph<Integer,Integer> testGraphIntegerInteger;

    // test data sets
    String[]  strings  = { "one", "two", "three" };
    Double[]  doubles  = { 1d, 2d, 3d };
    Integer[] integers = { 1, 2, 3 };

    /**
     * Construct new graphs before each test
     */
    @Before
    public void setUp() {
        testGraphStringStringNull = new UndirectedGraph<String,String>();
        testGraphStringString = new UndirectedGraph<String,String>();
        testGraphStringDouble = new UndirectedGraph<String,Double>();
        testGraphIntegerInteger = new UndirectedGraph<Integer,Integer>();
    }

    /**
     * Unit test for UndirectedGraph constructor
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
        UndirectedGraph<ComplexObject,String> complexObjectGraph;
        complexObjectGraph = new UndirectedGraph<ComplexObject,String>();
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
        clearGraphData(); // clear and reload vertices
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
        clearGraphData(); // clear and reload vertices
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

        ///// TESTING containsEdge(v1, v2) /////
        /*
         * Test negative edge (v1//v2, v2//v1) containment in new graph
         */
        clearGraphData();
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
         * Test mutability of vertex set of graph
         * 
         * (1) Test by removing a vertex from the vertex set and check that the
         * underlying graph is unaffected and vice versa.
         * 
         * (2) Test removing collection from vertex set and check that the
         * underlying graph is unaffected and vice versa.
         */
        Set<String> vSet;

        // (1)
        clearGraphData();
        loadVertices();
        vSet = testGraphStringString.vertexSet();
        vSet.remove(strings[2]);
        assertTrue(testGraphStringString.containsVertex(strings[2]));
        
        testGraphStringString.removeVertex(strings[0]);
        assertTrue(vSet.contains(strings[0]));

        // (2)
        clearGraphData();
        loadVertices();
        vSet = testGraphStringString.vertexSet();
        vSet.removeAll(Arrays.asList(strings));
        for (String s : strings) {
            assertTrue(testGraphStringString.containsVertex(s));
        }
        
        clearGraphData();
        loadVertices();
        vSet = testGraphStringString.vertexSet();
        testGraphStringString.removeAllVertices(Arrays.asList(strings));
        assertTrue(vSet.containsAll(Arrays.asList(strings)));
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
         * Test mutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that the
         * underlying graph is unaffected and vice versa.
         * 
         * (2) Test removing collection from edge set and check that the
         * underlying graph is unaffected and vice versa.
         */
        Set<Double> eSet;

        // (1)
        eSet = testGraphStringDouble.edgeSet();
        eSet.remove(doubles[2]);
        assertTrue(testGraphStringDouble.containsEdge(doubles[2]));
        
        testGraphStringDouble.removeEdge(doubles[0]);
        assertTrue(eSet.contains(doubles[0]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet();
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertTrue(testGraphStringDouble.containsEdge(d));
        }
        
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet();
        testGraphStringDouble.removeAllEdges(Arrays.asList(doubles));
        assertTrue(eSet.containsAll(Arrays.asList(doubles)));

        ///// TESTING edgeSet(v) /////
        /*
         * Test edge containment from a vertex of new graph
         */
        clearGraphData();
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
         * Test mutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that the
         * underlying graph is unaffected and vice versa.
         * 
         * (2) Test removing collection from edge set and check that the
         * underlying graph is unaffected and vice versa.
         */

        // (1)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[2]);
        eSet.remove(doubles[2]);
        assertTrue(testGraphStringDouble.containsEdge(doubles[2]));
        
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[2]);
        testGraphStringDouble.removeEdge(doubles[2]);
        assertTrue(eSet.contains(doubles[2]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[2]);
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertTrue(testGraphStringDouble.containsEdge(d));
        }
        
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[2]);
        testGraphStringDouble.removeAllEdges(Arrays.asList(doubles));
        assertTrue(eSet.containsAll(Arrays.asList(new Double[]{2d,3d})));

        ///// TESTING edgeSet(v1, v2) /////
        /*
         * Test edge containment from two vertices in new graph
         */
        clearGraphData();
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
         * Test mutability of edge set of graph
         * 
         * (1) Test by removing an edge from the edge set and check that the
         * underlying graph is unaffected and vice versa.
         * 
         * (2) Test removing collection from edge set and check that the
         * underlying graph is unaffected and vice versa.
         */

        // (1)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[0], strings[2]);
        eSet.remove(doubles[2]);
        assertTrue(testGraphStringDouble.containsEdge(doubles[2]));
        
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[0], strings[2]);
        testGraphStringDouble.removeEdge(doubles[2]);
        assertTrue(eSet.contains(doubles[2]));

        // (2)
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[0], strings[2]);
        eSet.removeAll(Arrays.asList(doubles));
        for (Double d : doubles) {
            assertTrue(testGraphStringDouble.containsEdge(d));
        }
        
        reloadGraphData();
        eSet = testGraphStringDouble.edgeSet(strings[0], strings[2]);
        testGraphStringDouble.removeAllEdges(Arrays.asList(doubles));
        assertTrue(eSet.containsAll(Arrays.asList(doubles[2])));
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
     * Unit test for removing all edges from graph functionality
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
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            reloadGraphData();
            Set<String> removeStringSet = testGraphStringStringNull
                    .edgeSet(strings[i]);
            assertTrue(
                    testGraphStringStringNull.removeAllEdges(removeStringSet));
            assertFalse(testGraphStringStringNull.edgeSet()
                    .containsAll(removeStringSet));

            removeStringSet = testGraphStringString.edgeSet(strings[i]);
            assertTrue(testGraphStringString.removeAllEdges(removeStringSet));
            assertFalse(testGraphStringString.edgeSet()
                    .containsAll(removeStringSet));

            Set<Double> removeDoubleSet = testGraphStringDouble
                    .edgeSet(strings[i]);
            assertTrue(testGraphStringDouble.removeAllEdges(removeDoubleSet));
            assertFalse(testGraphStringDouble.edgeSet()
                    .containsAll(removeDoubleSet));

            Set<Integer> removeIntSet = testGraphIntegerInteger
                    .edgeSet(integers[i]);
            assertTrue(testGraphIntegerInteger.removeAllEdges(removeIntSet));
            assertFalse(testGraphIntegerInteger.edgeSet()
                    .containsAll(removeIntSet));
        }

        /*
         * Test NullPointerException is thrown on null edge collection
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
        clearGraphData();
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
        clearGraphData();
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

    /**
     * Unit test for removing all vertices from graph functionality
     */
    @Test
    public void removeAllVertices() {
        List<String> tempStrings = new ArrayList<String>(
                Arrays.asList(strings));
        tempStrings.add("four");
        List<Integer> tempIntegers = new ArrayList<Integer>(
                Arrays.asList(integers));
        tempIntegers.add(4);

        /*
         * Remove vertex collection from new empty graph
         */
        assertFalse(testGraphStringStringNull
                .removeAllVertices(Arrays.asList(strings)));
        assertFalse(testGraphStringStringNull.removeAllVertices(tempStrings));

        assertFalse(testGraphStringString
                .removeAllVertices(Arrays.asList(strings)));
        assertFalse(testGraphStringString.removeAllVertices(tempStrings));

        assertFalse(testGraphStringDouble
                .removeAllVertices(Arrays.asList(strings)));
        assertFalse(testGraphStringDouble.removeAllVertices(tempStrings));

        assertFalse(testGraphIntegerInteger
                .removeAllVertices(Arrays.asList(integers)));

        /*
         * Remove vertex collection from loaded graph
         */
        reloadGraphData();
        assertTrue(testGraphStringStringNull
                .removeAllVertices(Arrays.asList(strings)));
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());
        reloadGraphData();
        assertTrue(testGraphStringStringNull.removeAllVertices(tempStrings));
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());

        reloadGraphData();
        assertTrue(testGraphStringString
                .removeAllVertices(Arrays.asList(strings)));
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        reloadGraphData();
        assertTrue(testGraphStringString.removeAllVertices(tempStrings));
        assertTrue(testGraphStringString.edgeSet().isEmpty());

        reloadGraphData();
        assertTrue(testGraphStringDouble
                .removeAllVertices(Arrays.asList(strings)));
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());
        reloadGraphData();
        assertTrue(testGraphStringDouble.removeAllVertices(tempStrings));
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());

        reloadGraphData();
        assertTrue(testGraphIntegerInteger
                .removeAllVertices(Arrays.asList(integers)));
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());
        reloadGraphData();
        assertTrue(testGraphIntegerInteger.removeAllVertices(tempIntegers));
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());

        /*
         * Remove partial vertex collection from loaded graph
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            reloadGraphData();
            Set<String> removeStringSet = new HashSet<String>(
                    Arrays.asList(strings));
            removeStringSet.remove(strings[i]);

            assertTrue(testGraphStringStringNull
                    .removeAllVertices(removeStringSet));
            assertFalse(testGraphStringStringNull.vertexSet()
                    .containsAll(removeStringSet));
            assertTrue(testGraphStringStringNull.containsVertex(strings[i]));

            assertTrue(
                    testGraphStringString.removeAllVertices(removeStringSet));
            assertFalse(testGraphStringString.vertexSet()
                    .containsAll(removeStringSet));
            assertTrue(testGraphStringString.containsVertex(strings[i]));

            assertTrue(
                    testGraphStringDouble.removeAllVertices(removeStringSet));
            assertFalse(testGraphStringDouble.vertexSet()
                    .containsAll(removeStringSet));
            assertTrue(testGraphStringDouble.containsVertex(strings[i]));

            Set<Integer> removeIntegerSet = new HashSet<Integer>(
                    Arrays.asList(integers));
            removeIntegerSet.remove(integers[i]);

            assertTrue(testGraphIntegerInteger
                    .removeAllVertices(removeIntegerSet));
            assertFalse(testGraphIntegerInteger.vertexSet()
                    .containsAll(removeIntegerSet));
            assertTrue(testGraphIntegerInteger.containsVertex(integers[i]));
        }

        /*
         * Test NullPointerException is thrown on null vertex collection
         */
        reloadGraphData();
        try {
            testGraphStringStringNull.removeAllVertices(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex collection null", npe.getMessage());
        }
        try {
            testGraphStringString.removeAllVertices(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex collection null", npe.getMessage());
        }
        try {
            testGraphStringDouble.removeAllVertices(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex collection null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.removeAllVertices(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex collection null", npe.getMessage());
        }
    }

    /**
     * Unit test for removing specific edge from graph
     */
    @Test
    public void removeEdge() {
        ///// TESTING removeEdge(e) /////
        /*
         * Remove edge from new graph and assert removal failed
         */
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.removeEdge(null));
            assertFalse(testGraphStringString
                    .removeEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertFalse(testGraphStringDouble.removeEdge(doubles[i]));
            assertFalse(testGraphIntegerInteger.removeEdge(integers[i]));
        }

        /*
         * Remove edge from loaded graph and asserts edge removal successful
         */
        reloadGraphData();
        assertTrue(testGraphStringStringNull.removeEdge(null));
        assertFalse(testGraphStringStringNull.containsEdge(null));
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringString
                    .removeEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertFalse(testGraphStringString
                    .containsEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));

            assertTrue(testGraphStringDouble.removeEdge(doubles[i]));
            assertFalse(testGraphStringDouble.containsEdge(doubles[i]));

            assertTrue(testGraphIntegerInteger.removeEdge(integers[i]));
            assertFalse(testGraphIntegerInteger.containsEdge(integers[i]));
        }

        /*
         * Remove self-edge
         */
        reloadGraphData();
        testGraphStringString.addEdge(strings[0], strings[0],
                strings[0] + "-" + strings[0]);
        assertTrue(testGraphStringString
                .removeEdge(strings[0] + "-" + strings[0]));
        assertFalse(testGraphStringString
                .containsEdge(strings[0] + "-" + strings[0]));
        assertFalse(testGraphStringString.edgeSet()
                .contains(strings[0] + "-" + strings[0]));
        assertFalse(testGraphStringString.containsEdge(strings[0], strings[0]));

        /*
         * Remove edge not in graph
         */
        reloadGraphData();
        assertFalse(testGraphStringStringNull
                .removeEdge(strings[0] + "-" + strings[1]));
        assertFalse(testGraphStringString.removeEdge("three-four"));
        assertFalse(testGraphStringDouble.removeEdge(4d));
        assertFalse(testGraphIntegerInteger.removeEdge(4));

        /*
         * Remove edge already removed
         */
        reloadGraphData();
        testGraphStringStringNull.removeEdge(null);
        assertFalse(testGraphStringStringNull.removeEdge(null));

        testGraphStringString.removeEdge("one-two");
        assertFalse(testGraphStringString.removeEdge("one-two"));

        testGraphStringDouble.removeEdge(3d);
        assertFalse(testGraphStringDouble.removeEdge(3d));

        testGraphIntegerInteger.removeEdge(3);
        assertFalse(testGraphIntegerInteger.removeEdge(3));

        ///// TESTING removeEdge(v1, v2) /////
        /*
         * Remove edge from new unloaded graph, no vertices
         */
        clearGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertNull(testGraphStringStringNull.removeEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertNull(testGraphStringString.removeEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertNull(testGraphStringDouble.removeEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertNull(testGraphIntegerInteger.removeEdge(integers[i % 3],
                    integers[(i + 1) % 3]));
        }

        /*
         * Remove edge from loaded graph
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertEquals(null, testGraphStringStringNull
                    .removeEdge(strings[i % 3], strings[(i + 1) % 3]));
            assertEquals(strings[i % 3] + "-" + strings[(i + 1) % 3],
                    testGraphStringString.removeEdge(strings[i % 3],
                            strings[(i + 1) % 3]));
            assertEquals(doubles[i], testGraphStringDouble
                    .removeEdge(strings[i % 3], strings[(i + 1) % 3]));
            assertEquals(integers[i], testGraphIntegerInteger
                    .removeEdge(integers[i % 3], integers[(i + 1) % 3]));
        }
        assertTrue(testGraphStringStringNull.edgeSet().isEmpty());
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());

        /*
         * Remove self-edge
         */
        reloadGraphData();
        testGraphStringString.addEdge(strings[0], strings[0],
                strings[0] + "-" + strings[0]);
        assertEquals(strings[0] + "-" + strings[0],
                testGraphStringString.removeEdge(strings[0], strings[0]));

        /*
         * Remove edge not in graph
         */
        reloadGraphData();
        assertNull(
                testGraphStringStringNull.removeEdge(strings[0], "three-four"));
        assertNull(testGraphStringString.removeEdge(strings[0], "three-four"));
        assertNull(testGraphStringDouble.removeEdge(strings[0], "three-four"));
        assertNull(testGraphIntegerInteger.removeEdge(3, 4));

        /*
         * Remove edge already removed
         */
        reloadGraphData();
        testGraphStringStringNull.removeEdge(strings[0], strings[1]);
        assertNull(
                testGraphStringStringNull.removeEdge(strings[0], strings[1]));

        testGraphStringString.removeEdge(strings[0], strings[1]);
        assertNull(testGraphStringString.removeEdge(strings[0], strings[1]));

        testGraphStringDouble.removeEdge(strings[0], strings[1]);
        assertNull(testGraphStringDouble.removeEdge(strings[0], strings[1]));

        testGraphIntegerInteger.removeEdge(integers[0], integers[1]);
        assertNull(
                testGraphIntegerInteger.removeEdge(integers[0], integers[1]));

        /*
         * Remove a duplicately labeled edge
         */
        clearGraphData();
        loadVertices();
        testGraphStringString.addEdge(strings[0], strings[1], "duplicate-edge");
        testGraphStringString.addEdge(strings[1], strings[2], "duplicate-edge");
        assertEquals("duplicate-edge",
                testGraphStringString.removeEdge(strings[0], strings[1]));
        assertTrue(testGraphStringString.edgeSet().contains("duplicate-edge"));
        assertNull(testGraphStringString.getEdge(strings[0], strings[1]));
        assertEquals("duplicate-edge",
                testGraphStringString.getEdge(strings[1], strings[2]));

        /*
         * Test NullPointerException thrown if trying to remove edge when vertex
         * 1 or 2, or both, are null
         */
        reloadGraphData();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            try {
                testGraphStringStringNull.removeEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringStringNull.removeEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringString.removeEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringString.removeEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphStringDouble.removeEdge(strings[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphStringDouble.removeEdge(null, strings[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }

            try {
                testGraphIntegerInteger.removeEdge(integers[i], null);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
            try {
                testGraphIntegerInteger.removeEdge(null, integers[i]);
                fail("Expected NullPointerException!");
            } catch (NullPointerException npe) {
                assertEquals("Vertex value null", npe.getMessage());
            }
        }
        try {
            testGraphStringStringNull.removeEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringString.removeEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphStringDouble.removeEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
        try {
            testGraphIntegerInteger.removeEdge(null, null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals("Vertex value null", npe.getMessage());
        }
    }

    /**
     * Unit test for removing specific vertex from graph
     */
    @Test
    public void removeVertex() {
        /*
         * Remove vertex from new graph
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertFalse(testGraphStringStringNull.removeVertex(strings[i]));
            assertFalse(testGraphStringString.removeVertex(strings[i]));
            assertFalse(testGraphStringDouble.removeVertex(strings[i]));
            assertFalse(testGraphIntegerInteger.removeVertex(integers[i]));
        }

        /*
         * Remove vertex from loaded graph
         */
        loadVertices();
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.removeVertex(strings[i]));
            assertTrue(testGraphStringString.removeVertex(strings[i]));
            assertTrue(testGraphStringDouble.removeVertex(strings[i]));
            assertTrue(testGraphIntegerInteger.removeVertex(integers[i]));
        }
        
        /*
         * Remove vertex from graph, vertex !exist in graph
         */
        clearGraphData();
        loadVertices();
        assertFalse(testGraphStringStringNull.removeVertex("four"));
        assertFalse(testGraphStringString.removeVertex("four"));
        assertFalse(testGraphStringDouble.removeVertex("four"));
        assertFalse(testGraphIntegerInteger.removeVertex(4));

        /*
         * Remove vertex from graph, vertex is null
         */
        clearGraphData();
        loadVertices();
        assertFalse(testGraphStringStringNull.removeVertex(null));
        assertFalse(testGraphStringString.removeVertex(null));
        assertFalse(testGraphStringDouble.removeVertex(null));
        assertFalse(testGraphIntegerInteger.removeVertex(null));

        /*
         * Remove vertex from loaded graph, assert connected edges removed
         */
        clearGraphData();
        loadVertices();
        testGraphStringString.addEdge(strings[0], strings[1], "edge1");
        testGraphStringString.addEdge(strings[0], strings[2], "edge2");
        testGraphStringString.removeVertex(strings[0]);
        assertFalse(testGraphStringString.vertexSet().contains(strings[0]));
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        
        clearGraphData();
        loadVertices();
        testGraphStringString.addEdge(strings[0], strings[1], "edge1");
        testGraphStringString.addEdge(strings[0], strings[2], "edge2");
        testGraphStringString.addEdge(strings[1], strings[2], "edge3");
        testGraphStringString.removeVertex(strings[0]);
        assertEquals(1, testGraphStringString.edgeSet().size());
        assertFalse(testGraphStringString.edgeSet().contains("edge1"));
        assertFalse(testGraphStringString.edgeSet().contains("edge2"));
        assertTrue(testGraphStringString.edgeSet().contains("edge3"));
        
        /*
         * Remove vertex with self-edges
         */
        clearGraphData();
        loadVertices();
        testGraphStringString.addEdge(strings[0], strings[0], "self-edge");
        testGraphStringString.removeVertex(strings[0]);
        assertTrue(testGraphStringString.edgeSet().isEmpty());
        assertFalse(testGraphStringString.containsEdge("self-edge"));
        
        clearGraphData();
        loadVertices();
        testGraphStringString.addEdge(strings[0], strings[0], "self-edge");
        testGraphStringString.addEdge(strings[1], strings[1], "self-edge");
        testGraphStringString.removeVertex(strings[0]);
        assertTrue(testGraphStringString.containsEdge("self-edge"));
    }

    /**
     * Clears graph data
     */
    private void clearGraphData() {
        setUp();
    }

    /**
     * Clears and reloads graph data (vertices & edges)
     */
    private void reloadGraphData() {
        clearGraphData();
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
