package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main extends Application {

    public static final double WINDOW_WIDTH = 1024;
    public static final double WINDOW_HEIGHT = 768;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        Simulation simulation = new Simulation();
        HBox view = simulation.getView();
        Scene scene = new Scene(view, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Symulacja parkingu");
        scene.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


        simulation.startSimulation();
    }


}
