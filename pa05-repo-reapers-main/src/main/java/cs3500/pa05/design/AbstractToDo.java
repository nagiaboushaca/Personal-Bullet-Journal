package cs3500.pa05.design;

/**
 * Abstracted behavior for an event or a task
 */
public abstract class AbstractToDo {
  private String name;
  private String description;
  private DayOfWeek day;

  /**
   * constructor with description provided
   *
   * @param name the name of the event or task
   * @param day the day of the week it's on
   * @param description the description of the event or task
   */
  public AbstractToDo(String name, DayOfWeek day, String description) {
    this.name = name;
    this.day = day;
    this.description = description;
  }

  /**
   * gets the name of the event or task
   *
   * @return the name of the event or task
   */
  public String getName() {
    return name;
  }

  /**
   * gets the description of this event or task
   *
   * @return the description of this event or task
   */
  public String getDescription() {
    return description;
  }


  /**
   * edits the name of the event or task
   *
   * @param name the name to change to
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * edits the description of the event or task
   *
   * @param description the description to change to
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * edits the day of the week the event or task is on
   *
   * @param day the day of week to change to
   */
  public void setDayOfWeek(DayOfWeek day) {
    this.day = day;
  }

  /**
   * gets day of week of the event or task
   *
   * @return the day of week of the event or task
   */
  public DayOfWeek getDay() {
    return this.day;
  }
}
