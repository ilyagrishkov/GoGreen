package server.envy.me.settings;

import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository extends CrudRepository<SettingsClass, Integer> {
    SettingsClass findById(int userId);
}