package graphADT;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicUndirectedGraphTest {
    
    BasicUndirectedGraph<String, String> testGraphStringString;
    BasicUndirectedGraph<String, Double> testGraphStringDouble;
    BasicUndirectedGraph<Integer, Integer> testGraphIntegerInteger;
    
    @Test
    public void testConstructor() {
        new BasicUndirectedGraph<String, String>();
        new BasicUndirectedGraph<String, Double>();
        new BasicUndirectedGraph<Integer, Integer>();
    }

}
