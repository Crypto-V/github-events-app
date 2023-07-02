package vincode.service;

import vincode.model.Event;
import vincode.util.GitHubEventFetcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Service class for fetching and displaying GitHub events.
 */
public class EventService {

    /**
     * The GitHub event fetcher.
     */
    private final GitHubEventFetcher eventFetcher;

    /**
     * Constructor.
     */
    public EventService() {
        eventFetcher = new GitHubEventFetcher();
    }

    /**
     * The GitHub event fetcher.
     * try-with-resources
     */
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the repository owner: ");
            String owner = scanner.nextLine().trim();

            System.out.print("Enter the repository name: ");
            String repo = scanner.nextLine().trim();

            List<Event> events = eventFetcher.fetchEvents(owner, repo);

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
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Displays the given events.
     *
     * @param events The events to display.
     */
    private void displayEvents(List<Event> events) {
        for (Event event : events) {
            System.out.println(event);
        }
    }

    /**
     * Displays the event statistics.
     *
     * @param events The events to display statistics for.
     */
    private void displayEventStatistics(List<Event> events) {
        Map<String, Long> eventTypeCounts = events.stream()
                .collect(Collectors.groupingBy(Event::getType, Collectors.counting()));

        eventTypeCounts.forEach((eventType, count) -> System.out.println(eventType + ": " + count));
    }

    /**
     * Displays the most active user.
     *
     * @param events The events to display the most active user for.
     */
    private void displayMostActiveUser(List<Event> events) {
        Map<String, Long> userEventCounts = events.stream()
                .filter(event -> event.getActorLogin() != null)
                .collect(Collectors.groupingBy(Event::getActorLogin, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostActiveUser = userEventCounts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        mostActiveUser.ifPresent(user -> System.out.println(user.getKey() + " (" + user.getValue() + " events)"));
    }
}


