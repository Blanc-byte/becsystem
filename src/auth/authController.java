/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auth;

import controllers.studentController;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import studentModel.userModel;

/**
 *
 * @author Administrator
 */
public class authController {
    final String DB_URL = "jdbc:mysql://localhost/becsystem";
    final String USER = "root";
    final String PASS = "";
    public String url;
    public Connection con = null;
    
    ObservableList<String> years = FXCollections.observableArrayList("1", "2", "3", "4");
    ObservableList<String> sections = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F");
    ObservableList<String> courses = FXCollections.observableArrayList("BSIT", "BSBA", "BSA", "BTLEd");
    
    String typeOfStudent = "", setUsername="ouch";
    
    public void initialize()throws Exception{
        connect();
        year.setItems(years);
        section.setItems(sections);
        course.setItems(courses);
        loadStudents();
    }
    
    
    ObservableList<userModel> users = FXCollections.observableArrayList();
    ObservableList<String> usernames = FXCollections.observableArrayList();
    ObservableList<String> passwords = FXCollections.observableArrayList();
    public void loadStudents()throws Exception{
        users.clear();
        java.sql.Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        while(resultSet.next()){
            int i1 = resultSet.getInt("id");
            String i2 = resultSet.getString("firstname");
            String i3 = resultSet.getString("middlename");
            String i4 = resultSet.getString("lastname");
            String i5 = resultSet.getString("contact");
            String i6 = resultSet.getString("school_id");
            String i7 = resultSet.getString("year");
            String i8 = resultSet.getString("section");
            String i9 = resultSet.getString("date_created");
            String i10 = resultSet.getString("status");
            String i11 = resultSet.getString("type");
            String i12 = resultSet.getString("username");
            String i13 = resultSet.getString("password");
            String i14 = resultSet.getString("role");
            String i15 = resultSet.getString("program");
            users.add(new userModel(i1,i2,i3,i4,i5,i6,i7,i8,i9,i10, i11, i12, i13,i14,i15));
            usernames.add(i12);
            passwords.add(i13);
        }
    }
    public boolean checkregistrarauth()throws Exception{
        String adminAd="ADMINistrator", passAd="ADMINistrator";
        if(adminAd.equals(user.getText()) && passAd.equals(pass.getText())){
            return true;
        }
        return false;
    }
    public void logIn(ActionEvent event)throws Exception{
        
        boolean logIned = true;
        if(checkregistrarauth()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registrar/stage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            logIned=false;
        }else{
            for(userModel usered: users){
                if(usered.getUsername().contains(user.getText()) && usered.getPassword().contains(pass.getText())){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/stage.fxml"));
                    Parent root = loader.load();

                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();

                    Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    currentStage.close();

                    studentController sC= new studentController();
                    sC.setUsername(usered.getId()+"");

                    writeToFile(usered.getId() + "", usered.getUsername());

                    logIned=false;
                }
            }
        }
        if(logIned){
            JOptionPane.showMessageDialog(null, "try again!!!");
        }
        
    }
    public void writeToFile(String userId, String username) throws Exception{
        // Define the path to the text file
        String filePath = "C:/Users/Administrator/Documents/NetBeansProjects/BECSystem/src/id.txt";  // Path to save the file (can be adjusted)

        // Prepare the data you want to write to the file
        String dataToWrite = userId;

        // Write to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
        writer.write(dataToWrite);
        System.out.println("Login data written to file successfully.");
        writer.close();
        
    }
    @FXML private TextField fname,mname,lname,contact,schoolID, user, pass;
    @FXML private ChoiceBox year,section, course;
    public boolean checkThe1stPhase()throws Exception{
        if(fname.getText().equals("") || mname.getText().equals("") || lname.getText().equals("") ||
           contact.getText().equals("") || schoolID.getText().equals("") || 
                year.getValue()==null || section.getValue()==null || course.getValue()==null){
            JOptionPane.showMessageDialog(null, "FILL IN ALL THE INFORMATION BEFORE PROCEEDING");
            return false;
        }
        return true;
    }
    @FXML private TextField userRegister,passRegister,passConfirmRegister;
    public boolean checkThe2ndPhase()throws Exception{
        if(userRegister.getText().equals("") || passRegister.getText().equals("") || passConfirmRegister.getText().equals("")){
            JOptionPane.showMessageDialog(null, "FILL IN ALL THE INFORMATION BEFORE PROCEEDING");
            return false;
        }
        return true;
    }
    public void createAccount(ActionEvent event)throws Exception{
        if(checkThe2ndPhase()){
            if(usernames.contains(userRegister.getText())){
                JOptionPane.showMessageDialog(null, "USERNAME already taken, try again!!!");
            }else if(!passRegister.getText().equals(passConfirmRegister.getText())){
                JOptionPane.showMessageDialog(null, "PASSWORD dont match, try again!!!");
            }else{
                loadRequest();
                setUsername=userRegister.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/stage.fxml"));
                Parent root = loader.load();

                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
                
                Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            }
        }
    }
    public void loadRequest()throws Exception{
        String insertSql = "INSERT INTO users(`firstname`, `middlename`, `lastname`, `contact`, `school_id`, `year`, `section`, `type`, `username`, `password`,`program`) "
                            + "VALUES ('" + fname.getText() + "', "
                            + "'" + mname.getText() + "', "
                            + "'" + lname.getText() + "', "
                            + "'" + contact.getText() + "', "
                            + "'" + schoolID.getText() + "', "
                            + "'" + year.getValue() + "', "
                            + "'" + section.getValue() + "', "
                            + "'" + typeOfStudent + "', "
                            + "'" + userRegister.getText() + "', "
                            + "'" + passRegister.getText() + "', "
                            + "'" + course.getValue() + "');";
        java.sql.Statement statement = con.createStatement();
        statement.executeUpdate(insertSql);
    }
    @FXML private Pane logIn, signUp, typeOf, signUp2ndPhase;
    public void logInShow(){
        logIn.setVisible(true);
        signUp.setVisible(false);

    }
    public void signUpShowInternal()throws Exception{
        signUp.setVisible(true);
        typeOf.setVisible(false);
        typeOfStudent = "INTERNAL";
    }
    public void signUpShowExternal()throws Exception{
        signUp.setVisible(true);
        typeOf.setVisible(false);
//        course.setDisable(true);
//        section.setDisable(true);
//        year.setDisable(true);
//        schoolID.setDisable(true);
        typeOfStudent = "EXTERNAL";
    }
    public void signUp2ndPhaseShow()throws Exception{
        if(checkThe1stPhase()){
            signUp.setVisible(false);
            signUp2ndPhase.setVisible(true);
        }
    }
    public void typeOfShow(){
        logIn.setVisible(false);
        typeOf.setVisible(true);
        
    }
    public Connection connect() {
        url = "jdbc:mysql://localhost:3306/becsystem";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
        return con;
    
    }
}
