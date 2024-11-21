package controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import auth.authController;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import studentModel.requestsModel;

/**
 *
 * @author Administrator
 */
public class studentController {
    final String DB_URL = "jdbc:mysql://localhost/becsystem";
    final String USER = "root";
    final String PASS = "";
    public String url;
    public Connection con = null;
    public void initialize()throws Exception{
        connect();
        loadRequestsToTable();
    }
    ObservableList<requestsModel> requestsPending = FXCollections.observableArrayList();
    ObservableList<requestsModel> requestsApprove = FXCollections.observableArrayList();
    ObservableList<requestsModel> requestsDenied = FXCollections.observableArrayList();
    public void getRequest()throws Exception{
        requestsPending.clear();requestsApprove.clear();requestsDenied.clear();
        java.sql.Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM requests");
        while(resultSet.next()){
            String i = resultSet.getString("id");
            String ii = resultSet.getString("student_id");
            String iii = resultSet.getString("date_request");
            String iiii = resultSet.getString("status");
            String iiiii = resultSet.getString("reason");
            String iiiiii = resultSet.getString("date_approve");
            String iiiiiii = resultSet.getString("file");
            if(iiii.equals("pending")){
                requestsPending.add(new requestsModel(i,ii,iii,iiii,iiiii,iiiiii,iiiiiii));
            }else if(iiii.equals("denied")){
                requestsDenied.add(new requestsModel(i,ii,iii,iiii,iiiii,iiiiii,iiiiiii));
            }else{
                requestsApprove.add(new requestsModel(i,ii,iii,iiii,iiiii,iiiiii,iiiiiii));
            }
            
        }
    }
    @FXML private TableView<requestsModel> pendingTable, approveTable, deniedTable;
    @FXML private TableColumn<requestsModel, String> requestColumn,dateColumn, requestApprove, dateApprove, requestDenied, dateDenied;
    @FXML private TableColumn<requestsModel, Void> actionColumn;
    public void loadRequestsToTable() throws Exception {
        requestColumn.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date_requestedProperty());
        requestApprove.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
        dateApprove.setCellValueFactory(cellData -> cellData.getValue().date_approveProperty());
        requestDenied.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        dateDenied.setCellValueFactory(cellData -> cellData.getValue().date_requestedProperty());
        
        getRequest();
        pendingTable.setItems(requestsPending);
        approveTable.setItems(requestsApprove);
        deniedTable.setItems(requestsDenied);

        
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button cancelButton = new Button("Cancel");

            {
                
                cancelButton.setOnAction(event -> {
                    requestsModel request = getTableView().getItems().get(getIndex());
                    deleteRequest(request); 
                    try {
                        getRequest(); 
                        pendingTable.setItems(requestsPending); 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(cancelButton);
                }
            }
        });
    }

    public void deleteRequest(requestsModel request) {
        try {
            String query = "DELETE FROM requests WHERE id = ?";
            java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, request.getid());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML private Pane home, pending, approve,denied, reason, credentialsPane;
    public void homeClick(){
        home.setVisible(true);
        pending.setVisible(false);
        approve.setVisible(false);
        credentialsPane.setVisible(false);
        denied.setVisible(false);
    }
    public void pendingClick()throws Exception{
        home.setVisible(false);
        pending.setVisible(true);
        approve.setVisible(false);
        credentialsPane.setVisible(false);
        denied.setVisible(false);
        loadRequestsToTable();
    }
    public void approveClick()throws Exception{
        home.setVisible(false);
        pending.setVisible(false);
        approve.setVisible(true);
        credentialsPane.setVisible(false);
        denied.setVisible(false);
        loadRequestsToTable();
    }
    public void deniedClick()throws Exception{
        home.setVisible(false);
        pending.setVisible(false);
        approve.setVisible(false);
        denied.setVisible(true);
        credentialsPane.setVisible(false);
        loadRequestsToTable();
    }
    String studentRequested = "",studentID="1";
    public void CORClick(){
        studentRequested = "COR";
        home.setVisible(false);
        reason.setVisible(true);
    }
    public void GRADEClick(){
        home.setVisible(false);
        reason.setVisible(true);//ilisanan
    }
    public void CREDClick(){
//        studentRequested = "CREDENTIAL";
        home.setVisible(false);
        credentialsPane.setVisible(true);
    }
    @FXML private CheckBox tor, ad, authentication, goodmoral, diploma, cav;
    public void requestCredentials()throws Exception{
        if (!tor.isSelected() && !ad.isSelected() && !authentication.isSelected() &&
            !goodmoral.isSelected() && !diploma.isSelected() && !cav.isSelected()) {
            JOptionPane.showMessageDialog(null, "REQUEST DENIED, NO REASON");
            home.setVisible(true);
            credentialsPane.setVisible(false);
        }else{
            loadRequestForCredentials();
            JOptionPane.showMessageDialog(null, "REQUEST SUCCESS");
        }
    }
    public void loadRequestForCredentials()throws Exception{
        StringBuilder selectedCheckBoxes = new StringBuilder();

        if (tor.isSelected()) {
            selectedCheckBoxes.append(tor.getText());
        }
        if (ad.isSelected()) {
            if (selectedCheckBoxes.length() > 0) selectedCheckBoxes.append(", ");
            selectedCheckBoxes.append(ad.getText());
        }
        if (authentication.isSelected()) {
            if (selectedCheckBoxes.length() > 0) selectedCheckBoxes.append(", ");
            selectedCheckBoxes.append(authentication.getText());
        }
        if (goodmoral.isSelected()) {
            if (selectedCheckBoxes.length() > 0) selectedCheckBoxes.append(", ");
            selectedCheckBoxes.append(goodmoral.getText());
        }
        if (diploma.isSelected()) {
            if (selectedCheckBoxes.length() > 0) selectedCheckBoxes.append(", ");
            selectedCheckBoxes.append(diploma.getText());
        }
        if (cav.isSelected()) {
            if (selectedCheckBoxes.length() > 0) selectedCheckBoxes.append(", ");
            selectedCheckBoxes.append(cav.getText());
        }

        // Convert StringBuilder to String
        String selectedNames = selectedCheckBoxes.toString();
        
        String insertSql = "INSERT INTO `requests`(`student_id`, `reason`, `file`) "
                            + "VALUES ('" + studentID + "', "
                            + "'" + reasonOfStudent.getText() + "', "
                            + "'" + selectedNames + "');";
        java.sql.Statement statement = con.createStatement();
        int rowsInserted = statement.executeUpdate(insertSql);
        home.setVisible(true);
        credentialsPane.setVisible(false);
        tor.setSelected(false);
        ad.setSelected(false);
        authentication.setSelected(false);
        goodmoral.setSelected(false);
        diploma.setSelected(false);
        cav.setSelected(false);
    }
    @FXML private TextArea reasonOfStudent;
    public void requestSuccess()throws Exception{
        if(reasonOfStudent.getText().equals("")){
            JOptionPane.showMessageDialog(null, "REQUEST DENIED, NO REASON");
        }else{
            JOptionPane.showMessageDialog(null, "SUCCESSFULLY REQUESTED");
            loadRequest();
            reasonOfStudent.setText("");
        }
        home.setVisible(true);
        reason.setVisible(false);
    }
    public void loadRequest()throws Exception{
        String insertSql = "INSERT INTO `requests`(`student_id`, `reason`, `file`) "
                            + "VALUES ('" + studentID + "', "
                            + "'" + reasonOfStudent.getText() + "', "
                            + "'" + studentRequested + "');";
        java.sql.Statement statement = con.createStatement();
        int rowsInserted = statement.executeUpdate(insertSql);
        
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
