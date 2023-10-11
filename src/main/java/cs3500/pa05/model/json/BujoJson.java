package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record for storing the entire bullet journal
 *
 * @param notes the quotes and notes section
 * @param week an array of weeks containing information about tasks and events
 */
public record BujoJson(
    @JsonProperty("notes") String notes,
    @JsonProperty("week") WeekJson week,
    @JsonProperty("maxTasks") int maxTasks,
    @JsonProperty("maxEvents") int maxEvents) {
}
