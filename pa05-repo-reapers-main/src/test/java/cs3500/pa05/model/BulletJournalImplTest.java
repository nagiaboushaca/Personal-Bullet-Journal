package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Event;
import cs3500.pa05.design.Task;
import cs3500.pa05.design.Week;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test for the BulletJournalImpl class
 */
class BulletJournalImplTest {

  /**
   * tests the journalimpl
   */
  @Test
  public void testJournalImpl() {
    Map<DayOfWeek, List<AbstractToDo>> initMap = new HashMap<>();
    Task t = new Task("aaaa", DayOfWeek.monday, "aaaa");
    final Event e = new Event(
        "name",
        DayOfWeek.thursday,
        "Desc",
        LocalTime.of(8, 30),
        30);
    List<AbstractToDo> list = new ArrayList<>();
    list.add(t);
    initMap.put(DayOfWeek.monday, list);
    initMap.put(DayOfWeek.tuesday, list);
    initMap.put(DayOfWeek.wednesday, list);
    initMap.put(DayOfWeek.thursday, list);
    initMap.put(DayOfWeek.friday, list);
    initMap.put(DayOfWeek.saturday, list);
    initMap.put(DayOfWeek.sunday, list);
    Week week = new Week(initMap);
    Path p = Path.of("src/main/resources/sampleBujoFiles/sampleone.bujo");
    BulletJournalImpl journal = new BulletJournalImpl(p, week);
    BulletJournalImpl journalEmpty = new BulletJournalImpl(p);
    journal.addEventCount();
    journal.getMaxEvents();
    journal.getMaxTasks();
    journal.addTodo(t);
    journal.addTodo(e);
    journal.addTaskCount();
    assertEquals(7, journal.getCurEvents(DayOfWeek.thursday));
    assertEquals(14, journal.getCurTasks(DayOfWeek.monday));
    journal.setMaxEvents(3);
    journal.setMaxTasks(3);
    assertEquals(3, journal.getMaxEvents());
    assertEquals(3, journal.getMaxTasks());


  }

}