package cs3500.pa05;

import cs3500.pa05.controller.BujoController;
import cs3500.pa05.controller.Controller;
import cs3500.pa05.view.BujoView;
import cs3500.pa05.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main entry point for the application
 */
public class Driver extends Application {

  /**
   * starts the Bullet Journaling application
   *
   * @param stage the primary stage for this application, onto which
   *        the application scene can be set.
   *        Applications may create other stages, if needed, but they will not be
   *        primary stage
   */
  @Override
  public void start(Stage stage) {
    Controller controller = new BujoController(stage);
    View view = new BujoView(controller);

    try {
      stage.setScene(view.load());
      stage.setTitle("RepoReaper's Bullet Journal");
      controller.run();

      stage.show();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  /**
   * main method taking in command line arguments
   *
   * @param args any command line arguments
   */
  public static void main(String[] args) {
    launch();
  }
}
