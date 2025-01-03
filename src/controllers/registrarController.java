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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import studentModel.historyModel;
import studentModel.requestsModel;
import studentModel.service;
import studentModel.userModel;

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
        loadRequestsToServiceTable();
        loadRequestsToUsersTable();
    }
    
    
    ObservableList<service> services = FXCollections.observableArrayList();
    public void getServices()throws Exception{
        services.clear();
        java.sql.Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM service");
        while(resultSet.next()){
            String i = resultSet.getString("id");
            String ii = resultSet.getString("name");
            String iii = resultSet.getString("status");
            services.add(new service(i,ii,iii));
        }
    }
    @FXML private TableView<service> serviceTable;
    @FXML private TableColumn<service, String> m1,m2;
    @FXML private TableColumn<service, Void> m3;
    public void loadRequestsToServiceTable() throws Exception {
        m1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        m2.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        
        getServices();
        serviceTable.setItems(services);

        
        m3.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("EDIT");
            private final Button deleteButton = new Button("DELETE");
            private final HBox buttonContainer = new HBox(10);
            {
                
                buttonContainer.getChildren().addAll(editButton, deleteButton);
                editButton.setOnAction(event -> {
                    service serviced = getTableView().getItems().get(getIndex());  // Get the selected service
                    try {
                        // Current service data
                        String currentServiceName = serviced.getname();  // Get the current name
                        String currentStatus = serviced.getstatus();    // Get the current status

                        // Create input fields pre-filled with current data
                        JTextField serviceNameField = new JTextField(currentServiceName);
                        JTextField statusField = new JTextField(currentStatus);

                        Object[] message = {
                            "Service Name:", serviceNameField,
                            "Status:", statusField
                        };

                        // Show the input dialog
                        int option = JOptionPane.showConfirmDialog(null, message, "Edit Service", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.OK_OPTION) {
                            String newServiceName = serviceNameField.getText().trim();
                            String newStatus = statusField.getText().trim();

                            // Validate inputs
                            if (newServiceName.isEmpty() || newStatus.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Both fields must be filled. Edit failed.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Prepare the SQL UPDATE query
                                String query = "UPDATE service SET name = ?, status = ? WHERE id = ?";
                                java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
                                // Set the values in the query
                                preparedStatement.setString(1, newServiceName);  // Set the new service name
                                preparedStatement.setString(2, newStatus);       // Set the new status
                                preparedStatement.setString(3, serviced.getid());  // Use the service id to update the correct record

                                int rowsAffected = preparedStatement.executeUpdate();
                                
                                loadRequestsToServiceTable();

                                JOptionPane.showMessageDialog(null, "Service updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                deleteButton.setOnAction(event -> {
                    service serviced = getTableView().getItems().get(getIndex());
                    try {
                        String query = "DELETE FROM service WHERE id = ?";
                        java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
                        preparedStatement.setString(1, serviced.getid());
                        preparedStatement.executeUpdate();
                        
                        loadRequestsToServiceTable();
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
    public void addNewService()throws Exception{
        String serviceName = JOptionPane.showInputDialog(null, "Enter the service name:", "Add Service", JOptionPane.PLAIN_MESSAGE);

        // Check if the input is null (user pressed cancel) or empty
        if (serviceName == null) {
        } else if (serviceName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Service name cannot be empty. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            
            String insertSql = "INSERT INTO `service`(`name`) "
                                + "VALUES ("+"'" + serviceName + "')";
            java.sql.Statement statement = con.createStatement();
            int rowsInserted = statement.executeUpdate(insertSql);
            loadRequestsToServiceTable();
            JOptionPane.showMessageDialog(null, "Service added: " + serviceName, "Success", JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    
    
    ObservableList<userModel> unverifiedUsers = FXCollections.observableArrayList();
    public void getUsers()throws Exception{
        unverifiedUsers.clear();
        java.sql.Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE status = '0'");
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
            unverifiedUsers.add(new userModel(i1,i2,i3,i4,i5,i6,i7,i8,i9,i10, i11, i12, i13,i14,i15));
            
        }
    }
    @FXML private TableView<userModel> studentTable;
    @FXML private TableColumn<userModel, String> q1,q2,q3,q4,q5,q6,q7;
    @FXML private TableColumn<userModel, Void> q8;
    public void loadRequestsToUsersTable() throws Exception {
        q1.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        q2.setCellValueFactory(cellData -> cellData.getValue().schoolIdProperty());
        q3.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        q4.setCellValueFactory(cellData -> cellData.getValue().yearAndSectionProperty());
        q5.setCellValueFactory(cellData -> cellData.getValue().dateCreatedProperty());
        q6.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        q7.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
        
        getUsers();
        studentTable.setItems(unverifiedUsers);

        
        q8.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("VERIFY");
            {
                
                editButton.setOnAction(event -> {
                    userModel serviced = getTableView().getItems().get(getIndex());  // Get the selected service
                    try {
                        String query = "UPDATE users SET status = '1' WHERE id = ?";
                        java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);
                        preparedStatement.setString(1, serviced.getId()+"");
                        preparedStatement.executeUpdate();
                        loadRequestsToUsersTable();
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
                    setGraphic(editButton); // Set the HBox with buttons as the cell graphic
                }
            }
        });
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
    @FXML private TableColumn<requestsModel, String> name,dateRequested, file, reason;
    @FXML private TableColumn<requestsModel, Void> actions;
    public void loadRequestsToTable() throws Exception {
        name.setCellValueFactory(cellData -> cellData.getValue().student_idProperty());
        dateRequested.setCellValueFactory(cellData -> cellData.getValue().date_requestedProperty());
        file.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
        reason.setCellValueFactory(cellData -> cellData.getValue().reasonProperty());
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
    @FXML private Pane pending, approve, history,service,students;
    public void pendingClick()throws Exception{
        students.setVisible(false);
        service.setVisible(false);
        pending.setVisible(true);
        history.setVisible(false);
        getRequest();
    }
    public void historyClick()throws Exception{
        students.setVisible(false);
        service.setVisible(false);
        pending.setVisible(false);
        history.setVisible(true);
        showHistoryInternal();
        getRequest();
    }
    public void serviceClick()throws Exception{
        students.setVisible(false);
        service.setVisible(true);
        pending.setVisible(false);
        history.setVisible(false);
        showHistoryInternal();
        getRequest();
    }
    public void studentsClick()throws Exception{
        students.setVisible(true);
        service.setVisible(false);
        pending.setVisible(false);
        history.setVisible(false);
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
