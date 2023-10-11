package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Event;
import cs3500.pa05.design.Task;
import cs3500.pa05.design.Week;
import cs3500.pa05.model.json.AbstractToDoJson;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.DayMapJson;
import cs3500.pa05.model.json.JsonUtils;
import cs3500.pa05.model.json.WeekJson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * class to write a BulletJournal object into JSON and then into a .bujo file
 */
public class BujoWriter implements Writer {
  private BulletJournal bujo;
  private Path path;

  /**
   * constructor
   *
   * @param bujo the BulletJournal object to write
   * @param path the path to write the contents to
   */
  public BujoWriter(BulletJournal bujo, Path path) {
    this.bujo = bujo;
    this.path = path;
  }

  /**
   * writes the bullet journal to a .bujo path
   */
  public void write() {
    JsonNode jsonNode = JsonUtils.serializeRecord(jsonData());

    String contents = jsonNode.toString();

    byte[] data = contents.getBytes();

    try {
      Files.write(this.path, data);
    } catch (IOException e) {
      System.out.println("Error occurred while writing to .bujo file");
      System.out.println(e.getMessage());
    }
  }

  /**
   * creates a BujoJson object from the IBulletJournal object
   *
   * @return the BujoJson object corresponding to the IBulletJournal object
   */
  private BujoJson jsonData() {

    List<WeekJson> weekJsonList = new ArrayList<>();
    WeekJson weekJson = convertToWeekJson(this.bujo.getWeek());

    return
        new BujoJson(this.bujo.getNotes(), weekJson, this.bujo.getMaxTasks(),
            this.bujo.getMaxEvents());
  }

  /**
   * converts this week to a week Json object
   *
   * @param week the week object to convert
   * @return the week object as a json form
   */
  private WeekJson convertToWeekJson(Week week) {
    List<String> categories = new ArrayList<>();
    Map<DayOfWeek, List<AbstractToDoJson>> dayMap = new LinkedHashMap<>();

    for (Map.Entry<DayOfWeek, List<AbstractToDo>> entry : week.getToDoByDay().entrySet()) {
      List<AbstractToDoJson> abstractToDoJsonList = new ArrayList<>();

      for (AbstractToDo todo : entry.getValue()) {
        String eventOrTask = null;
        boolean isComplete = false;
        String startTime = null;
        int duration = 0;

        if (todo instanceof Event) {
          eventOrTask = "event";
          startTime = ((Event) todo).getStartTime().toString();
          duration = ((Event) todo).getDuration();
        } else if (todo instanceof Task) {
          eventOrTask = "task";
          isComplete = ((Task) todo).isComplete();
        }

        AbstractToDoJson toDoJson =
            new AbstractToDoJson(
                eventOrTask,
                todo.getName(),
                todo.getDescription(),
                entry.getKey().toString(),
                isComplete,
                startTime,
                duration);

        abstractToDoJsonList.add(toDoJson);
      }
      dayMap.put(entry.getKey(), abstractToDoJsonList);
    }

    DayMapJson dayMapJson =
        new DayMapJson(
            dayMap.get(DayOfWeek.monday).toArray(AbstractToDoJson[]::new),
            dayMap.get(DayOfWeek.tuesday).toArray(AbstractToDoJson[]::new),
            dayMap.get(DayOfWeek.wednesday).toArray(AbstractToDoJson[]::new),
            dayMap.get(DayOfWeek.thursday).toArray(AbstractToDoJson[]::new),
            dayMap.get(DayOfWeek.friday).toArray(AbstractToDoJson[]::new),
            dayMap.get(DayOfWeek.saturday).toArray(AbstractToDoJson[]::new),
            dayMap.get(DayOfWeek.sunday).toArray(AbstractToDoJson[]::new));

    return new WeekJson(dayMapJson);
  }

}
