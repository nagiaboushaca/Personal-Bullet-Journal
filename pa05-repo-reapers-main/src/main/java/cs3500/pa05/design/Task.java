package cs3500.pa05.design;

/**
 * Class that represents a task
 */
public class Task extends AbstractToDo {
  private boolean isComplete = false;

  /**
   * constructor with description provided
   *
   * @param name name of the task
   * @param day the day of the week it's on
   * @param description the description of the task
   */
  public Task(String name, DayOfWeek day, String description) {
    super(name, day, description);
  }

  /**
   * returns true if the task is complete
   *
   * @return true if the task is complete
   */
  public boolean isComplete() {
    return this.isComplete;
  }

  /**
   * marks this task complete
   */
  public void markComplete() {
    this.isComplete = true;
  }

}
