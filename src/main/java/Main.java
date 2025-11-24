import controller.UrlController;
import service.UrlService;
import store.DatabaseStore;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseStore store = new DatabaseStore(
                "jdbc:postgresql://localhost:5432/tinyurl_db",
                "tinyuser",
                "tinypass"
        );
        UrlService service = new UrlService(store);
        UrlController controller = new UrlController(service);

        Scanner scanner = new Scanner(System.in);

        System.out.println("TinyURL In-Memory Application");
        System.out.println("Commands:");
        System.out.println("  shorten <url>");
        System.out.println("  expand <code>");
        System.out.println("  exit");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);

            if (parts.length == 0) continue;

            switch (parts[0].toLowerCase()) {

                case "shorten":
                    if (parts.length < 2) {
                        System.out.println("Usage: shorten <url>");
                    } else {
                        System.out.println(controller.createShortUrl(parts[1]));
                    }
                    break;

                case "expand":
                    if (parts.length < 2) {
                        System.out.println("Usage: expand <shortCode>");
                    } else {
                        System.out.println(controller.getOriginalUrl(parts[1]));
                    }
                    break;

                case "exit":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
