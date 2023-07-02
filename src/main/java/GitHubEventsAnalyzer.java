import vincode.service.EventService;

public class GitHubEventsAnalyzer {

    public static void main(String[] args) {
        EventService analyzer = new EventService();
        analyzer.run();
    }
}

