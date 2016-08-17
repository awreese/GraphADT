package graphADT;

import static org.junit.Assert.*;

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
        testGraphStringString = new BasicUndirectedGraph<String,String>();
        testGraphStringStringNull = new BasicUndirectedGraph<String,String>();
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
            assertTrue("Failed to insert " + s,
                    testGraphStringString.addVertex(s));
        }

        // try graph of more complex objects
        BasicUndirectedGraph<ComplexObject,String> complexObjectGraph;
        complexObjectGraph = new BasicUndirectedGraph<ComplexObject,String>();
        ComplexObject co = new ComplexObject(strings, doubles, integers);
        assertTrue(complexObjectGraph.addVertex(co));

        /*
         * Add duplicate vertices
         */
        assertFalse("Duplicate vertex added",
                testGraphStringString.addVertex(strings[0]));
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
         * Add duplicate edge adds a duplicate edge already present in graph and
         * asserts addition was unsuccessful
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
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringString
                    .containsEdge(strings[i % 3] + "-" + strings[(i + 1) % 3]));
            assertTrue(testGraphStringDouble.containsEdge(doubles[i]));
            assertTrue(testGraphIntegerInteger.containsEdge(integers[i]));
        }

        /*
         * Test negative edge value containment
         */
        assertFalse(testGraphStringString.containsEdge("four"));
        assertFalse(testGraphStringDouble.containsEdge(4d));
        assertFalse(testGraphIntegerInteger.containsEdge(4));

        /*
         * Test positive edge (v1//v2, v2//v1) containment
         */
        for (int i = 0; i <= 2; i++) { // i = {0, 1, 2}
            assertTrue(testGraphStringStringNull.containsEdge(strings[i % 3],
                    strings[(i + 1) % 3]));
            assertTrue(testGraphStringStringNull.containsEdge(strings[(i + 1) % 3],
                    strings[i % 3]));
            
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
            assertTrue(testGraphIntegerInteger.containsEdge(integers[(i + 1) % 3],
                    integers[i % 3]));
        }
        
        /*
         * Test negative edge (v1, v2) containment
         * v1 and v2 in the graph
         * 
         * Since test graphs all only contain 3 vertices, we need to add a few
         * more to each to ensure unconnected vertices exist in graph in order
         * to test properly.
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
        
        /*
         * Test negative edge (v1, v2) containment
         * here either v1, v2, or both aren't in the graph
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
        
        /*
         * Test NullPointerException thrown when vertex v1, v2, or both are null
         */
        try {
            assertFalse(testGraphStringString.containsEdge(strings[2], null));
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            assertFalse(testGraphStringString.containsEdge(null, strings[2]));
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
        try {
            assertFalse(testGraphStringString.containsEdge(null, null));
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }
    }

    /**
     * Clears graph data
     */
    private void clearGraph() {
        setUp();
    }

    /**
     * Clears and reloads graph data
     */
    private void reloadGraphData() {
        clearGraph();
        loadGraphData();
    }

    /**
     * Loads data into graphs for use in tests
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
