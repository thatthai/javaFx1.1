import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;


import animatefx.animation.ZoomIn;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControllerClass implements Initializable{

    @FXML
    private Label TextD1;

    @FXML
    private Label TextD2;

    @FXML
    private Label TextD3;

    @FXML
    private Button btnDash;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMain;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnUsers;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblStatusMini;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnProfile;

    @FXML
    private Pane pnlDashboard;

    @FXML
    private Pane pnlStatus;
    @FXML
    private ChoiceBox<String> boxInfect;

    @FXML
    private ChoiceBox<String> boxVac;

    @FXML
    private ChoiceBox<String> boxdoes1;

    @FXML
    private ChoiceBox<String> boxdoes2;

    @FXML
    private ChoiceBox<String> boxdoes3;

    @FXML
    private Text textage;

    @FXML
    private Text textcovid;

    @FXML
    private Text textdisease;

    @FXML
    private Text textdoes1;

    @FXML
    private Text textdoes2;

    @FXML
    private Text textdoes3;

    @FXML
    private Text textheigh;

    @FXML
    private Text textlastname;

    @FXML
    private Text textname;

    @FXML
    private Text textsex;

    @FXML
    private Text textvacc;

    @FXML
    private Text textweight;

    @FXML
    private TableView<CovidData> table;

    @FXML
    private TableColumn<CovidData, String> tabledoes1;

    @FXML
    private TableColumn<CovidData, String> tabledoes2;

    @FXML
    private TableColumn<CovidData, String> tabledoes3;

    @FXML
    private TableColumn<CovidData, String> tableid;

    @FXML
    private TableColumn<CovidData, String> tableinfect;

    @FXML
    private TableColumn<CovidData, String> tablelastname;

    @FXML
    private TableColumn<CovidData, String> tablename;

    @FXML
    private TableColumn<CovidData, String> tablevacc;

    

    @FXML
    private TextField tflastname;

    @FXML
    private TextField tfname;

    @FXML
    private PieChart pieInfect;

    @FXML
    private PieChart pieVac;

    @FXML
    private Label totalNum;

    @FXML
    private Label infectNum;

    @FXML
    private Label nonInfectNum;

    private  int sf,pz,az,sv,mn,ot,non,y,n;
    private String[] Vaccin = {"SinoFarm","Pfizer","AstraZeneca","Sinovec","Moderna","Other","None"};

    @FXML
    void ClickClose(ActionEvent event) throws IOException  {
        
        if (event.getSource().equals(btnProfile)) {
            lblStatusMini.setText("/home/profile");
            lblStatus.setText("Profile");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(205, 92, 92),CornerRadii.EMPTY,Insets.EMPTY)));
            new ZoomIn(pnProfile).play();
            pnProfile.toFront();
        }

        if (event.getSource() == btnMain){
            lblStatusMini.setText("/home/");
            lblStatus.setText("Home");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(249, 117, 88),CornerRadii.EMPTY,Insets.EMPTY)));
            new ZoomIn(pnMain).play();
            pnMain.toFront();
        
        }
        if(event.getSource() == btnLogout ){
            System.exit(0);
        }


        if (event.getSource() == btnDash) {
            lblStatusMini.setText("/home/dashboard");
            lblStatus.setText("Dashboard");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(133, 193, 233 ),CornerRadii.EMPTY,Insets.EMPTY)));
            new ZoomIn(pnlDashboard).play();
            pnlDashboard.toFront();
        }

        if (event.getSource() == btnEdit){
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("EditController.fxml"));
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
            update();
        }
        if(event.getSource() == btnDelete){
             delete("record.txt", controllreLog.getUsername());
             update();
        }
       
    }


    @FXML
    void handleClose(MouseEvent event) throws IOException {
        
    }

    
    Collection<CovidData> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sf = pz = az = sv = mn = ot = non = y = n = 0;
        try {
            list = Files.readAllLines(new File("record.txt").toPath()).stream().map
            (line -> {
                String[] details = line.split(",");
                CovidData cd = new CovidData();
                cd.setId(details[2]);
                cd.setName(details[3]);
                cd.setLastName(details[4]);
                cd.setInfect(details[10]);
                cd.setVacineted(details[11]);
                cd.setDose1(details[12]);
                cd.setDose2(details[13]);
                cd.setDose3(details[14]);
                return cd;
            })
            .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<CovidData> details = FXCollections.observableArrayList(list);
        

        tableid.setCellValueFactory(data -> data.getValue().IdProperty());
        //System.out.println(Colid);
        tablename.setCellValueFactory(data -> data.getValue().nameProperty());
        tablelastname.setCellValueFactory(data -> data.getValue().LastNameProperty());
        tableinfect.setCellValueFactory(data -> data.getValue().InfectProperty());
        tablevacc.setCellValueFactory(data -> data.getValue().VacinetedProperty());
        tabledoes1.setCellValueFactory(data -> data.getValue().DoseProperty1());
        tabledoes2.setCellValueFactory(data -> data.getValue().DoseProperty2());
        tabledoes3.setCellValueFactory(data -> data.getValue().DoseProperty3());

        table.setItems(details);

        String fname = "";
        String lname= "";
        String agr= "";
        String sex= "";
        String height = "";
        String weight = "";
        String vacc = "";
        String does1 = "";
        String does2 = "";
        String does3 = "";
        String dis ="";
        String covid = "";
        try {
            File file = new File("record.txt");
            Scanner input = new Scanner(file);
            
            while (input.hasNext())
            {
    
                String[] line = (input.nextLine()).split(",");
                //System.out.pritln(line[0]);
                if (line[0].equals(controllreLog.getUsername())) {
                    fname = line[3];
                    lname = line[4];
                    agr = line[5];
                    sex = line[6];
                    height = line[7];
                    weight = line[8];
                    vacc = line[11];
                    does1 = line[12];
                    does2 = line[13];
                    does3 = line[14];
                    dis = line[15];
                    covid = line[10];
                }

                if(line[12].equals(Vaccin[0])){setSF();} else if(line[13].equals(Vaccin[0])){setSF();} else if(line[14].equals(Vaccin[0])){setSF();}
                if(line[12].equals(Vaccin[1])){setPZ();} else if(line[13].equals(Vaccin[1])){setPZ();} else if(line[14].equals(Vaccin[1])){setPZ();}
                if(line[12].equals(Vaccin[2])){setAZ();} else if(line[13].equals(Vaccin[2])){setAZ();} else if(line[14].equals(Vaccin[2])){setAZ();}
                if(line[12].equals(Vaccin[3])){setSV();} else if(line[13].equals(Vaccin[3])){setSV();} else if(line[14].equals(Vaccin[3])){setSV();}
                if(line[12].equals(Vaccin[4])){setMN();} else if(line[13].equals(Vaccin[4])){setMN();} else if(line[14].equals(Vaccin[4])){setMN();}
                if(line[12].equals(Vaccin[5])){setOT();} else if(line[13].equals(Vaccin[5])){setOT();} else if(line[14].equals(Vaccin[5])){setOT();}
                if(line[12].equals(Vaccin[6])){setNON();} else if(line[13].equals(Vaccin[6])){setNON();} else if(line[14].equals(Vaccin[6])){setNON();}

                if(line[10].equals("Yes")){setY();} if(line[10].equals("No")){setN();}
                
            }
            input.close();
        } catch (Exception e) {
            System.out.println("error");
        }
        //info
        textname.setText(fname);
        textlastname.setText(lname);
        textage.setText(agr);
        textsex.setText(sex);
        textheigh.setText(height);
        textweight.setText(weight);
        textvacc.setText(vacc);
        textdoes1.setText(does1);
        textdoes2.setText(does2);
        textdoes3.setText(does3);
        textdisease.setText(dis);
        textcovid.setText(covid);
        infectNum.setText(Integer.toString(y));
        nonInfectNum.setText(Integer.toString(n));
        totalNum.setText(Integer.toString(y+n));

        //piechart
        ObservableList<PieChart.Data> pieVacData = FXCollections.observableArrayList(
            new PieChart.Data("SinoFarm",this.getSF()),
            new PieChart.Data("Pfizer",this.getPZ()),
            new PieChart.Data("AstraZeneca",this.getAZ()),
            new PieChart.Data("Sinovec",this.getSV()),
            new PieChart.Data("Moderna",this.getMN()),
            new PieChart.Data("Other",this.getOT()),
            new PieChart.Data("None",this.getNON()));
        pieVac.setData(pieVacData);

        ObservableList<PieChart.Data> pieInfectData = FXCollections.observableArrayList(
            new PieChart.Data("เคยเป็นแล้ว",this.getY()),
            new PieChart.Data("ยังไม่เคยเป็น",this.getN()));
        pieInfect.setData(pieInfectData);
    }

    public static void delete(String filepath, String editAim){
        String tempfile = "temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempfile);
        
        String Uname ="" ;
        String Pname ="" ;
        String id ="";
        String fname = "";
        String lname= "";
        String agr= "";
        String sex= "";
        String height = "";
        String weight = "";
        String vacc = "";
        String does1 = "";
        String does2 = "";
        String does3 = "";
        String dis ="";
        String covid = "";
        try {
            FileWriter fw = new FileWriter(tempfile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            Scanner x = new Scanner(new File(filepath));

            while(x.hasNext()) {
                String[] line = (x.nextLine()).split(",");
                Uname = line[0];
                Pname = line[1];
                id = line[2];
                fname = line[3];
                lname = line[4];
                agr = line[5];
                sex = line[6];
                height = line[7];
                weight = line[8];
                covid = line[10];
                vacc = line[11];
                does1 = line[12];
                does2 = line[13];
                does3 = line[14];
                dis = line[15];
                if (!line[0].equalsIgnoreCase(editAim)) {
                    pw.println(Uname +","+Pname+","+id+","+fname+","+lname+","+agr+","+sex+","+height+","+weight+","+line[9]+","+covid+","+vacc+","+does1+","+does2+","+does3+","+dis);
                }
            }
            x.close();
            pw.flush();
            pw.close();
            bw.close();
            x.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        }catch(Exception e){
            System.out.println("ERROR");
        }
    }
    
    public void update(){
        sf = pz = az = sv = mn = ot = non = y = n = 0;
        try {
            list = Files.readAllLines(new File("record.txt").toPath()).stream().map
            (line -> {
                String[] details = line.split(",");
                CovidData cd = new CovidData();
                cd.setId(details[2]); //id
                cd.setName(details[3]); //name
                cd.setLastName(details[4]); //lastname
                cd.setInfect(details[10]); 
                cd.setVacineted(details[11]);
                cd.setDose1(details[12]);
                cd.setDose2(details[13]);
                cd.setDose3(details[14]);
                return cd;
            })
            .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<CovidData> details = FXCollections.observableArrayList(list);
        

        tableid.setCellValueFactory(data -> data.getValue().IdProperty());
        //System.out.println(Colid);
        tablename.setCellValueFactory(data -> data.getValue().nameProperty());
        tablelastname.setCellValueFactory(data -> data.getValue().LastNameProperty());
        tableinfect.setCellValueFactory(data -> data.getValue().InfectProperty());
        tablevacc.setCellValueFactory(data -> data.getValue().VacinetedProperty());
        tabledoes1.setCellValueFactory(data -> data.getValue().DoseProperty1());
        tabledoes2.setCellValueFactory(data -> data.getValue().DoseProperty2());
        tabledoes3.setCellValueFactory(data -> data.getValue().DoseProperty3());

        table.setItems(details);

        String fname = "";
        String lname= "";
        String agr= "";
        String sex= "";
        String height = "";
        String weight = "";
        String vacc = "";
        String does1 = "";
        String does2 = "";
        String does3 = "";
        String dis ="";
        String covid = "";
        try {
            File file = new File("record.txt");
            Scanner input = new Scanner(file);
            
            while (input.hasNext())
            {
    
                String[] line = (input.nextLine()).split(",");
                //System.out.pritln(line[0]);
                if (line[0].equals(controllreLog.getUsername())) {
                    fname = line[3];
                    lname = line[4];
                    agr = line[5];
                    sex = line[6];
                    height = line[7];
                    weight = line[8];
                    vacc = line[11];
                    does1 = line[12];
                    does2 = line[13];
                    does3 = line[14];
                    dis = line[15];
                    covid = line[10];
                }
                if(line[12].equals(Vaccin[0])){setSF();} else if(line[13].equals(Vaccin[0])){setSF();} else if(line[14].equals(Vaccin[0])){setSF();}
                if(line[12].equals(Vaccin[1])){setPZ();} else if(line[13].equals(Vaccin[1])){setPZ();} else if(line[14].equals(Vaccin[1])){setPZ();}
                if(line[12].equals(Vaccin[2])){setAZ();} else if(line[13].equals(Vaccin[2])){setAZ();} else if(line[14].equals(Vaccin[2])){setAZ();}
                if(line[12].equals(Vaccin[3])){setSV();} else if(line[13].equals(Vaccin[3])){setSV();} else if(line[14].equals(Vaccin[3])){setSV();}
                if(line[12].equals(Vaccin[4])){setMN();} else if(line[13].equals(Vaccin[4])){setMN();} else if(line[14].equals(Vaccin[4])){setMN();}
                if(line[12].equals(Vaccin[5])){setOT();} else if(line[13].equals(Vaccin[5])){setOT();} else if(line[14].equals(Vaccin[5])){setOT();}
                if(line[12].equals(Vaccin[6])){setNON();} else if(line[13].equals(Vaccin[6])){setNON();} else if(line[14].equals(Vaccin[6])){setNON();}

                if(line[10].equals("Yes")){setY();} if(line[10].equals("No")){setN();}
               
            }
            input.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
        textname.setText(fname);
        textlastname.setText(lname);
        textage.setText(agr);
        textsex.setText(sex);
        textheigh.setText(height);
        textweight.setText(weight);
        textvacc.setText(vacc);
        textdoes1.setText(does1);
        textdoes2.setText(does2);
        textdoes3.setText(does3);
        textdisease.setText(dis);
        textcovid.setText(covid);
        infectNum.setText(Integer.toString(y));
        nonInfectNum.setText(Integer.toString(n));
        totalNum.setText(Integer.toString(y+n));

        ObservableList<PieChart.Data> pieVacData = FXCollections.observableArrayList(
            new PieChart.Data("SinoFarm",this.getSF()),
            new PieChart.Data("Pfizer",this.getPZ()),
            new PieChart.Data("AstraZeneca",this.getAZ()),
            new PieChart.Data("Sinovec",this.getSV()),
            new PieChart.Data("Moderna",this.getMN()),
            new PieChart.Data("Other",this.getOT()),
            new PieChart.Data("None",this.getNON()));
        pieVac.setData(pieVacData);

        ObservableList<PieChart.Data> pieInfectData = FXCollections.observableArrayList(
            new PieChart.Data("เคยเป็นแล้ว",this.getY()),
            new PieChart.Data("ยังไม่เคยเป็น",this.getN()));
        pieInfect.setData(pieInfectData);

    }
    private class CovidData {
        StringProperty id = new SimpleStringProperty();
        StringProperty name = new SimpleStringProperty();
        StringProperty LastName= new SimpleStringProperty();
        StringProperty infect = new SimpleStringProperty();
        StringProperty vacineted = new SimpleStringProperty();
        StringProperty dose1 = new SimpleStringProperty();
        StringProperty dose2 = new SimpleStringProperty();
        StringProperty dose3 = new SimpleStringProperty();
        
        public final StringProperty IdProperty() {
            return this.id;
        }

        public final String getId() {
            return this.IdProperty().get();
        }

        public final void setId(final String details) {
            this.IdProperty().set(details);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final java.lang.String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final java.lang.String capital) {
            this.nameProperty().set(capital);
        }

        public final StringProperty LastNameProperty() {
            return this.LastName;
        }

        public final java.lang.String getLastName() {
            return this.LastNameProperty().get();
        }

        public final void setLastName(final java.lang.String LastName) {
            this.LastNameProperty().set(LastName);
        }

        public final StringProperty InfectProperty() {
            return this.infect;
        }

        public final java.lang.String getInfect() {
            return this.InfectProperty().get();
        }

        public final void setInfect(final java.lang.String infect) {
            this.InfectProperty().set(infect);
        }

        public final StringProperty VacinetedProperty() {
            return this.vacineted;
        }

        public final java.lang.String getVacineted() {
            return this.VacinetedProperty().get();
        }

        public final void setVacineted(final java.lang.String vacineted) {
            this.VacinetedProperty().set(vacineted);
        }

        public final StringProperty DoseProperty1() {
            return this.dose1;
        }

        public final java.lang.String getDose1() {
            return this.DoseProperty1().get();
        }

        public final void setDose1(final java.lang.String dose1) {
            this.DoseProperty1().set(dose1);
        }

        public final StringProperty DoseProperty2() {
            return this.dose2;
        }

        public final java.lang.String getDose2() {
            return this.DoseProperty2().get();
        }

        public final void setDose2(final java.lang.String dose2) {
            this.DoseProperty2().set(dose2);
        }

        public final StringProperty DoseProperty3() {
            return this.dose3;
        }

        public final java.lang.String getDose3() {
            return this.DoseProperty3().get();
        }

        public final void setDose3(final java.lang.String dose3) {
            this.DoseProperty3().set(dose3);
        }
    }
    public int getSF(){
        return sf;
    }
    public int getPZ(){
        return pz;
    }
    public int getAZ(){
        return az;
    }
    public int getSV(){
        return sv;
    }
    public int getMN(){
        return mn;
    }
    public int getOT(){
        return ot;
    }
    public int getNON(){
        return non;
    }
    
    public void setSF(){
        sf++;
    }
    public void setPZ(){
        pz++;
    }
    public void setAZ(){
        az++;
    }
    public void setSV(){
        sv++;
    }
    public void setMN(){
        mn++;
    }
    public void setOT(){
        ot++;
    }
    public void setNON(){
        non++;
    }
    public int getN(){
        return n;
    }
    public int getY(){
        return y;
    }
    public void setY(){
        y++;
    }
    public void setN(){
        n++;
    }


}
