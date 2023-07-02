package vincode.util;

import vincode.model.Event;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static vincode.service.EventService.*;

public class DisplayEvent {

    public static String owner = "";

    public static void displayData() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the repository owner: ");
            owner = scanner.nextLine().trim();

            System.out.print("Enter the repository name: ");
            String repo = scanner.nextLine().trim();

            List<Event> events = fetchEvents(owner, repo);

            if (events.isEmpty()) {
                System.out.println("No events found for the repository.");
                return;
            }

            System.out.println("=== Latest Events ===");
            displayEvents(events);

            System.out.println("=== Event Statistics ===");
            displayEventStatistics(events);

            System.out.println("=== Most Active User ===");
            displayMostActiveUser(events);

        } catch (IOException e) {
            System.out.println("Error occurred while fetching events: " + e.getMessage());
        }
    }

}
