package a;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ExpTreeTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testSimpleExpression() {
        // Simulate user input
        String input = "( a + b )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Clear output from constructor
        outputStreamCaptor.reset();

        // Test inorder traversal
        expTree.inorder();

        // Verify output
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("("));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains(")"));

        // Test preorder traversal
        outputStreamCaptor.reset();
        expTree.preorder();

        // Verify preorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("+"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));

        // Test postorder traversal
        outputStreamCaptor.reset();
        expTree.postorder();

        // Verify postorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("+"));
    }

    @Test
    void testComplexExpression() {
        // Simulate user input for a more complex expression
        String input = "( ( a + b ) * ( c - d ) )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Clear output from constructor
        outputStreamCaptor.reset();

        // Test inorder traversal
        expTree.inorder();

        // Verify output
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("-"));
        assertTrue(output.contains("d"));

        // Test preorder traversal
        outputStreamCaptor.reset();
        expTree.preorder();

        // Verify preorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("*"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("-"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("d"));

        // Test postorder traversal
        outputStreamCaptor.reset();
        expTree.postorder();

        // Verify postorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("-"));
        assertTrue(output.contains("*"));
    }

    @Test
    void testNestedExpression() {
        // Simulate user input for a nested expression
        String input = "( ( ( a + b ) * c ) - ( d / e ) )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Clear output from constructor
        outputStreamCaptor.reset();

        // Test inorder traversal
        expTree.inorder();

        // Verify output
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("-"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("e"));

        // Test preorder traversal
        outputStreamCaptor.reset();
        expTree.preorder();

        // Verify preorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("-"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("e"));

        // Test postorder traversal
        outputStreamCaptor.reset();
        expTree.postorder();

        // Verify postorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("e"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("-"));
    }

    @Test
    void testExpressionWithAllOperators() {
        // Test all operators: +, -, *, /, ^
        String input = "( ( a + b ) - ( ( c * d ) / ( e ^ f ) ) )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Clear output from constructor
        outputStreamCaptor.reset();

        // Test inorder traversal
        expTree.inorder();

        // Verify output
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("+"));
        assertTrue(output.contains("-"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("^"));

        // Test preorder traversal
        outputStreamCaptor.reset();
        expTree.preorder();

        // Verify preorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("-"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("^"));
        assertTrue(output.contains("e"));
        assertTrue(output.contains("f"));

        // Test postorder traversal
        outputStreamCaptor.reset();
        expTree.postorder();

        // Verify postorder output
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("+"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("*"));
        assertTrue(output.contains("e"));
        assertTrue(output.contains("f"));
        assertTrue(output.contains("^"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("-"));
    }

    @Test
    void testInvalidExpression() {
        // Test with invalid expression (missing closing parenthesis)
        String input = "( a + b";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Expect RuntimeException
        assertThrows(RuntimeException.class, () -> new ExpTree());
    }

    @Test
    void testInvalidOperator() {
        // Test with invalid operator
        String input = "( a $ b )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Expect RuntimeException
        assertThrows(RuntimeException.class, () -> new ExpTree());
    }

    @Test
    void testEmptyExpression() {
        // Test with empty expression
        String input = "";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Expect RuntimeException
        assertThrows(RuntimeException.class, () -> new ExpTree());
    }

    @Test
    void testEvalSimpleExpression() {
        // Test simple addition with numeric values
        String input = "( 5 + 3 )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Test eval method
        double result = expTree.eval();
        assertEquals(8.0, result, 0.001);
    }

    @Test
    void testEvalComplexExpression() {
        // Test complex expression with numeric values
        String input = "( ( 5 + 3 ) * ( 10 - 2 ) )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Test eval method
        double result = expTree.eval();
        assertEquals(64.0, result, 0.001);
    }

    @Test
    void testEvalNestedExpression() {
        // Test nested expression with numeric values
        String input = "( ( ( 2 + 3 ) * 4 ) - ( 10 / 2 ) )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Test eval method
        double result = expTree.eval();
        assertEquals(15.0, result, 0.001);
    }

    @Test
    void testEvalAllOperators() {
        // Test all operators with numeric values
        String input = "( ( 2 + 3 ) - ( ( 4 * 5 ) / ( 2 ^ 3 ) ) )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Test eval method
        double result = expTree.eval();
        assertEquals(2.5, result, 0.001);
    }

    @Test
    void testEvalWithVariables() {
        // Test with variables (should throw exception since variables are not implemented)
        String input = "( x + 5 )";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create ExpTree instance
        ExpTree expTree = new ExpTree();

        // Expect RuntimeException when evaluating
        assertThrows(RuntimeException.class, () -> expTree.eval());
    }
}
