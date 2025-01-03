/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentModel;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Administrator
 */

//SELECT r.id, CONCAT(firstname, " ", middlename,  " ", lastname) as Fullname, date_request, file, CONCAT(program, " ", year,section) as Course, type
//FROM requests r
//JOIN users u ON r.student_id = u.id
//WHERE r.status = 'approved'
public class service {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty status;
    public service(String a,String b,String c) {
        this.id = new SimpleStringProperty(a);
        this.name = new SimpleStringProperty(b);
        this.status = new SimpleStringProperty(c);
    }
    
    public StringProperty idProperty() {return id;}
    public StringProperty nameProperty() {return name;}
    public StringProperty statusProperty() {return status;}
    
    public String getid() {return id.get();}

    public void setid(String a) {this.id.set(a);}

    public String getname() {return name.get();}

    public void setname(String a) {this.name.set(a);}
    
    public String getstatus() {return status.get();}

    public void setstatus(String a) {this.status.set(a);}
}
