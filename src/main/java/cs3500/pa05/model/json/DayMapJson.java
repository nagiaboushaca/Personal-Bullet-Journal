package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record for storing array of tasks and events per day
 *
 * @param monday the array of tasks/events on Monday
 * @param tuesday the array of tasks/events on Tuesday
 * @param wednesday the array of tasks/events on Wednesday
 * @param thursday the array of tasks/events on Thursday
 * @param friday the array of tasks/events on Friday
 * @param saturday the array of tasks/events on Saturday
 * @param sunday the array of tasks/events on Sunday
 */
public record DayMapJson(
    @JsonProperty("monday") AbstractToDoJson[] monday,
    @JsonProperty("tuesday") AbstractToDoJson[] tuesday,
    @JsonProperty("wednesday") AbstractToDoJson[] wednesday,
    @JsonProperty("thursday") AbstractToDoJson[] thursday,
    @JsonProperty("friday") AbstractToDoJson[] friday,
    @JsonProperty("saturday") AbstractToDoJson[] saturday,
    @JsonProperty("sunday") AbstractToDoJson[] sunday) {
}
