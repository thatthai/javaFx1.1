import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Loadresearch {

    private Loadresearch(){

    }

    public static Parent loadFXML(String research) throws IOException{

        Parent root = null;
        try{
            new FXMLLoader();
            root =FXMLLoader.load(new Loadresearch().getClass().getResource(research+".fxml"));
            
        }catch(IOException ex){

        }
        return root;
    }

}