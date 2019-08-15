package oop.scenes;

public class Properties {

    public static String vegan = "Vegan";

    public static String vegetarian = "Vegetarian";

    public static String pescetarian = "Pescetarian";

    public static String lowmeat = "Low meat";

    public static String mediummeat = "Medium meat";

    public static String highmeat = "High meat";

    private int windowWidth = 1024;

    private int windowHeight = 600;

    private int sidebarWidth = 200;

    private double sidebarRatio = 0.15;

    private double sidebarElementMaxHeight = 75;

    private double sidebarElementHeightRatio = 0.1;

    private int sidebarLogoHeightRatio = 3;

    private double sidebarProfileHeightRatio = 1.5;

    private double profileImageHeightRatio = 0.2;

    private double profileImageWidthRatio = 0.1;

    private double profileChangeLogWidth = 390;

    private double profileChangeLogHeight = 300;

    private double changelogEntryWidth = 350;

    private double profileFriendsWidth = 268;

    private double profileAboutWidth = 300;

    private double addremFriendPaneSize = 150;

    private double profileSearchBarWidth = 150;

    private double achievementContainerHeight = 150;

    private double achievementImageSize = 100;

    private double achievementDetailsHeight = 120;

    private String labelFont = "Arial";


    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getSidebarWidth() {
        return sidebarWidth;
    }

    public void setSidebarWidth(int sidebarWidth) {
        this.sidebarWidth = sidebarWidth;
    }

    public double getSidebarRatio() {
        return sidebarRatio;
    }

    public void setSidebarRatio(double sidebarRatio) {
        this.sidebarRatio = sidebarRatio;
    }

    public double getSidebarElementMaxHeight() {
        return sidebarElementMaxHeight;
    }

    public void setSidebarElementMaxHeight(double sidebarElementMaxHeight) {
        this.sidebarElementMaxHeight = sidebarElementMaxHeight;
    }

    public double getSidebarElementHeightRatio() {
        return sidebarElementHeightRatio;
    }

    public void setSidebarElementHeightRatio(double sidebarElementHeightRatio) {
        this.sidebarElementHeightRatio = sidebarElementHeightRatio;
    }

    public int getSidebarLogoHeightRatio() {
        return sidebarLogoHeightRatio;
    }

    public double getSidebarProfileHeightRatio() {
        return sidebarProfileHeightRatio;
    }

    public void setSidebarProfileHeightRatio(double sidebarProfileHeightRatio) {
        this.sidebarProfileHeightRatio = sidebarProfileHeightRatio;
    }

    public double getProfileImageHeightRatio() {
        return profileImageHeightRatio;
    }

    public double getProfileImageWidthRatio() {
        return profileImageWidthRatio;
    }

    public double getProfileChangeLogWidth() {
        return profileChangeLogWidth;
    }

    public double getProfileChangeLogHeight() {
        return profileChangeLogHeight;
    }

    public double getChangelogEntryWidth() {
        return changelogEntryWidth;
    }

    public double getProfileFriendsWidth() {
        return profileFriendsWidth;
    }

    public double getProfileAboutWidth() {
        return profileAboutWidth;
    }

    public double getAddremFriendPaneSize() {
        return addremFriendPaneSize;
    }

    public double getProfileSearchBarWidth() {
        return profileSearchBarWidth;
    }

    public double getAchievementContainerHeight() {
        return achievementContainerHeight;
    }

    public double getAchievementImageSize() {
        return achievementImageSize;
    }

    public double getAchievementDetailsHeight() {
        return achievementDetailsHeight;
    }

    public String getLabelFont() {
        return labelFont;
    }

}
