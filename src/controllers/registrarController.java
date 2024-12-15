/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import studentModel.historyModel;
import studentModel.requestsModel;

/**
 *
 * @author Administrator
 */
public class registrarController {final String DB_URL = "jdbc:mysql://localhost/becsystem";
    final String USER = "root";
    final String PASS = "";
    public String url;
    public Connection con = null;
    
    
    public void initialize()throws Exception{
        connect();
        loadRequestsToTable();
    }
    public void logout(ActionEvent event){
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    ObservableList<requestsModel> requestsPending = FXCollections.observableArrayList();
    ObservableList<requestsModel> requestsApprove = FXCollections.observableArrayList();
    public void getRequest()throws Exception{
        requestsPending.clear();requestsApprove.clear();
        java.sql.Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT CONCAT(u.firstname,' ', u.middlename, ' ', u.lastname) as student_id, "
                                                    + "r.id, date_request, r.status, reason, date_approve, r.file " 
                                                    + "FROM requests r " 
                                                    + "JOIN users u ON r.student_id = u.id");
        while(resultSet.next()){
            String i = resultSet.getString("id");
            String ii = resultSet.getString("student_id");
            String iii = resultSet.getString("date_request");
            String iiii = resultSet.getString("status");
            String iiiii = resultSet.getString("reason");
            String iiiiii = resultSet.getString("date_approve");
            String iiiiiii = resultSet.getString("file");
            System.out.println(iiiiiii);
            if(iiii.equals("pending")){
                requestsPending.add(new requestsModel(i,ii,iii,iiii,iiiii,iiiiii,iiiiiii));
            }else if(iiii.equals("approved")){
                requestsApprove.add(new requestsModel(i,ii,iii,iiii,iiiii,iiiiii,iiiiiii));
                
            }
        }
    }
    
    ObservableList<historyModel> internal = FXCollections.observableArrayList();
    ObservableList<historyModel> external = FXCollections.observableArrayList();
    public void requestHistory()throws Exception{
        internal.clear();external.clear();
        java.sql.Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT CONCAT(u.firstname,' ', u.middlename, ' ', u.lastname) as student_id, r.id, date_request, r.status, reason,CONCAT(u.program, ' ',u.year,u.section) as course , r.file, u.type\n" +
                                                        "FROM requests r\n" +
                                                        "JOIN users u ON r.student_id = u.id;");
        while(resultSet.next()){
            String i = resultSet.getString("id");
            String ii = resultSet.getString("student_id");
            String iii = resultSet.getString("date_request");
            String iiii = resultSet.getString("file");
            String iiiii = resultSet.getString("course");
            String iiiiii = resultSet.getString("type");
            if(iiiiii.equals("INTERNAL")){
                internal.add(new historyModel(i,ii,iii,iiii,iiiii,iiiiii));
            }else{
                external.add(new historyModel(i,ii,iii,iiii,iiiii,iiiiii));
                
            }
        }
    }
    @FXML private TableView<historyModel> historyTable;
    @FXML private TableColumn<historyModel, String> nameHistory,dateRequestedHistory, fileHistory, courseHistory;
    public void showHistoryInternal()throws Exception{
        requestHistory();
        nameHistory.setCellValueFactory(cellData -> cellData.getValue().fullnameProperty());
        dateRequestedHistory.setCellValueFactory(cellData -> cellData.getValue().date_requestedProperty());
        fileHistory.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
        courseHistory.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
        historyTable.setItems(internal);
    }
    public void showHistoryExternal()throws Exception{
        requestHistory();
        nameHistory.setCellValueFactory(cellData -> cellData.getValue().fullnameProperty());
        dateRequestedHistory.setCellValueFactory(cellData -> cellData.getValue().date_requestedProperty());
        fileHistory.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
        courseHistory.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
        historyTable.setItems(external);
    }
    
    @FXML private TableView<requestsModel> pendingTable;
    @FXML private TableColumn<requestsModel, String> name,dateRequested, file;
    @FXML private TableColumn<requestsModel, Void> actions;
    public void loadRequestsToTable() throws Exception {
        name.setCellValueFactory(cellData -> cellData.getValue().student_idProperty());
        dateRequested.setCellValueFactory(cellData -> cellData.getValue().date_requestedProperty());
        file.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
        getRequest();
        pendingTable.setItems(requestsPending);

        actions.setCellFactory(col -> new TableCell<>() {
            private final Button cancelButton = new Button();
            private final Button approveButton = new Button();
            private final HBox buttonContainer = new HBox(10);
            {
                ImageView cancelIcon = new ImageView(new Image("registrar/images/cancel.png")); // Replace with your icon file
                cancelIcon.setFitHeight(23); 
                cancelIcon.setFitWidth(23);
                ImageView approveIcon = new ImageView(new Image("registrar/images/check.png")); // Replace with your icon file
                approveIcon.setFitHeight(20);
                approveIcon.setFitWidth(20);
                cancelButton.setGraphic(cancelIcon);
                cancelButton.setContentDisplay(ContentDisplay.LEFT); 
                approveButton.setGraphic(approveIcon);
                approveButton.setContentDisplay(ContentDisplay.LEFT);
                buttonContainer.getChildren().addAll(cancelButton, approveButton);
                cancelButton.setOnAction(event -> {
                    requestsModel request = getTableView().getItems().get(getIndex());
                    try {
                        deleteRequest(request); 
                        getRequest(); // Refresh the data
                        pendingTable.setItems(requestsPending); // Reload the table
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                approveButton.setOnAction(event -> {
                    requestsModel request = getTableView().getItems().get(getIndex());
                    try {
                        approveRequest(request);
                        getRequest(); // Refresh the data
                        pendingTable.setItems(requestsPending); // Reload the table
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
                    setGraphic(buttonContainer); // Set the HBox with buttons as the cell graphic
                }
            }
        });
    }
    public void approveRequest(requestsModel request) throws Exception{
        String query = "UPDATE requests SET status = 'approved', date_approve = NOW() WHERE id = ?";
        java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, request.getid());
        preparedStatement.executeUpdate();
    }
    public void deleteRequest(requestsModel request) throws Exception{
        String query = "UPDATE requests SET status = 'denied' WHERE id = ?";
        java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, request.getid());
        preparedStatement.executeUpdate();
    }
    @FXML private Pane pending, approve, history;
    public void pendingClick()throws Exception{
        pending.setVisible(true);
        history.setVisible(false);
        getRequest();
    }
    public void historyClick()throws Exception{
        pending.setVisible(false);
        history.setVisible(true);
        showHistoryInternal();
        getRequest();
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
