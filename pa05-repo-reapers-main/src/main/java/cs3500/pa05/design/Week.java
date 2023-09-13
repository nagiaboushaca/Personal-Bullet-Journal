package cs3500.pa05.design;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * class representing a week's worth of tasks and events
 */
public class Week {
  private Map<DayOfWeek, List<AbstractToDo>> toDoByDay;

  /**
   * constructor for a week
   *
   * @param toDoByDay the map of events/tasks separated by each day
   */
  public Week(Map<DayOfWeek, List<AbstractToDo>> toDoByDay) {
    this.toDoByDay = toDoByDay;
  }

  /**
   * gets the list of tasks for this week
   *
   * @return the list of tasks for this week
   */
  public List<Task> getTasks() {
    List<Task> tasks = new ArrayList<>();

    for (int i = 0; i < 7; i++) {
      for (AbstractToDo todo : this.toDoByDay.get(DayOfWeek.values()[i])) {
        if (todo instanceof Task) {
          tasks.add((Task) todo);
        }
      }
    }

    return tasks;
  }

  /**
   * gets the list of events for this week
   *
   * @return the list of events for this week
   */
  public List<Event> getEvents() {
    List<Event> events = new ArrayList<>();

    for (int i = 0; i < 7; i++) {
      for (AbstractToDo todo : this.toDoByDay.get(DayOfWeek.values()[i])) {
        if (todo instanceof Event) {
          events.add((Event) todo);
        }
      }
    }

    return events;
  }

  /**
   * gets the map for list of todos by day
   *
   * @return the list of todos by day
   */
  public Map<DayOfWeek, List<AbstractToDo>> getToDoByDay() {
    return this.toDoByDay;
  }

  /**
   * adds the given event or task to this list by day
   *
   * @param todo the event or task to add
   */
  public void addTodo(AbstractToDo todo) {
    this.toDoByDay.get(todo.getDay()).add(todo);
  }

  /**
   * removes the given event or task from the list
   *
   * @param todo the event or task to remove
   */
  public void removeTodo(AbstractToDo todo) {
    this.toDoByDay.get(todo.getDay()).remove(todo);
  }

}
