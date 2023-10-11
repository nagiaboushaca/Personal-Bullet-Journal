package cs3500.pa05.model;

import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Event;
import cs3500.pa05.design.Task;
import cs3500.pa05.design.Week;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * implementation class for the BulletJournal
 */
public class BulletJournalImpl implements BulletJournal {
  private Path path;
  private Week week;

  private int maxTasks;
  private int taskCount;
  private int maxEvents;
  private int eventCount;

  private String notes;

  /**
   * constructor for bullet journal
   *
   * @param path the path to the particular bujo file
   * @param week the list of weeks and all details
   */
  public BulletJournalImpl(Path path, Week week) {
    this.path = path;
    this.week = week;
    this.maxTasks = 10;
    this.maxEvents = 10;
    this.eventCount = this.week.getEvents().size();
    this.taskCount = this.week.getTasks().size();
  }


  /**
   * constructor for bullet journal
   *
   * @param path the path for the .bujo file
   */
  public BulletJournalImpl(Path path) {
    Map<DayOfWeek, List<AbstractToDo>> map = new LinkedHashMap<>();
    for (int i = 0; i < 7; i++) {
      map.put(DayOfWeek.values()[i], new ArrayList<>());
    }
    this.path = path;
    this.week = new Week(map);
    this.maxTasks = 10;
    this.maxEvents = 10;
    this.eventCount = this.week.getEvents().size();
    this.taskCount = this.week.getTasks().size();
  }



  @Override
  public Week getWeek() {
    return this.week;
  }

  /**
   * sets the maximum number of tasks for this bullet journal
   *
   * @param max - the number to set as the max
   */
  public void setMaxTasks(int max) {
    this.maxTasks = max;
  }

  /**
   * sets the maximum number of events for this bullet journal
   *
   * @param max - the number to set as the max
   */
  public void setMaxEvents(int max) {
    this.maxEvents = max;
  }

  /**
   * gets the max tasks for this bullet journal
   *
   * @return the max tasks for this bullet journal
   */
  public int getMaxTasks() {
    return this.maxTasks;
  }

  /**
   * gets the current task count
   *
   * @return the current task count for this journal
   */
  public int getCurTasks(DayOfWeek day) {
    int count = 0;
    for (AbstractToDo todo : this.week.getTasks()) {
      if (todo.getDay().equals(day) && todo instanceof Task) {
        count++;
      }
    }
    return count;
  }

  /**
   * adds 1 to the task count
   */
  public void addTaskCount() {
    taskCount++;
  }

  @Override
  public int getMaxEvents() {
    return maxEvents;
  }

  @Override
  public int getCurEvents(DayOfWeek day) {
    int count = 0;
    for (AbstractToDo todo : this.week.getEvents()) {
      if (todo.getDay().equals(day) && todo instanceof Event) {
        count++;
      }
    }
    return count;
  }

  @Override
  public void addEventCount() {
    eventCount++;
  }

  @Override
  public void addTodo(AbstractToDo todo) {
    this.week.addTodo(todo);
  }

  /**
   * gets the quotes and notes string
   *
   * @return the quotes and notes string
   */
  public String getNotes() {
    return this.notes;
  }

  /**
   * sets the quotes and notes string to the given string
   *
   * @param notes the notes to set to
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

}
