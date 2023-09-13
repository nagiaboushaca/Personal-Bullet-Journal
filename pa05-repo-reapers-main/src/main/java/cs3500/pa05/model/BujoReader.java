package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * class to read a .bujo file and convert it into a BulletJournal object
 */
public class BujoReader implements Reader {
  private Path path;
  private final ObjectMapper mapper = new ObjectMapper();
  private Scanner scanner;

  /**
   * constructor
   *
   * @param path path to the .bujo to be read
   */
  public BujoReader(Path path) {
    this.path = path;
    this.scanner = null;
  }

  /**
   * returns an IBulletJournal object created from the contents of the .bujo file
   *
   * @return an IBulletJournal object
   */
  public BulletJournal read() {
    Appendable output = new StringBuilder();

    try {
      this.scanner = new Scanner(this.path);
    } catch (IOException e) {
      System.out.println("Error scanning .bujo file, or filepath is incorrect");
      System.out.println(e.getMessage());
    }

    while (this.scanner.hasNextLine()) {
      try {
        output.append(this.scanner.nextLine());
      } catch (IOException e) {
        System.out.println("Error while appending scanned file contents");
        System.out.println(e.getMessage());
      }
    }

    JsonNode jsonNode = null;

    try {
      jsonNode = this.mapper.readTree(output.toString());
    } catch (JsonProcessingException e) {
      System.out.println("Error while parsing json string in .bujo file");
      System.out.println(e.getMessage());
    }

    BujoJson bujoJson = this.mapper.convertValue(jsonNode, BujoJson.class);

    WeekJson weekJson = bujoJson.week();

    Week week = constructWeek(weekJson);

    BulletJournal bujo = new BulletJournalImpl(this.path, week);
    bujo.setNotes(bujoJson.notes());
    bujo.setMaxTasks(bujoJson.maxTasks());
    bujo.setMaxEvents(bujo.getMaxEvents());
    return bujo;
  }

  /**
   * constructs a week object from its json counterpart
   *
   * @param weekJson the json object for the week
   * @return the week object
   */
  private Week constructWeek(WeekJson weekJson) {
    DayMapJson dayMapJson = weekJson.layoutPerDay();
    JsonNode node = JsonUtils.serializeRecord(dayMapJson);
    Map<DayOfWeek, List<AbstractToDo>> dayMap = new LinkedHashMap<>();

    int count = 0;
    for (int i = 0; i < 7; i++) {
      DayOfWeek day = DayOfWeek.values()[i];
      AbstractToDoJson[] todos =
          this.mapper.convertValue(node.get(day.toString()), AbstractToDoJson[].class);

      List<AbstractToDo> todoList = new ArrayList<>();
      for (AbstractToDoJson todo : todos) {
        todoList.add(constructTodo(todo));
      }
      dayMap.put(day, todoList);
    }

    Week weekOutput = new Week(dayMap);
    return weekOutput;
  }

  /**
   * constructs an event or task from the given AbstractToDoJson
   *
   * @param todo the json AbstractToDo object
   * @return the event or task as an AbstractToDo object
   */
  private AbstractToDo constructTodo(AbstractToDoJson todo) {
    String eventOrTask = todo.eventOrTask();
    String name = todo.name();
    String description = todo.description();
    DayOfWeek day = DayOfWeek.valueOf(todo.day());
    boolean isComplete = todo.complete();
    String startTime = todo.startTime();
    int duration = todo.duration();

    if (eventOrTask.equals("event")) {
      Event event = new Event(name, day, description, LocalTime.parse(startTime), duration);
      return event;
    } else {
      Task task = new Task(name, day, description);
      if (isComplete) {
        task.markComplete();
      }
      return task;
    }
  }
}
