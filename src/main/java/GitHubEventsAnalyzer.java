package vincode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GitHubEventsAnalyzer {
    private static final String EVENTS_API_URL = "https://api.github.com/events";
    private static final int PAGE_SIZE = 30;  // Number of events per page

    private static ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the repository owner: ");
            String owner = scanner.nextLine().trim();

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

    private static List<Event> fetchEvents(String owner, String repo) throws IOException {
        List<Event> events = new ArrayList<>();
        int page = 1;

        while (true) {
            String apiUrl = EVENTS_API_URL + "?page=" + page + "&per_page=" + PAGE_SIZE;
            apiUrl += "&repo=" + owner + "/" + repo;

            Event[] pageEvents = objectMapper.readValue(new URL(apiUrl), Event[].class);
            events.addAll(Arrays.asList(pageEvents));

            if (pageEvents.length < PAGE_SIZE)
                break;

            page++;
        }

        return events;
    }

    private static void displayEvents(List<Event> events) {
        for (Event event : events) {
            System.out.println(event);
        }
    }

    private static void displayEventStatistics(List<Event> events) {
        Map<String, Long> eventTypeCounts = events.stream()
                .collect(Collectors.groupingBy(Event::getType, Collectors.counting()));

        eventTypeCounts.forEach((eventType, count) ->
                System.out.println(eventType + ": " + count));
    }

    private static void displayMostActiveUser(List<Event> events) {
        Map<String, Long> userEventCounts = events.stream()
                .collect(Collectors.groupingBy(Event::getActorLogin, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostActiveUser = userEventCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        mostActiveUser.ifPresent(user ->
                System.out.println(user.getKey() + " (" + user.getValue() + " events)"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Event {
        private String type;
        private String actorLogin;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getActorLogin() {
            return actorLogin;
        }

        public void setActorLogin(String actorLogin) {
            this.actorLogin = actorLogin;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "type='" + type + '\'' +
                    ", actorLogin='" + actorLogin + '\'' +
                    '}';
        }
    }
}

