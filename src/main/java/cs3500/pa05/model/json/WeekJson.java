package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record for storing information about the week's events/tasks
 *
 * @param layoutPerDay map for events/tasks for each day of the week
 */
public record WeekJson(
    @JsonProperty("layoutPerDay") DayMapJson layoutPerDay) {
}
