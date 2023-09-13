package cs3500.pa05.model;

import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Week;
import java.util.List;

/**
 * interface for a BulletJournal
 */
public interface BulletJournal {

  Week getWeek();

  int getMaxTasks();

  /**
   * @return the current tasks for this journal
   */
  int getCurTasks(DayOfWeek day);

  /**
   * adds 1 to the task count
   */
  void addTaskCount();

  /**
   * @return gets the max events
   */
  int getMaxEvents();

  /**
   * @return gets the current events
   */
  int getCurEvents(DayOfWeek day);

  /**
   * gets the event count
   */
  void addEventCount();

  /**
   * adds the given event or task to this bujo
   *
   * @param todo the todo to add
   */
  void addTodo(AbstractToDo todo);

  /**
   * gets the quotes and notes string
   *
   * @return the string in the notes section
   */
  String getNotes();

  /**
   * sets the string in the quotes and notes section to the given string
   *
   * @param notes the string to set the notes to
   */
  void setNotes(String notes);

  /**
   * sets the maximum number of tasks for this bullet journal
   *
   * @param max - the number to set as the max
   */
  void setMaxTasks(int max);

  /**
   * sets the maximum number of events for this bullet journal
   *
   * @param max - the number to set as the max
   */
  void setMaxEvents(int max);
}
