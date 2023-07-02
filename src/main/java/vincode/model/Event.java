package vincode.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    /**
     * The type of the event.
     */
    private String type;
    /**
     * The login of the actor.
     */
    private String actorLogin;

    /**
     * overridden toString method.
     *
     * @return String
     * The string representation of the event.
     * @see Object#toString()
     * @see lombok.ToString
     */
    @Override
    public String toString() {
        return "Event{" + "type='" + type + '\'' + ", actorLogin='" + (actorLogin != null ? getActorLogin() : "null") + '\'' + '}';
    }
}
