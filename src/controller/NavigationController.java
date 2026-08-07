package controller;

import model.BloodRequest;
import model.User;
import view.*;

import javax.swing.*;

/**
 * Singleton controller managing navigation between Swing views.
 */
public class NavigationController {
    private static NavigationController instance;
    private User currentUser;

    private NavigationController() {}

    // Thread-safe singleton accessor
    public static synchronized NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Opens Welcome screen
    public void openWelcomeView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new WelcomeView();
    }

    // Opens Login screen
    public void openLoginView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new LoginView();
    }

    // Opens Registration screen
    public void openRegistrationView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new RegistrationView();
    }

    // Opens Search screen
    public void openSearchBloodView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new SearchBloodView();
    }

    // Opens Request submission screen
    public void openRequestBloodView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new RequestBloodView();
    }

    // Opens Become a Donor screen
    public void openBeDonorView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new BeDonorView();
    }

    // Opens My Requests details screen
    public void openMyRequestView(JFrame currentFrame, BloodRequest request) {
        if (currentFrame != null) currentFrame.dispose();
        new MyRequestView(request);
    }

    // Opens Hospital/Admin Management Dashboard screen
    public void openManageInventoryView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new ManageInventoryView();
    }
}
