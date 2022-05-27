import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelperStage {
    private HelperStage(){

    }

    public static void close(Node node){
        ImageView Image = (ImageView) node;
        Stage window = (Stage) Image.getScene().getWindow();
        window.close();
    }
}