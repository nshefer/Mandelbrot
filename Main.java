package mandelbrot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main-Class for the project Mandelbrot-set.
 */
public class Main extends Application {

    /**
     * Main function
     *
     * @param arg command line arguments
     */
    public static void main(String[] arg) {
        launch(arg);

    }

    /**
     * Start for JavaFX application.
     *
     * @param primaryStage primary Stage
     * @throws Exception exception
     */
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
