package cs3500.pa05.controller;

import cs3500.pa05.design.AbstractToDo;
import cs3500.pa05.design.DayOfWeek;
import cs3500.pa05.design.Event;
import cs3500.pa05.design.Task;
import cs3500.pa05.design.Week;
import cs3500.pa05.model.BujoReader;
import cs3500.pa05.model.BujoWriter;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.Reader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.application.HostServices;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the bullet journal application
 */
public class BujoController implements Controller {
  private BulletJournal journal;
  private Reader reader;
  private Path savePath;

  @FXML
  public TextField pathField;
  @FXML
  private Button submitPathButton;
  @FXML
  private Button maxButton;
  @FXML
  private Button submitEvent;
  @FXML
  private Button submitTask;
  @FXML
  private MenuItem createNewEventButton;
  @FXML
  private MenuItem createNewTaskButton;
  @FXML
  private ProgressBar progressBar;
  @FXML
  private VBox monday;
  @FXML
  private VBox tuesday;
  @FXML
  private VBox wednesday;
  @FXML
  private VBox thursday;
  @FXML
  private VBox friday;
  @FXML
  private VBox saturday;
  @FXML
  private VBox sunday;
  @FXML
  private VBox taskQueue;
  @FXML
  private Button themeButton;
  @FXML
  private AnchorPane root;
  @FXML
  private AnchorPane rootEvent;
  @FXML
  private AnchorPane rootTask;
  @FXML
  private TextField eventName;
  @FXML
  private TextField eventDescription;
  @FXML
  private TextField eventStartHour;
  @FXML
  private TextField eventStartMinute;
  @FXML
  private TextField eventDuration;
  @FXML
  private ChoiceBox<DayOfWeek> eventDayOfWeek;
  @FXML
  private TextField taskName;
  @FXML
  private TextField taskDescription;
  @FXML
  private ChoiceBox<DayOfWeek> taskDayOfWeek;
  @FXML
  private Button continueSplash;
  @FXML
  private Button saveButton;
  @FXML
  private TextArea quotesAndNotes;
  @FXML
  private TextField maxEvents;
  @FXML
  private TextField maxTasks;
  @FXML
  private Button submitLimit;
  @FXML
  private Label icon;
  @FXML
  private TextField eventErrorDisplay;
  @FXML
  private TextField taskErrorDisplay;

  private Stage stage;
  private Dialog<String> welcomeDialog;
  private Dialog<String> splash;
  private Dialog<String> newEventDialog;
  private Dialog<String> newTaskDialog;
  private Dialog<String> maxDialog;
  private ArrayList<VBox> week;
  private Popup themePopup;
  private boolean validEvent = false;
  private boolean validTask = false;

  /**
   * constructor
   *
   * @param stage the stage for the view
   */
  public BujoController(Stage stage) {
    this.stage = stage;
    week = new ArrayList<>();
  }

  /**
   * runs the bullet journaling application
   */
  @Override
  public void run() {
    try {
      showSplash();
      showWelcomeScreen();
      initializeReader();
      getMaxToDos();
    } catch (IOException e) {
      System.err.println("Error with initializing journal");
    }
    week.add(monday);
    week.add(tuesday);
    week.add(wednesday);
    week.add(thursday);
    week.add(friday);
    week.add(saturday);
    week.add(sunday);
    initButtons();
    displayTodos();
    displayTaskQueue();
  }

  private void showSplash() throws IOException {
      splash = new Dialog<>();
      FXMLLoader loader = new FXMLLoader(
          getClass().getClassLoader().getResource("splash.fxml"));
      loader.setController(this);
      Scene s = loader.load();
      this.splash = new Dialog<>();
      this.splash.getDialogPane().setContent(s.getRoot());
      continueSplash.setOnAction(e -> this.splash.close());
      this.splash.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
      Button close = (Button) this.splash.getDialogPane().lookupButton(ButtonType.CLOSE);
      close.setOpacity(0);
      this.splash.showAndWait();
  }

  /**
   * Prompts the welcome screen and takes in a file path
   * @throws IOException if FXML doesn't load
   */
  private void showWelcomeScreen() throws IOException {
    this.welcomeDialog = new Dialog<>();
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));
    loader.setController(this);
    Scene s = loader.load();
    this.welcomeDialog.getDialogPane().setContent(s.getRoot());
    submitPathButton.setOnAction(event -> initializeReader());
    submitPathButton.setOnAction(event -> this.welcomeDialog.close());
    this.welcomeDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
    Button close = (Button) this.welcomeDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
    close.setOpacity(0);
    this.welcomeDialog.showAndWait();
  }

  /**
   * Gets maximum number of tasks and events
   * @throws IOException if FXML doesn't load
   */
  private void getMaxToDos() {
    this.maxDialog = new Dialog<>();
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("limit.fxml"));
    loader.setController(this);
    Scene s = null;
    try {
      s = loader.load();
    } catch (IOException e) {
      System.err.println("Unable to load popup for getting maximum number of events and tasks");
    }
    this.maxDialog.getDialogPane().setContent(s.getRoot());
    submitLimit.setOnAction(event -> {
      int maximumEvents = Integer.parseInt(maxEvents.getText());
      int maximumTasks = Integer.parseInt(maxTasks.getText());

      this.journal.setMaxEvents(maximumEvents);
      this.journal.setMaxTasks(maximumTasks);
      this.maxDialog.close();
    });
    this.maxDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
    Button close = (Button) this.maxDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
    close.setOpacity(0);
    this.maxDialog.showAndWait();
  }

  /**
   * Initializes the reader and journal
   */
  private void initializeReader() {
    String stringPath = pathField.getText();
    this.savePath = Path.of(stringPath);
    this.reader = new BujoReader(this.savePath);
    this.journal = this.reader.read(); // creates the bullet journal
    if (this.journal.getNotes() != null) {
      quotesAndNotes.setText(this.journal.getNotes());
    }
  }

  /**
   * Initializes the buttons for creating tasks/events and choosing theme
   */
  @FXML
  private void initButtons() {
    // initializing the create event and task buttons
    createNewEventButton.setOnAction(event -> {
      loadEventPopup();
    });

    // create task button
    createNewTaskButton.setOnAction(event -> {
      loadTaskPopup();
    });

    // initializing the theme button and shows popup
    themeButton.setOnAction(event -> {
      new ThemeSetter(this.root, this.icon, this.stage).chooseTheme();
    });

    // initializes the save button
    saveButton.setOnAction(event -> save());

    // initializes the max button for setting max number of events and tasks
    maxButton.setOnAction(event -> getMaxToDos());
  }

  /**
   * Creates popup for creating new event
   */
  private void loadEventPopup() {
    Scene s = null;
    try {
      this.newEventDialog = new Dialog<>();
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("event.fxml"));
      loader.setController(this);
      s = loader.load();
      this.newEventDialog.getDialogPane().setContent(s.getRoot());
      this.newEventDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
    } catch (IOException e) {
      System.err.println("Error loading create event popup");
    }

    this.eventDayOfWeek.getItems().addAll(DayOfWeek.values());

    submitEvent.setOnAction(event -> {
      createNewEvent();
      displayTodos();

      if (validEvent) {
        this.newEventDialog.close();
      }
    });

    this.newEventDialog.showAndWait();
  }


  /**
   * Creates popup for creating new task
   */
  private void loadTaskPopup() {
    Scene s = null;
    try {
      this.newTaskDialog = new Dialog<>();
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("task.fxml"));
      loader.setController(this);
      s = loader.load();
      this.newTaskDialog.getDialogPane().setContent(s.getRoot());
      this.newTaskDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
    } catch (IOException e) {
      System.err.println("Error loading create task popup");
    }

    this.taskDayOfWeek.getItems().addAll(DayOfWeek.values());

    submitTask.setOnAction(event -> {
      createNewTask();
      displayTodos();
      displayTaskQueue();

      if (validTask) {
        this.newTaskDialog.close();
      }
    });

    this.newTaskDialog.showAndWait();
  }


  /**
   * Saves the contents of this bullet journal and overwrites it to the given path
   */
  private void save() {
    this.journal.setNotes(quotesAndNotes.getText());
    BujoWriter bujoWriter = new BujoWriter(this.journal, this.savePath);
    bujoWriter.write();
  }

  /**
   * Creates a new task
   */
  private void createNewTask() {
    validTask = false;
    DayOfWeek taskDayOfWeekInput = this.taskDayOfWeek.getValue();

    if (taskDayOfWeekInput == null) {
      this.taskErrorDisplay.setText("Must select day of week");
      return;
    }

    int max = journal.getMaxTasks();
    int cur = journal.getCurTasks(taskDayOfWeekInput);
    if (max <= cur) {
      this.taskErrorDisplay.setText("You cannot create more than " + max + "tasks");
      return;
    }
    journal.addTaskCount();

    String taskNameInput = this.taskName.getText();
    if (taskNameInput.equals("")) {
      this.taskErrorDisplay.setText("Must have a name");
      return;
    }
    String taskDescriptionInput = this.taskDescription.getText();


    AbstractToDo task = new Task(taskNameInput, taskDayOfWeekInput, taskDescriptionInput);


    this.journal.addTodo(task);
    validTask = true;
  }

  /**
   * Creates a new event
   */
  private void createNewEvent() {
    validEvent = false;
    DayOfWeek eventDayOfWeekInput = this.eventDayOfWeek.getValue();

    if (eventDayOfWeekInput == null) {
      this.eventErrorDisplay.setText("Must select day of week");
      return;
    }

    int max = journal.getMaxEvents();
    int cur = journal.getCurEvents(eventDayOfWeekInput);
    if (max <= cur) {
      this.eventErrorDisplay.setText("You cannot create more than " + max + "event");
      return;
    }
    journal.addEventCount();

    String eventNameInput = this.eventName.getText();
    if (eventNameInput.equals("")) {
      this.eventErrorDisplay.setText("Must have a name");
      return;
    }
    String eventDescriptionInput = this.eventDescription.getText();
    int hour = Integer.parseInt(this.eventStartHour.getText());
    int minute = Integer.parseInt(this.eventStartMinute.getText());
    if (!(hour >= 0 && hour <= 23) && !(minute >= 0 && minute <=59)) {
      this.eventErrorDisplay.setText("Invalid time");
      return;
    }
    LocalTime eventStartTimeInput =
        LocalTime.of(
            hour,
            minute);
    int eventDurationInput = Integer.parseInt(this.eventDuration.getText());

    AbstractToDo event = new Event(
        eventNameInput,
        eventDayOfWeekInput,
        eventDescriptionInput,
        eventStartTimeInput,
        eventDurationInput);
    this.journal.addTodo(event);
    validEvent = true;
  }

  /**
   * displays the todos on screen by adding hboxes for each event or task
   */
  private void displayTodos() {
      for (int i = 0; i < this.week.size(); i++) {
        this.week.get(i).getChildren().clear();
        this.week.get(i).getChildren().add(
            new Label("Tasks left: "
                + ProgressBarCalculator.getNumRemainingTasks(
                    DayOfWeek.values()[i], this.journal.getWeek())));
        this.week.get(i).getChildren().add(
            new ProgressBar(ProgressBarCalculator.getBarValue(
                DayOfWeek.values()[i], this.journal.getWeek())));
        for (AbstractToDo todo : this.journal.getWeek().getToDoByDay().get(DayOfWeek.values()[i])) {
          VBox vBox = new VBox();
          HBox hBox = new HBox();
          Button deleteButton = new Button("x");
          Button editButton = new Button("e");
          vBox.getChildren().add(hBox);
          hBox.getChildren().add(deleteButton);
          hBox.getChildren().add(editButton);

          deleteButton.setOnAction(event -> {
            this.journal.getWeek().removeTodo(todo);
            displayTodos();
            displayTaskQueue();
          });

          editButton.setOnAction(event -> {
            editToDo(todo);
            displayTodos();
            displayTaskQueue();
          });

          vBox.setBorder(
              new Border(
                  new BorderStroke(
                      Color.GRAY,
                      BorderStrokeStyle.DASHED,
                      CornerRadii.EMPTY,
                      new BorderWidths(1))));
          Label nameLabel = new Label(todo.getName() + "\n");
          nameLabel.setWrapText(true);
          vBox.getChildren().add(nameLabel);

          Label descLabel = new Label("Desc: " + todo.getDescription() + "\n");
          descLabel.setWrapText(true);
          vBox.getChildren().add(descLabel);
          Hyperlink hyperlink = HyperlinkHandler.findLink(todo);
          if (hyperlink != null) {
            descLabel.setText(HyperlinkHandler.removeLinkDescription(todo.getDescription()));
            hyperlink.setWrapText(true);
            vBox.getChildren().add(hyperlink);
          }
          if (todo instanceof Event) {
            Label startTimeLabel =
                new Label("Start: " + ((Event) todo).getStartTime().toString() + "\n");
            startTimeLabel.setWrapText(true);
            vBox.getChildren().add(startTimeLabel);

            Label durationLabel =
                new Label(((Event) todo).getDuration() + " minutes\n");
            durationLabel.setWrapText(true);
            vBox.getChildren().add(durationLabel);
          } else if (todo instanceof Task) {
            String dispComplete = null;
            if (((Task) todo).isComplete()) {
              dispComplete = "Status: Complete!\n";
            } else {
              dispComplete = "Status: In progress\n";
            }
            Label completeLabel =
                new Label(dispComplete);
            completeLabel.setWrapText(true);
            vBox.getChildren().add(completeLabel);
          }

          this.week.get(i).getChildren().add(vBox);
        }
      }
  }

  private void editToDo(AbstractToDo todo) {
    if (todo instanceof Task) {
      Scene s = null;
      try {
        this.newTaskDialog = new Dialog<>();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("task.fxml"));
        loader.setController(this);
        s = loader.load();
        taskName.setText(todo.getName());
        taskDescription.setText(todo.getDescription());
        this.newTaskDialog.getDialogPane().setContent(s.getRoot());
        this.newTaskDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
      } catch (IOException e) {
        System.err.println("Error loading create task popup");
      }

      this.taskDayOfWeek.getItems().addAll(DayOfWeek.values());

      submitTask.setOnAction(event -> {
        createNewTask();
        this.journal.getWeek().removeTodo(todo);
        displayTodos();
        displayTaskQueue();
        this.newTaskDialog.close();
      });

      this.newTaskDialog.showAndWait();
    }
    else if (todo instanceof Event) {
      Scene s = null;
      try {
        this.newEventDialog = new Dialog<>();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("event.fxml"));
        loader.setController(this);
        s = loader.load();
        eventName.setText(todo.getName());
        eventDescription.setText(todo.getDescription());
        eventStartHour.setText(Integer.toString(((Event) todo).getStartTime().getHour()));
        eventStartMinute.setText(Integer.toString(((Event) todo).getStartTime().getMinute()));
        eventDuration.setText(Integer.toString(((Event) todo).getDuration()));
        this.newEventDialog.getDialogPane().setContent(s.getRoot());
        this.newEventDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
      } catch (IOException e) {
        System.err.println("Error loading create event popup");
      }

      this.eventDayOfWeek.getItems().addAll(DayOfWeek.values());

      submitEvent.setOnAction(event -> {
        createNewEvent();
        this.journal.getWeek().removeTodo(todo);
        displayTodos();
        this.newEventDialog.close();
      });

      this.newEventDialog.showAndWait();
    }
  }

  /**
   * displays the task Queue in the sidebar
   */
  private void displayTaskQueue() {
    this.taskQueue.getChildren().clear();
    for (Task t : this.journal.getWeek().getTasks()) {
      VBox vBox = new VBox();
      vBox.setBorder(
          new Border(
              new BorderStroke(
                  Color.GRAY,
                  BorderStrokeStyle.DASHED,
                  CornerRadii.EMPTY,
                  new BorderWidths(1))));
      Label taskName = new Label(t.getName());
      String taskStatusString = null;

      if (t.isComplete()) {
        taskStatusString = "Status: Complete!";
      } else {
        taskStatusString = "Status: In progress";
      }

      Label taskStatus = new Label(taskStatusString);

      vBox.getChildren().add(taskName);
      vBox.getChildren().add(taskStatus);
      Button markCompletionStatus = null;
      if (!t.isComplete()) {
        markCompletionStatus = new Button("Mark Done");
        vBox.getChildren().add(markCompletionStatus);

        markCompletionStatus.setOnAction(event -> {
          t.markComplete();
          displayTodos();
          displayTaskQueue();
        });
      }

      this.taskQueue.getChildren().add(vBox);
    }
  }
}
