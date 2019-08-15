package server.envy.me.utilities;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesToClient {

    private List<UtilitiesClass> utilities;

    public UtilitiesToClient(List<UtilitiesClass> utilities) {
        this.utilities = utilities;
    }

    public UtilitiesToClient() {
        this.utilities = new ArrayList<>();
    }

    public List<UtilitiesClass> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<UtilitiesClass> utilities) {
        this.utilities = utilities;
    }
}
