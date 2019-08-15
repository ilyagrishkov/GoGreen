package server.envy.me.userutilities;

import java.io.Serializable;

public class UserUtilitiesCompositeKey implements Serializable {
    private int userId;
    private int utilitiesId;

    public UserUtilitiesCompositeKey(int userId, int utilitiesId) {
        this.userId = userId;
        this.utilitiesId = utilitiesId;
    }

    public UserUtilitiesCompositeKey() {}

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
