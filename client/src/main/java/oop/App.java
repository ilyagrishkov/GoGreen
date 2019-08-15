package oop;

import javafx.application.Application;
import javafx.stage.Stage;
import oop.connection.Connection;
import oop.details.AchievementDetails;
import oop.details.BadgeDetails;
import oop.details.RegistrationDetails;
import oop.scenes.SceneController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Start the login window.
 */
public class App extends Application {

    public static Connection connection;
    public static ArrayList<AchievementDetails> achievementDetails;
    public static ArrayList<BadgeDetails> badgeDetails;

    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final String pathToLogFile = "./logs/LogFile.log";
    private static FileHandler fh;


    private static String username;
    private static int userId;

    private static RegistrationDetails globalRegister;

    /**
     * Entry point of the app.
     *
     * @param args the arguments the app is launched with
     */


    public static void main(final String[] args) {
        username = "";

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler(pathToLogFile);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Start of the Client Log");

        } catch (IOException e) {
            logger.warning("Error while loading logger");
        }

        launch();
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        App.userId = userId;
    }

    public static RegistrationDetails getGlobalRegister() {
        return globalRegister;
    }

    public static void setGlobalRegister(RegistrationDetails globalRegister) {
        App.globalRegister = globalRegister;
    }

    public static void log(Level level, String callerClass, String msg) {
        logger.log(level, callerClass + ": " + msg);
    }

    @Override
    public final void start(final Stage stage) {



        logger.entering(getClass().getName(), "start");

        SceneController sceneController = new SceneController(stage);

        connection = new Connection();

        achievementDetails = new ArrayList<>();
        achievementDetails.add(new AchievementDetails(-1, "-1", "bogus", -1));

        badgeDetails = new ArrayList<>();
        badgeDetails.add(new BadgeDetails(-1, "", ""));

        SceneController.startLogin("main", false);
        stage.setResizable(false);
        stage.show();

        logger.exiting(getClass().getName(), "start");
    }
}
