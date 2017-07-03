package mandelbrot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Baerbel
 */
public class Main extends Application {
    
    public static void main(String[] arg) {
		launch(arg);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		Parent root = FXMLLoader.load(getClass().getResource("Mandelbrot.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setTitle("Mandelbrotmenge");
		primaryStage.setScene(scene);
                primaryStage.setResizable(false);
		primaryStage.show();
	}

    
}
