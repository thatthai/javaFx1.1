import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMain.fxml"));
        Parent root = fxmlLoader.load();
        Scene lay = new Scene(root);

        primaryStage.initStyle(StageStyle.UNDECORATED); //tab หาย
        primaryStage.setScene(lay);
        primaryStage.show();
    }
}