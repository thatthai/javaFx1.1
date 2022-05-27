import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InformationController implements Initializable {

    @FXML
    private TextField AgeField;

    @FXML
    private ChoiceBox<String> Choicebox;

    @FXML
    private ChoiceBox<String> Choicefirst;

    @FXML
    private ChoiceBox<String> Choicesecond;

    @FXML
    private ChoiceBox<String> Choicethird;

    @FXML
    private PasswordField ConPassId;

    @FXML
    private DatePicker Date;

    @FXML
    private TextField DiseaseField;

    @FXML
    private TextField HeightField;

    @FXML
    private Button InforDelete;

    @FXML
    private Button Inforadd;

    @FXML
    private TextField LastField;

    @FXML
    private TextField NameField;

    @FXML
    private RadioButton No1ch;

    @FXML
    private RadioButton No2ch;

    @FXML
    private PasswordField PassId;

    @FXML
    private Label TextD1;

    @FXML
    private Label TextD2;

    @FXML
    private Label TextD3;

    @FXML
    private TextField WeightField;

    @FXML
    private ImageView imback;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField usename;

    @FXML
    private RadioButton yes1ch;

    @FXML
    private RadioButton yes2ch;

    public  String sex;
    public  String Dose1 = "None";
    public  String Dose2 = "None";
    public  String Dose3 = "None";
    public  String Dose4 = "None";

    private static Scanner x;

    private String[] Sex = {"Male","female"};
    private String[] Vaccin = {"SinoFarm","Pfizer","AstraZeneca","Sinovec","Moderna","Other","None"};


    @FXML
    void ClickInfor(MouseEvent event) {

        HelperStage.close((Node) event.getSource());
    }


    @Override
    public void initialize(URL argo, ResourceBundle arg1) {
        Choicebox.getItems().addAll(Sex);
        Choicebox.setOnAction(this :: getSex);
        Choicefirst.getItems().addAll(Vaccin);
        Choicefirst.setOnAction(this::getDose1);
        Choicesecond.getItems().addAll(Vaccin);
        Choicesecond.setOnAction(this::getDose2);
        Choicethird.getItems().addAll(Vaccin);
        Choicethird.setOnAction(this::getDose3);
        
    }

    
    public void getSex(ActionEvent event){
        sex = Choicebox.getValue();
        System.out.println(sex);
    }

    public void getDose1(ActionEvent event){
        Dose1 = Choicefirst.getValue();
        System.out.println(Dose1);
    }

    public void getDose2(ActionEvent event){
        Dose2 = Choicesecond.getValue();
        System.out.println(Dose2);
    }

    public void getDose3(ActionEvent event){
        Dose3 = Choicethird.getValue();
        System.out.println(Dose3);
    }
    @FXML
    void AddInfo(ActionEvent event) throws IOException {

            if(event.getSource() == Inforadd)
            {
                File fileread = new File("record.txt");
                String uname = usename.getText();
                String pass = PassId.getText();
                Integer Id = 0 ;
                String Name = NameField.getText();
                String lastName = LastField.getText();
                String Age = AgeField.getText();
                String Gender = Choicebox.getValue();
                String Weight = WeightField.getText();
                String Height = HeightField.getText();
                String Disease = DiseaseField.getText();
                String Covid = getTextch(yes2ch);
                String Vacineted =getTextch(yes1ch);
                String Dose1 = this.Dose1;
                String Dose2 = this.Dose2;
                String Dose3 = this.Dose3;

                boolean Usedname = false;  // ชื่อซ้ำ
                Scanner input = new Scanner(fileread);
                
                while(input.hasNextLine()){
                    String[] line = (input.nextLine()).split(",");

          
                    if(line[0].equals(uname))
                    {
                        Usedname = true;
                    }
                     Id =  Integer.parseInt(line[2])+1; ;
                    
                }
                input.close();

                    FileWriter output = new FileWriter(fileread,true);
                    output.append(uname+","+pass+","+Id +","+Name + "," + lastName +","+Age+","+Gender+","+Weight+","+Height+","+Disease+","+Covid+","+Vacineted+","+Dose1+","+Dose2+","+Dose3+","+Dose4+ "\n");
                    output.close();
                    
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.initStyle(StageStyle.UNDECORATED);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    ((Node) event.getSource()).getScene().getWindow().hide();
            }
    
    }


    String getTextch(RadioButton box) {
        if(box.isSelected()){
            return "Yes";
        }
        return "No";
    }
    @FXML
    void getAddicCovid(ActionEvent event) {
        if(yes2ch.isSelected()){
            No2ch.setVisible(false);
        }
        if(!yes2ch.isSelected()){
            No2ch.setVisible(true);
        }
        if(No2ch.isSelected()){
            yes2ch.setVisible(false);
        }
        if(!No2ch.isSelected()){
            yes2ch.setVisible(true);
        }
        //
        if(yes1ch.isSelected()){
            TextD1.setVisible(true);
            TextD2.setVisible(true);
            TextD3.setVisible(true);
            
            Choicefirst.setVisible(true);
            Choicesecond.setVisible(true);
            Choicethird.setVisible(true);
            No1ch.setVisible(false);
        }
        if(!yes1ch.isSelected()){
            TextD1.setVisible(false);
            TextD2.setVisible(false);
            TextD3.setVisible(false);
            
            Choicefirst.setVisible(false);
            Choicesecond.setVisible(false);
            Choicethird.setVisible(false);
            No1ch.setVisible(true);
        }
        if(No1ch.isSelected()){
            TextD1.setVisible(false);
            TextD2.setVisible(false);
            TextD3.setVisible(false);
            
            Choicefirst.setVisible(false);
            Choicesecond.setVisible(false);
            Choicethird.setVisible(false);
            yes1ch.setVisible(false);
        }
        if(!No1ch.isSelected()){
            TextD1.setVisible(true);
            TextD2.setVisible(true);
            TextD3.setVisible(true);
            
            Choicefirst.setVisible(true);
            Choicesecond.setVisible(true);
            Choicethird.setVisible(true);
            yes1ch.setVisible(true);
        }

    }

}
