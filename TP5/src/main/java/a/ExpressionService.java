package a;

public interface ExpressionService {
    //lanza excepción si no se puede evaluar porque hay algo mal formado en la expresión
    double eval();
    void preorder();
    void inorder();
    void postorder();
}
