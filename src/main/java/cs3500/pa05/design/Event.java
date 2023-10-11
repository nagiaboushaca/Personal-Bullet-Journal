package cs3500.pa05.design;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class that represents an event
 */
public class Event extends AbstractToDo {
  private LocalTime startTime;
  private int duration;

  /**
   * constructor with description provided
   *
   * @param name name of the event
   * @param day the day of the week it's on
   * @param description description of the event
   * @param startTime the start time of the event
   * @param duration the duration of the event in minutes
   */
  public Event(String name, DayOfWeek day, String description,
               LocalTime startTime, int duration) {
    super(name, day, description);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * get the start time of this event
   *
   * @return the start time of this event
   */
  public LocalTime getStartTime() {
    return this.startTime;
  }

  /**
   * gets the duration of this event
   *
   * @return the duration of this event
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * calculates the end time using the start time and duration
   *
   * @return the time this event ends
   */
  public LocalTime calculateEndTime() {
    return this.startTime.plusMinutes(this.duration);
  }

  /**
   * edits the start time of the event
   *
   * @param startTime the start time the user wants to edit to
   */
  public void editStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  /**
   * edits the duration of the event to the provided duration
   *
   * @param duration the duration in minutes the user wants to edit to
   */
  public void editDuration(int duration) {
    this.duration = duration;
  }

}
