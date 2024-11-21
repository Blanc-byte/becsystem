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
public class historyModel {
    private final StringProperty id;
    private final StringProperty fullname;
    private final StringProperty date_requested;
    private final StringProperty file;
    private final StringProperty course;
    private final StringProperty type;
    public historyModel(String a,String b,String c,String d,String e, String f) {
        this.id = new SimpleStringProperty(a);
        this.fullname = new SimpleStringProperty(b);
        this.date_requested = new SimpleStringProperty(c);
        this.file = new SimpleStringProperty(d);
        this.course = new SimpleStringProperty(e);
        this.type = new SimpleStringProperty(f);
    }
    
    public StringProperty idProperty() {return id;}
    public StringProperty fullnameProperty() {return fullname;}
    public StringProperty date_requestedProperty() {return date_requested;}
    public StringProperty fileProperty() {return file;}
    public StringProperty courseProperty() {return course;}
    public StringProperty typeProperty() {return type;}
    
    public String getid() {return id.get();}

    public void setid(String a) {this.id.set(a);}

    public String getfullname() {return fullname.get();}

    public void setfullname(String a) {this.fullname.set(a);}
    
    public String getdate_requested() {return date_requested.get();}

    public void setdate_requested(String a) {this.date_requested.set(a);}
    
    public String getfile() {return file.get();}

    public void setfile(String a) {this.file.set(a);}
    
    public String getcourse() {return course.get();}

    public void setcourse(String a) {this.course.set(a);}
    
    public String gettype() {return type.get();}

    public void settype(String a) {this.type.set(a);}
}
