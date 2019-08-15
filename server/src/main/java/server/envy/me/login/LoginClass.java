package server.envy.me.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "useracc")
public class LoginClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    //@OneToOne(mappedBy = "loginInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private RegistrationClass UserData;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public LoginClass(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Creating new user.
     * @param id of the user
     * @param username of the user
     * @param password of the user
     */
    public LoginClass(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * For Deserialization purposes.
     */
    public LoginClass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAll() {

        return "AUTHORIZATION: Username: " + username + ", Password: " + password;
    }
}
