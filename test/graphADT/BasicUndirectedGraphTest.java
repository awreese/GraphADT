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
    public void testConstructor() {
        assertTrue(testGraphStringString.vertexSet().isEmpty());
        assertTrue(testGraphStringString.edgeSet().isEmpty());

        assertTrue(testGraphStringDouble.vertexSet().isEmpty());
        assertTrue(testGraphStringDouble.edgeSet().isEmpty());

        assertTrue(testGraphIntegerInteger.vertexSet().isEmpty());
        assertTrue(testGraphIntegerInteger.edgeSet().isEmpty());
    }

    /**
     * Unit test to adding vertices to graphs. Also tests
     * {@link BasicUndirectedGraph#containsVertex containsVertex} method
     */
    @Test
    public void addVertex() {
        // String into String type vertex
        for (String s : strings) {
            assertTrue("Failed to insert " + s,
                    testGraphStringString.addVertex(s));
            assertTrue(testGraphStringString.containsVertex(s));
        }

        // try to add duplicate vertex
        assertFalse("Duplicate vertex added",
                testGraphStringString.addVertex(strings[0]));

        // try to add null vertex
//         thrown.expect(NullPointerException.class);
//         thrown.expectMessage("Vertex value null");
//         testGraphStringString.addVertex(null);

        try {
            testGraphStringString.addVertex(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }

        // try graph of more complex objects
        BasicUndirectedGraph<ComplexObject,String> complexObjectGraph;
        complexObjectGraph = new BasicUndirectedGraph<ComplexObject,String>();
        ComplexObject co = new ComplexObject(strings, doubles, integers);
        assertTrue(complexObjectGraph.addVertex(co));
        assertTrue(complexObjectGraph.containsVertex(co));
        
        try {
            complexObjectGraph.addVertex(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException npe) {
            assertEquals(npe.getMessage(), "Vertex value null");
        }

    }

    /**
     * Loads data into graphs for use in tests
     */
    private void loadGraphData() {
        
        for (String s : strings) {
            testGraphStringStringNull.addVertex(s);
            testGraphStringString.addVertex(s);
            testGraphStringDouble.addVertex(s);
        }
        
        // String/String/null edge value
        testGraphStringStringNull.addEdge(strings[0], strings[1]);
        testGraphStringStringNull.addEdge(strings[1], strings[2]);
        testGraphStringStringNull.addEdge(strings[0], strings[2]);
        
        // String/String
        testGraphStringString.addEdge(strings[0], strings[1], "one-two");
        testGraphStringString.addEdge(strings[1], strings[2], "two-three");
        testGraphStringString.addEdge(strings[0], strings[2], "one-three");
        
        // String/Double
        testGraphStringDouble.addEdge(strings[0], strings[1], doubles[0]);
        testGraphStringDouble.addEdge(strings[1], strings[2], doubles[1]);
        testGraphStringDouble.addEdge(strings[0], strings[2], doubles[2]);
        
        for (Integer i : integers) {
            testGraphIntegerInteger.addVertex(i);
        }
        
        // Integer/Integer
        testGraphIntegerInteger.addEdge(integers[0], integers[1], 100);
        testGraphIntegerInteger.addEdge(integers[1], integers[2], 200);
        testGraphIntegerInteger.addEdge(integers[0], integers[2], 300);
        
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
