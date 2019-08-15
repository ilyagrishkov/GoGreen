package server.envy.me.registration;

import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<RegistrationClass, Integer> {
    public RegistrationClass findById(int id);
}