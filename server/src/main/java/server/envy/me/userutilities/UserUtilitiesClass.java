package server.envy.me.userutilities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "userutilities")
@IdClass(UserUtilitiesCompositeKey.class)
public class UserUtilitiesClass {

    @Id
    @Column(name = "userid")
    private int userId;

    @Id
    @Column(name = "utilitiesid")
    private int utilitiesId;

    /**
     * Constructor.
     * @param userId of the user
     * @param utilitiesId of the utilities
     */
    public UserUtilitiesClass(int userId, int utilitiesId) {
        this.userId = userId;
        this.utilitiesId = utilitiesId;
    }

    public UserUtilitiesClass() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUtilitiesId() {
        return utilitiesId;
    }

    public void setUtilitiesId(int utilitiesId) {
        this.utilitiesId = utilitiesId;
    }
}
