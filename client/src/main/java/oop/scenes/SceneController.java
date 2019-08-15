package oop.scenes;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import oop.App;
import oop.connection.Connection;
import oop.details.FoodDetails;
import oop.scenes.controllers.HomeController;
import oop.scenes.controllers.PieChartController;
import oop.scenes.controllers.ProfileController;
import oop.scenes.dialogs.FromToDistanceDialogController;
import oop.scenes.dialogs.GreenEnergyDialogController;
import oop.scenes.dialogs.MoneyDialogController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SceneController {

    private static Stage mainStage;

    private static Parent root;

    private static Map<String, Stage> openedStages;


    /**
     * Constructor for Scene Controller.
     * @param mainStage - main window
     */
    public SceneController(Stage mainStage) {

        SceneController.mainStage = mainStage;
        openedStages = new HashMap<>();
    }

    /**
     * Starts home screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startHome(String callingStage, boolean newWindow) {

        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

            SceneController.mainStage.setOnCloseRequest(event -> {

                mainStage.close();

                for (int i = 1; i < App.achievementDetails.size(); i++) {

                    App.connection.setAchievementProgress(i,
                            App.achievementDetails.get(i).getProgress());
                }
            });

        }

        FXMLLoader loader = null;
        try {
            URL url = new File("src/main/java/oop/scenes/fxml/home.fxml").toURI().toURL();

            loader = new FXMLLoader(url);

            root = loader.load();

            HomeController homeController = loader.getController();
            homeController.start();

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }

    /**
     * Starts login screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startLogin(String callingStage, boolean newWindow) {

        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/login.fxml").toURI().toURL();

            FXMLLoader loader = new FXMLLoader(url);

            root = loader.load();

            mainStage.setScene(new Scene(root, 400, 600));

            mainStage.show();

        } catch (IOException e) {
            System.out.println("File not found");
        }


    }

    /**
     * Starts registration screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startRegistration(String callingStage, boolean newWindow) {


        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/registration.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 550, 700));

        mainStage.show();
    }

    /**
     * Starts initial survey screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startInitialSurvey(String callingStage, boolean newWindow) {


        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/initialsurvey.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 600, 800));
        mainStage.setOnCloseRequest(evt -> {
            System.out.println("Deleting Registration...");
            Connection newConn = new Connection();
            newConn.deleteRegistration(App.getGlobalRegister());
        });

        mainStage.show();
    }

    /**
     * Starts food type screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startFoodType(String callingStage, boolean newWindow) {


        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/foodtype.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }

    /**
     * Starts transportation type screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startTransportationType(String callingStage, boolean newWindow) {


        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/transportationtype.fxml")
                    .toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }

    /**
     * Starts utilities type screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     */
    public static void startUtilitiesType(String callingStage, boolean newWindow) {


        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/utilitiestype.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }

    /**
     * Starts profile screen.
     * @param username - current user's username
     */
    public static void startProfile(String username) {


        System.out.println(username);
        FXMLLoader loader;
        try {
            URL url = new File("src/main/java/oop/scenes/fxml/profile.fxml").toURI().toURL();

            loader = new FXMLLoader(url);

            root = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setUsername(username);
            profileController.start();

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }

    /**
     * Starts achievements screen.
     */
    public static void startAchievements() {


        try {
            URL url = new File("src/main/java/oop/scenes/fxml/achievements.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }

    /**
     * Starts badges screen.
     */
    public static void startBadges() {

        FXMLLoader loader;
        try {
            URL url = new File("src/main/java/oop/scenes/fxml/badges.fxml").toURI().toURL();

            loader = new FXMLLoader(url);

            root = loader.load();

        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        mainStage.getScene().setRoot(root);

        mainStage.show();
    }

    /**
     * Starts leaderBoard screen.
     */
    public static void startLeaderboard() {


        try {
            URL url = new File("src/main/java/oop/scenes/fxml/leaderboard.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }


    /**
     * Starts settings screen.
     */
    public static void startSettings() {


        try {
            URL url = new File("src/main/java/oop/scenes/fxml/settings.fxml").toURI().toURL();

            root = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        mainStage.setScene(new Scene(root, 1000, 600));

        mainStage.show();
    }


    /**
     * Starts PieChart screen.
     * @param callingStage - stage this method was called from
     * @param newWindow - true if new window should be created
     * @param foodDetails - foodDetails to send
     * @param mealtype - type of a meal
     */
    public static void startPieChart(String callingStage, boolean newWindow,
                                     FoodDetails foodDetails, String mealtype) {


        if (newWindow) {
            openedStages.put(callingStage, mainStage);
            mainStage = new Stage();
            mainStage.setResizable(false);

        }

        FXMLLoader loader = null;
        try {
            URL url = new File("src/main/java/oop/scenes/fxml/piechart.fxml").toURI().toURL();

            loader = new FXMLLoader(url);

            root = loader.load();

        } catch (IOException e) {
            System.out.println("File not found");
        }

        PieChartController pieChartController = loader.getController();

        pieChartController.setValues(mealtype, foodDetails);
        pieChartController.start();

        mainStage.setScene(new Scene(root, 1000, 600));
        mainStage.show();
    }

    /**
     * Starts dialog.
     * @param type - type of a dialog
     */
    public static void openDialog(String type) {

        int height = 250;

        Stage tempStage = new Stage();

        tempStage.setResizable(false);


        FXMLLoader loader = null;
        try {

            URL url = null;

            switch (type) {

                case "Plane":

                    url = new File("src/main/java/oop/scenes/fxml/fromtodialog.fxml")
                            .toURI().toURL();
                    loader = new FXMLLoader(url);
                    root = loader.load();
                    height = 200;
                    break;

                case "BikeWalk":
                case "PubTrans":
                case "Car":

                    url = new File("src/main/java/oop/scenes/fxml/fromtodistancedialog.fxml")
                            .toURI().toURL();
                    loader = new FXMLLoader(url);
                    root = loader.load();

                    FromToDistanceDialogController ftdController = loader.getController();
                    ftdController.setType(type);
                    height = 250;
                    break;

                case "addcar":

                    url = new File("src/main/java/oop/scenes/fxml/addcardialog.fxml")
                            .toURI().toURL();
                    loader = new FXMLLoader(url);
                    root = loader.load();

                    height = 300;

                    break;

                default:
                    break;

            }

        } catch (IOException e) {
            System.out.println("File not found");
        }


        tempStage.initOwner(mainStage);
        tempStage.initStyle(StageStyle.UNDECORATED);
        tempStage.initModality(Modality.WINDOW_MODAL);
        tempStage.setScene(new Scene(root, 500, height));
        tempStage.show();
    }

    /**
     * Starts utilities dialog.
     * @param type - type of a dialog
     * @param controlButton - button from calling function, corresponding to the type
     * @param completed - saves number of filled out categories in utilities
     * @param prices - map of entered prices
     */
    public static void openUtilitiesDialog(String type, Button controlButton,
                                           Label completed, Map prices) {

        int height = 250;

        Stage tempStage = new Stage();

        tempStage.setResizable(false);


        FXMLLoader loader = null;
        try {

            URL url = null;

            switch (type) {

                case "greenenergy":

                    url = new File("src/main/java/oop/scenes/fxml/greenenergydialog.fxml")
                            .toURI().toURL();
                    loader = new FXMLLoader(url);
                    root = loader.load();

                    GreenEnergyDialogController gcontroller = loader.getController();
                    gcontroller.setType(type);
                    gcontroller.setControllButton(controlButton);
                    gcontroller.setCompleted(completed);
                    gcontroller.setPrices(prices);
                    gcontroller.start();
                    height = 260;

                    break;

                case "electricity":
                case "naturalgas":
                case "water":

                    url = new File("src/main/java/oop/scenes/fxml/moneydialog.fxml")
                            .toURI().toURL();
                    loader = new FXMLLoader(url);
                    root = loader.load();

                    MoneyDialogController mcontroller = loader.getController();
                    mcontroller.setType(type);
                    mcontroller.setControllButton(controlButton);
                    mcontroller.setCompleted(completed);
                    mcontroller.setPrices(prices);
                    mcontroller.start();
                    height = 250;

                    break;

                default:
                    break;

            }

        } catch (IOException e) {
            System.out.println("File not found");
        }


        tempStage.initOwner(mainStage);
        tempStage.initStyle(StageStyle.UNDECORATED);
        tempStage.initModality(Modality.WINDOW_MODAL);
        tempStage.setScene(new Scene(root, 500, height));
        tempStage.show();
    }


    /**
     * Closes stage.
     * @param stageName - stage to close
     */
    public static void closeStage(String stageName) {

        try {
            Stage temp = openedStages.get(stageName);
            temp.close();

        } catch (NullPointerException e) {
            System.out.println("Error");
        }

        openedStages.remove(stageName);
    }

    /**
     * Creates sidebar.
     * @param controlButton - sidebar control button
     * @param drawer - sidebar object
     */
    public static void sideBarControl(Button controlButton, JFXDrawer drawer) {

        VBox vbox = null;

        try {
            URL url = new File("src/main/java/oop/scenes/fxml/sidebar.fxml").toURI().toURL();

            vbox = FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("File not found");
        }

        drawer.setSidePane(vbox);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.millis(400));
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.setAutoReverse(true);
        transition.setNode(controlButton);

        controlButton.setOnAction(e -> {

            if (drawer.isClosed()) {
                drawer.setPrefWidth(250);
                drawer.open();
                transition.setByX(250);
                transition.play();
            } else {
                transition.setByX(-250);
                transition.play();
                drawer.close();
                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                drawer.setPrefWidth(0);
                            }
                        },
                        500
                );
            }
        });
    }
}
