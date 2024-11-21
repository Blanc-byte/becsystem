
package studentModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class userModel {
    private final IntegerProperty id;
    private final StringProperty firstname;
    private final StringProperty middlename;
    private final StringProperty lastname;
    private final StringProperty contact;
    private final StringProperty schoolId;
    private final StringProperty year;
    private final StringProperty section;
    private final StringProperty dateCreated;
    private final StringProperty status;
    private final StringProperty type;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty role;
    private final StringProperty course;

    // Constructor
    public userModel(int id, String firstname, String middlename, String lastname, String contact, 
                   String schoolId, String year, String section, String dateCreated, 
                   String status, String type, String username, String password, String role, String course) {
        this.id = new SimpleIntegerProperty(id);
        this.firstname = new SimpleStringProperty(firstname);
        this.middlename = new SimpleStringProperty(middlename);
        this.lastname = new SimpleStringProperty(lastname);
        this.contact = new SimpleStringProperty(contact);
        this.schoolId = new SimpleStringProperty(schoolId);
        this.year = new SimpleStringProperty(year);
        this.section = new SimpleStringProperty(section);
        this.dateCreated = new SimpleStringProperty(dateCreated);
        this.status = new SimpleStringProperty(status);
        this.type = new SimpleStringProperty(type);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.course = new SimpleStringProperty(course);
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        this.id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String value) {
        this.firstname.set(value);
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename.get();
    }

    public void setMiddlename(String value) {
        this.middlename.set(value);
    }

    public StringProperty middlenameProperty() {
        return middlename;
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String value) {
        this.lastname.set(value);
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String value) {
        this.contact.set(value);
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public String getSchoolId() {
        return schoolId.get();
    }

    public void setSchoolId(String value) {
        this.schoolId.set(value);
    }

    public StringProperty schoolIdProperty() {
        return schoolId;
    }

    public String getYear() {
        return year.get();
    }

    public void setYear(String value) {
        this.year.set(value);
    }

    public StringProperty yearProperty() {
        return year;
    }

    public String getSection() {
        return section.get();
    }

    public void setSection(String value) {
        this.section.set(value);
    }

    public StringProperty sectionProperty() {
        return section;
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public void setDateCreated(String value) {
        this.dateCreated.set(value);
    }

    public StringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        this.status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        this.type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String value) {
        this.username.set(value);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String value) {
        this.password.set(value);
    }

    public StringProperty passwordProperty() {
        return password;
    }
    
    public String getRole() {
        return role.get();
    }
    
    public void setRole(String value) {
        this.role.set(value);
    }

    public StringProperty roleProperty() {
        return role;
    }
    
    public String getCourse() {
        return course.get();
    }
    
    public void setCourse(String value) {
        this.course.set(value);
    }

    public StringProperty courseProperty() {
        return course;
    }
}
