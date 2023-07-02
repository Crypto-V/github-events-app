# GitHub Events API Analyzer

The objective of this assignment is to get a sense of your design and
coding approach to a problem, what you pay attention to – basically your
thought process to software development. Remember to comment your
code thoroughly, explaining your rationale for any important decisions and
the functionality of each section of the code. Feel free to use any
programming language of your choice to complete this task. You may use
any external libraries or packages that you find necessary.

# Requirements:

Utilize the GitHub Events API to retrieve events data.
You can refer to https://api.github.com/events and 
[this](https://docs.github.com/en/webhooks-and-events/webhooks/webhook-events-and-payloads) 
documentation to understand GitHub events

## Implement the following features:
- Fetch and display the latest events for a given GitHub repository.
- Implement filtering options to display specific types of events (e.g., only display "PushEvent" or "PullRequestEvent").
- Calculate and display statistics on the number of events per type.
- Identify the most active GitHub user based on the number of events.
- Implement error handling for API requests and display appropriate error messages.
- Use pagination to handle a large number of events and enable the retrieval of more events beyond the initial limit.
- Implement sorting options to display events in chronological or reverse-chronological order.
- A command-line interface (CLI) for the user to interact with your program.
- Write unit tests to verify the functionality of your program.