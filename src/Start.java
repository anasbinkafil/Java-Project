import controller.NavigationController;
import model.DataStore;

/**
 * Application entry point for the Blood Bank Management System.
 */
public class Start {

    public static void main(String[] args) {
        // Initialize data store with default demo data
        DataStore.getInstance();

        // Launch landing screen
        NavigationController.getInstance().openWelcomeView(null);
    }
}
