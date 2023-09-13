package cs3500.pa05.model.json;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record for storing information about an event or task
 *
 * @param eventOrTask "event" or "task" depending on which
 * @param name name of the event or task
 * @param description description of the event or task; may be empty
 * @param day day of the week the event or task occurs on
 * @param complete true if the task is complete, empty if is an event
 * @param startTime the start time of an event; empty if it is a task
 * @param duration the duration of an event; empty if it is a task
 */
public record AbstractToDoJson(
    @JsonProperty("eventOrTask") String eventOrTask,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day") String day,
    @JsonProperty("complete") boolean complete,
    @JsonProperty("startTime") String startTime,
    @JsonProperty("duration") int duration) {
}
