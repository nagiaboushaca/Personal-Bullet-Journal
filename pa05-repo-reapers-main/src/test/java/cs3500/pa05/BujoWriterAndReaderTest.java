package cs3500.pa05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Event;
import cs3500.pa05.design.Task;
import cs3500.pa05.design.Week;
import cs3500.pa05.model.BujoReader;
import cs3500.pa05.model.BujoWriter;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.BulletJournalImpl;
import cs3500.pa05.model.Reader;
import cs3500.pa05.model.Writer;
import cs3500.pa05.model.json.JsonUtils;
import cs3500.pa05.model.json.WeekJson;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * tests the methods in the BujoWriter class
 */
public class BujoWriterAndReaderTest {
  private static BulletJournal bujo;

  /**
   * sets up mock bujo object to work with for testing
   */
  @BeforeAll
  public static void testSetup() {
    Task task1 =
        new Task("Homework1", DayOfWeek.monday, "");
    task1.markComplete();
    Task task2 =
        new Task("PA05 UML", DayOfWeek.friday, "Create UML diagram and github issues");
    final Task task3 = new Task("Apply for jobs", DayOfWeek.sunday, "");
    Event event1 =
        new Event(
            "OOD PA05 presentation",
            DayOfWeek.thursday,
            "Present PA05 in class",
            LocalTime.of(11, 40),
            100);
    Event event2 =
        new Event(
            "Zoom meeting with advisor",
            DayOfWeek.tuesday,
            "",
            LocalTime.of(14, 30),
            15);
    Event event3 =
        new Event(
            "Hang out with friends",
            DayOfWeek.saturday,
            "",
            LocalTime.of(18, 40),
            15);
    Event event4 =
        new Event(
            "Hang out with friends",
            DayOfWeek.monday,
            "",
            LocalTime.of(12, 40),
            15);
    Map<DayOfWeek, List<AbstractToDo>> dayMap = new LinkedHashMap<>();
    dayMap.put(DayOfWeek.monday, new ArrayList<>(Arrays.asList(task1, event4)));
    dayMap.put(DayOfWeek.tuesday, new ArrayList<>(Arrays.asList(event2)));
    dayMap.put(DayOfWeek.wednesday, new ArrayList<>());
    dayMap.put(DayOfWeek.thursday, new ArrayList<>(Arrays.asList(event1)));
    dayMap.put(DayOfWeek.friday, new ArrayList<>(Arrays.asList(task2)));
    dayMap.put(DayOfWeek.saturday, new ArrayList<>(Arrays.asList(event3)));
    dayMap.put(DayOfWeek.sunday, new ArrayList<>(Arrays.asList(task3)));
    Week week = new Week(dayMap);
    bujo = new BulletJournalImpl(null, week);
  }

  /**
   * tests the read and write method
   */
  @Test
  public void testWrite() {
    Path path1 = Path.of("src/test/resources/sampleOutputs/example.bujo");
    Path path2 = Path.of("src/test/resources/sampleOutputs/example2.bujo");
    Writer bujoWriter =
        new BujoWriter(this.bujo, path1);
    bujoWriter.write();
    Reader bujoReader =
        new BujoReader(path1);
    BulletJournal readBujo = bujoReader.read();
    Writer bujoWriter2 =
        new BujoWriter(readBujo, path2);
    bujoWriter2.write();
    Scanner s = null;
    try {
      s = new Scanner(path1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Scanner s2 = null;
    try {
      s2 = new Scanner(path2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // assures that the json data is the same
    // comparing bujo -> json and then back to bujo then back to json again
    assertEquals(s.nextLine(), s2.nextLine());
  }



  @Test
  public void urlTest() {
    assertTrue(isUrl("https://docs.google.com"));
    assertFalse(isUrl("dakslfjdlskfjkaljsf"));
  }

  private boolean isUrl(String url) {
    try {
      (new java.net.URL(url)).openStream().close();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}