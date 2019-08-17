package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {
    public static Stage m;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FileListModel model = new FileListModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        loader.setController(new TraziController(model));
        Parent root = loader.load();
        primaryStage.setTitle("Pretraga datoteka");
        m=primaryStage;
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
