package c;

import c.core.BST;

// bajar el paquete nativo  
// https://gluonhq.com/products/javafx/ 

// en el VM poner el lib del paquete nativo
// --module-path C:\Users\lgomez\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.fxml,javafx.controls


import c.controller.GraphicsTree;
import c.core.Person;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import java.util.Iterator;

public class TestGUI extends Application {

	public static void main(String[] args) {
		// GUI
		launch(args);
	}

    @Override
	public void start(Stage stage) {
		stage.setTitle("Drawing the BST");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 1300, 700);

		BST<Integer> myTree = createModel();
		GraphicsTree<Integer> c = new GraphicsTree<>(myTree);
		c.widthProperty().bind(scene.widthProperty());
		c.heightProperty().bind(scene.heightProperty());
		root.getChildren().add(c);

		/*BST<Person> myTree2 = createModel2();
		GraphicsTree<Person> c2 = new GraphicsTree<>(myTree2);
		c2.widthProperty().bind(scene.widthProperty());
		c2.heightProperty().bind(scene.heightProperty());
		root.getChildren().add(c2);*/

		stage.setScene(scene);
		stage.show();
	}

    
	private BST<Integer> createModel() {
		BST<Integer> myTree = new BST<>();
		myTree = new BST<>();
		myTree.insert(50);
		myTree.insert(60);
		myTree.insert(80);
		myTree.insert(20);
		myTree.insert(70);
		myTree.insert(40);
		myTree.insert(44);
		myTree.insert(10);
		myTree.insert(40);

		for (Integer element : myTree) {
			System.out.print(element + " ");
		}
		System.out.println();

		myTree.setTraversal(BST.Traversal.INORDER);
		for (Integer item : myTree) {
			System.out.print(item + " ");
		}
		System.out.println();

		myTree.setTraversal(BST.Traversal.PREORDER);
		for (Integer integer : myTree) {
			System.out.print(integer + " ");
		}
		System.out.println();

		myTree.setTraversal(BST.Traversal.POSTORDER);
		for (Integer value : myTree) {
			System.out.print(value + " ");
		}
		System.out.println();

		return myTree;
	}

	private BST<Person> createModel2() {
		BST<Person> myTree = new BST<>();
		myTree = new BST<>();
		myTree.insert(new Person( 50, "Ana" ));
		myTree.insert(new Person( 60, "Juan") );
		myTree.insert(new Person( 80, "Sergio") );
		myTree.insert(new Person( 20, "Lila ") );
		myTree.insert(new Person( 77, "Ana") );
		myTree.inOrder();

		return myTree;
	}
	

}