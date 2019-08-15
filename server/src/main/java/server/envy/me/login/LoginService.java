package server.envy.me.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    /**
     * Returns all useracc instances in the database.
     * @return an iterable list of all the Login instances on the database
     */
    public List<LoginClass> getAllLogins() {
        List<LoginClass> result = new ArrayList<LoginClass>();
        loginRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * The method queries the database finding user by username.
     * @param id of the user to find Login Info.
     * @return the LoginClass containing username and password
     */
    public LoginClass getLogin(int id) {
        return loginRepository.findById(id);
    }

    /**
     * Find user in database by username.
     * @param username of the user to retrieve login information about
     * @return instance of LoginClass
     */
    public LoginClass getLoginByUsername(String username) {
        return loginRepository.findByUsername(username);
    }

    /**
     * Deletes User from database given username as a String.
     * @param username of the user to delete
     */
    public void deleteByUsername(String username) {
        int id = loginRepository.findByUsername(username).getId();
        loginRepository.deleteById(id);
    }
}
