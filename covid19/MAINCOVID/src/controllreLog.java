import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import animatefx.animation.*;

public class controllreLog {

    @FXML
    private Button ButtonBack;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Button ButtonNext;

    @FXML
    private Button ButtonSingUp;

    @FXML
    private PasswordField EnterConfirmPassId;

    @FXML
    private TextField EnterNameId;

    @FXML
    private PasswordField EnterPassId;

    @FXML
    private ImageView ImageBack;

    @FXML
    private Label LabelRegister;

    @FXML
    private ImageView Logo;

    @FXML
    private ImageView MLElement1;

    @FXML
    private PasswordField PassId;

    @FXML
    private TextField UsernameId;

    @FXML
    private ImageView buttonExit;

    @FXML
    private ImageView buttonExit1;

    @FXML
    private Pane pnlLogin;

    @FXML
    private Pane pnlRegister;

    @FXML
    private Label resultShow;

    public static String uname;
    public String pword;
    
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if(event.getSource().equals(ButtonSingUp))
        {   
           
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("InformationController.fxml"));
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        if (event.getSource().equals(ButtonBack)) {
            new SlideInUp(pnlLogin).play();
            pnlLogin.toFront();
            
        }
        if(event.getSource() == ButtonLogin)
        {
            String username = UsernameId.getText();
            String password = PassId.getText();
            boolean isSuccess = false;
            File file = new File("record.txt");
            Scanner input = new Scanner(file);
            if(!file.exists())
                {
                    file.createNewFile();
                }
            while (input.hasNext())
            {
                String[] line = (input.nextLine()).split(",");
                //System.out.println(line[0]);
                if(username.equalsIgnoreCase(line[0])&&password.equalsIgnoreCase(line[1]))
                {
                    new Tada(resultShow).play();
                    resultShow.setText("Success");
                    isSuccess = true;
                }
            }
            input.close();
            if (isSuccess) {
                uname = UsernameId.getText();
                pword = PassId.getText();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
                Scene scene = new Scene(root);
                primaryStage.initStyle(StageStyle.UNDECORATED);
                primaryStage.setScene(scene);
                primaryStage.show();
                ((Node) event.getSource()).getScene().getWindow().hide(); //ปิดต่างหน้า
            }
            if(!isSuccess)
            {
                new Shake(resultShow).play();
                resultShow.setText("Your username or password is wrong.");
            }
        }
        //register
        if(event.getSource() == ButtonNext)
        {
            File file = new File("record.txt");
            if(!file.exists())
                {
                    file.createNewFile();
                }
            String username = EnterNameId.getText();
            String password = EnterPassId.getText();
            String Cpassword = EnterConfirmPassId.getText();
            //System.out.println(password); 
            //System.out.println(Cpassword);
            Scanner in = new Scanner(file);
            boolean Usedname = false;  // ชื่อซ้ำ
            while (in.hasNext())
            {
                String[] line = (in.nextLine()).split(",");
                //System.out.println(line[0]);
                if(line[0].equals(username))
                {
                    Usedname = true;
                }
            }
            //System.out.println(Usedname);
            in.close();
            if(Usedname)
            {
                new Shake(LabelRegister).play();
                LabelRegister.setText("This name is already in use.");
            }
            else if(username == "" || password == "" || CheckSpace(username,BlackList))
            {
                new Shake(LabelRegister).play();
                LabelRegister.setText("Username and password must not be empty.");
            }
            else if(password.equals(Cpassword) && !CheckSpace(password,BlackList))
            {
                if(!file.exists())
                {
                    file.createNewFile();
                }
                //FileWriter output = new FileWriter(file,true);
                //output.append(username+","+password+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" \n");
                //output.close();

                new Tada(LabelRegister).play();
                LabelRegister.setText("Success");


                new SlideInUp(pnlLogin).play();
                pnlLogin.toFront();
            }
            else
            {
                new Shake(LabelRegister).play();
                LabelRegister.setText("check your password again");
            }

            
            
        }

    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if(event.getSource() == buttonExit || event.getSource() == buttonExit1){
            System.exit(0);
        }
        if(event.getSource().equals(ButtonBack)){
            resultShow.setText("");
            UsernameId.clear();
            PassId.clear();
            new SlideInDown(pnlLogin).play();
            pnlLogin.toFront();
        }
    }
    String BlackList[] = {" "};
    public boolean CheckSpace(String username,String[] BlackList){
        
        for(String string : BlackList){
            if(string.equals(username)){
                return true;
            }
        }
        return false;
    }

    public static String getUsername(){
        return uname;
    }

    

}