package ej13;

public class TestStack {

    private static int counter = 0;

    public static void causeStackOverflow() {
        counter++;
        System.out.println("Recursion depth: " + counter);
        causeStackOverflow(); // Recursive call
    }

    public static void main(String[] args) {
        try {
            causeStackOverflow();
        } catch (StackOverflowError e) {
            System.out.println("Stack Overflow reached at recursion depth: " + counter);
        }
    }
}
