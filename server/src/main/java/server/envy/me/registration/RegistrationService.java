package server.envy.me.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository regRepository;

    /**
     * The method queries the database finding user by username.
     * @param id of the user to find Registration information related to.
     * @return the LoginClass containing username and password
     */
    public RegistrationClass getRegistrationInfo(int id) {
        return regRepository.findById(id);
    }
}
