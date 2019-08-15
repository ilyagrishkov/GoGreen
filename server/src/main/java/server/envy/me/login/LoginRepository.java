package server.envy.me.login;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginRepository extends CrudRepository<LoginClass, Integer> {

    public LoginClass findById(int id);

    public LoginClass findByUsername(String username);

    List<LoginClass> findAll();

}