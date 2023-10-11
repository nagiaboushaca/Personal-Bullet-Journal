package cs3500.pa05.controller;

import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Task;
import cs3500.pa05.design.Week;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class to do all calculations related to the progress bar
 */
public class ProgressBarCalculator {

  /**
   * gets the double value for the progress bar
   *
   * @param day the day of the week to check
   * @param week the week to check
   * @return the double value to represent how much of the progress bar is filled
   */
  public static double getBarValue(DayOfWeek day, Week week) {
    Map<DayOfWeek, List<AbstractToDo>> dayMap = week.getToDoByDay();
    List<AbstractToDo> completedTasks = new ArrayList<>();
    List<AbstractToDo> allTasks = new ArrayList<>();

    for (AbstractToDo todo : dayMap.get(day)) {
      if (todo instanceof Task) {
        allTasks.add(todo);
        if (((Task) todo).isComplete()) {
          completedTasks.add(todo);
        }
      }
    }

    return (double) completedTasks.size() / allTasks.size();
  }


  /**
   * gets the number of remaining tasks for the specified day of the given week
   *
   * @param day the day to check
   * @param week the week to check from
   * @return the number of remaining tasks
   */
  public static int getNumRemainingTasks(DayOfWeek day, Week week) {
    Map<DayOfWeek, List<AbstractToDo>> dayMap = week.getToDoByDay();
    List<AbstractToDo> completedTasks = new ArrayList<>();
    List<AbstractToDo> allTasks = new ArrayList<>();

    for (AbstractToDo todo : dayMap.get(day)) {
      if (todo instanceof Task) {
        allTasks.add(todo);
        if (((Task) todo).isComplete()) {
          completedTasks.add(todo);
        }
      }
    }
    return allTasks.size() - completedTasks.size();
  }
}
