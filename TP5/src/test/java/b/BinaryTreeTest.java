package b;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testConstructorWithValidFile() {
        // Test that the constructor works with a valid file
        assertDoesNotThrow(() -> {
            BinaryTree tree = new BinaryTree("data0_1");
        });
    }

    @Test
    void testConstructorWithInvalidFile() {
        // Test that the constructor throws FileNotFoundException with an invalid file
        assertThrows(FileNotFoundException.class, () -> {
            BinaryTree tree = new BinaryTree("nonexistent_file");
        });
    }

    @Test
    void testPreorder() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        // Create a BinaryTree with a known structure
        BinaryTree tree = new BinaryTree("data0_1");
        
        // Clear output from constructor
        outputStreamCaptor.reset();
        
        // Test preorder traversal
        tree.preorder();
        
        // Verify output contains expected elements
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Preorder:"));
        assertTrue(output.contains("--"));
        assertTrue(output.contains("++"));
        assertTrue(output.contains("5"));
    }

    @Test
    void testPostorder() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        // Create a BinaryTree with a known structure
        BinaryTree tree = new BinaryTree("data0_1");
        
        // Clear output from constructor
        outputStreamCaptor.reset();
        
        // Test postorder traversal
        tree.postorder();
        
        // Verify output contains expected elements
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Postorder:"));
        assertTrue(output.contains("--"));
        assertTrue(output.contains("++"));
        assertTrue(output.contains("5"));
    }

    @Test
    void testPrintHierarchy() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        // Create a BinaryTree with a known structure
        BinaryTree tree = new BinaryTree("data0_1");
        
        // Clear output from constructor
        outputStreamCaptor.reset();
        
        // Test hierarchy printing
        tree.printHierarchy();
        
        // Verify output contains expected elements
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Hierarchy:"));
        assertTrue(output.contains("--"));
        assertTrue(output.contains("++"));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("null"));
    }

    @Test
    void testComplexTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        // Test with a more complex tree structure
        BinaryTree tree = new BinaryTree("data1");
        
        // Clear output from constructor
        outputStreamCaptor.reset();
        
        // Test preorder traversal
        tree.preorder();
        
        // Verify output contains expected elements
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("*"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("++"));
        assertTrue(output.contains("vble1"));
        assertTrue(output.contains("^"));
        assertTrue(output.contains("7"));
        assertTrue(output.contains("vble2"));
        assertTrue(output.contains("vble3"));
        
        // Reset output
        outputStreamCaptor.reset();
        
        // Test postorder traversal
        tree.postorder();
        
        // Verify output contains expected elements
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("*"));
        assertTrue(output.contains("/"));
        assertTrue(output.contains("++"));
        assertTrue(output.contains("vble1"));
        assertTrue(output.contains("^"));
        assertTrue(output.contains("7"));
        assertTrue(output.contains("vble2"));
        assertTrue(output.contains("vble3"));
    }

    @Test
    void testMultipleTreeFiles() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        // Test with different tree files
        BinaryTree tree1 = new BinaryTree("data0_1");
        BinaryTree tree2 = new BinaryTree("data0_2");
        BinaryTree tree3 = new BinaryTree("data0_3");
        
        // Each tree should be successfully created
        assertNotNull(tree1);
        assertNotNull(tree2);
        assertNotNull(tree3);
    }
}