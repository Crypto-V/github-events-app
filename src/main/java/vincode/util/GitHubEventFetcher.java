package vincode.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import vincode.model.Event;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fetches GitHub events from the GitHub API.
 */
public class GitHubEventFetcher {

    /**
     * The GitHub events API URL.
     */
    private static final String EVENTS_API_URL = "https://api.github.com/events";

    /**
     * The number of events per page.
     */
    private static final int PAGE_SIZE = 15;  // Number of events per page

    /**
     * The object mapper.
     */
    private final ObjectMapper objectMapper;

    /**
     * Constructor.
     */
    public GitHubEventFetcher() {
        objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    /**
     * Fetches events from the GitHub API.
     *
     * @param owner The repository owner.
     * @param repo  The repository name.
     * @return The list of events.
     * @throws IOException If an error occurs while fetching the events.
     */
    public List<Event> fetchEvents(String owner, String repo) throws IOException {
        List<Event> events = new ArrayList<>();
        int page = 1;

        while (true) {
            String apiUrl = EVENTS_API_URL + "?page=" + page + "&per_page=" + PAGE_SIZE;
            apiUrl += "&repo=" + owner + "/" + repo;

            Event[] pageEvents = fetchEventsFromUrl(apiUrl, owner);
            events.addAll(Arrays.asList(pageEvents));

            if (pageEvents.length < PAGE_SIZE)
                break;

            page++;
        }

        return events;
    }

    /**
     * Fetches events from the GitHub API.
     *
     * @param apiUrl The API URL.
     * @param owner  The repository owner.
     * @return The list of events.
     * @throws IOException If an error occurs while fetching the events.
     */
    private Event[] fetchEventsFromUrl(String apiUrl, String owner) throws IOException {
        try {
            Event[] events = objectMapper.readValue(new URL(apiUrl), Event[].class);

            for (Event event : events) {
                if (event.getActorLogin() == null) {
                    event.setActorLogin(owner); // Set owner as actor login
                }
            }

            return events;
        } catch (MismatchedInputException e) {
            throw new IOException("Error parsing API response: " + e.getMessage());
        }
    }
}
