package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;

/**
 * view class for the BulletJournal application
 */
public class BujoView implements View {
  private FXMLLoader loader;

  /**
   * constructor for the view
   *
   * @param controller the controller for interactivity
   */
  public BujoView(Controller controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("bujoBoard.fxml"));
    this.loader.setController(controller);
  }

  /**
   * loads the scene using the loader
   *
   * @return loaded Scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
