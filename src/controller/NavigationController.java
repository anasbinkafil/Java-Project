package controller;

import model.BloodRequest;
import model.User;
import view.*;

import javax.swing.*;

public class NavigationController {
    private static NavigationController instance;
    private User currentUser;

    private NavigationController() {}

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

    public void openWelcomeView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new WelcomeView();
    }

    public void openLoginView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new LoginView();
    }

    public void openRegistrationView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new RegistrationView();
    }

    public void openSearchBloodView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new SearchBloodView();
    }

    public void openRequestBloodView(JFrame currentFrame) {
        if (currentFrame != null) currentFrame.dispose();
        new RequestBloodView();
    }

    public void openMyRequestView(JFrame currentFrame, BloodRequest request) {
        if (currentFrame != null) currentFrame.dispose();
        new MyRequestView(request);
    }
}
