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
public class requestsModel {
    private final StringProperty id;
    private final StringProperty student_id;
    private final StringProperty date_requested;
    private final StringProperty status;
    private final StringProperty reason;
    private final StringProperty date_approve;
    private final StringProperty file;

    public requestsModel(String a,String b,String c,String d,String e,String f,String g) {
        this.id = new SimpleStringProperty(a);
        this.student_id = new SimpleStringProperty(b);
        this.date_requested = new SimpleStringProperty(c);
        this.status = new SimpleStringProperty(d);
        this.reason = new SimpleStringProperty(e);
        this.date_approve = new SimpleStringProperty(f);
        this.file = new SimpleStringProperty(g);
    }

    public StringProperty idProperty() {return id;}
    public StringProperty student_idProperty() {return student_id;}
    public StringProperty date_requestedProperty() {return date_requested;}
    public StringProperty statusProperty() {return status;}
    public StringProperty reasonProperty() {return reason;}
    public StringProperty date_approveProperty() {return date_approve;}
    public StringProperty fileProperty() {return file;}

    public String getid() {return id.get();}

    public void setid(String a) {this.id.set(a);}

    public String getstudent_id() {return student_id.get();}

    public void setstudent_id(String a) {this.student_id.set(a);}
    
    public String getdate_requested() {return date_requested.get();}

    public void setdate_requested(String a) {this.date_requested.set(a);}
    
    public String getstatus() {return status.get();}

    public void setstatus(String a) {this.status.set(a);}
    
    public String getreason() {return reason.get();}

    public void setreason(String a) {this.reason.set(a);}
    
    public String getdate_approve() {return date_approve.get();}

    public void setdate_approve(String a) {this.date_approve.set(a);}
    
    public String getfile() {return file.get();}

    public void setfile(String a) {this.file.set(a);}
}
