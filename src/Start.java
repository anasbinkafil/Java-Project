import controller.NavigationController;
import model.DataStore;

public class Start {
    public static void main(String[] args) {
        // Initialize central data store singleton
        DataStore.getInstance();

        // Launch welcome screen via NavigationController
        NavigationController.getInstance().openWelcomeView(null);
    }
}
