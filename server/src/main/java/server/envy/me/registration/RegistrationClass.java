package server.envy.me.registration;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "userdata")
public class RegistrationClass {

    @Id
    @Column(name = "userid")
    private int id;

    /**
     * Ignore username and password since they are being registered.
     */
    @Transient
    private String username;

    @Transient
    private String password;

    @Column(name = "Surname")
    private String lastname;

    @Column(name = "Forename")
    private String firstname;

    @Column(name = "Birthday")
    private Date dateofbirth;

    @Column(name = "Gender")
    private String gender;

    /**
     * Class to contain the data coming from the registration form.
     * @param username username of the user
     * @param firstname first name user
     * @param lastname last name user
     * @param dateofbirth Date of birth user
     * @param gender gender user
     * @param password password to input in the Database.
     */
    public RegistrationClass(String username, String firstname,
                             String lastname, Date dateofbirth,
                             String gender, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.password = password;
    }

    /**
     * For Deserialization purposes.
     */
    public RegistrationClass() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns password of user.
     * @return password stored in registration object
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
