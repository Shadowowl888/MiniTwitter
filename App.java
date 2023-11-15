package MiniTwitter;

/**
 * Main driver to create Admin Panel instance that applies Singleton Pattern
 */
public class App {
    public static void main(String[] args) {
        AdminPanel adminPanel = AdminPanel.getInstance();
    }
}
