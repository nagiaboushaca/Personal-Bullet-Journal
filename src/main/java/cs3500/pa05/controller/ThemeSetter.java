package cs3500.pa05.controller;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * class to hold all methods and popups for changing themes
 */
public class ThemeSetter {
  @FXML
  private Button darkThemeButton;
  @FXML
  private Button lightThemeButton;
  @FXML
  private Button sakuraThemeButton;
  private Popup themePopup;
  private Label icon;
  private Stage stage;
  private AnchorPane root;

  /**
   * constructor
   *
   * @param root - root anchorpane of board
   * @param icon - icon of the theme
   * @param stage - the stage to place
   */
  public ThemeSetter(AnchorPane root, Label icon, Stage stage) {
    this.root = root;
    this.icon = icon;
    this.stage = stage;
  }

  /**
   * Creates popup for choosing theme
   */
  public void chooseTheme() {

    // setting up the popup
    this.themePopup = new Popup();
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("theme.fxml"));
    loader.setController(this);
    Scene s = null;
    try {
      s = loader.load();
      themePopup.getContent().add(s.getRoot());
    } catch (IOException e) {
      System.err.println("Unable to load theme scene");
    }

    darkThemeButton.setOnAction(e -> {
      setTheme("dark-theme");
      icon.setText("[]");
      icon.setFont(new Font("Arial", 19));
      themePopup.hide();
    });

    lightThemeButton.setOnAction(e -> {
      setTheme("light-theme");
      icon.setText("*");
      icon.setFont(new Font("Arial", 19));
      themePopup.hide();
    });

    sakuraThemeButton.setOnAction(e -> {
      setTheme("sakura-theme");
      icon.setText("-*");
      icon.setFont(new Font("Arial", 40));
      themePopup.hide();
    });

    this.themePopup.show(this.stage);

  }

  /**
   * sets the theme to the given css string
   *
   * @param theme the theme to set
   */
  @FXML
  public void setTheme(String theme) {
    Scene scene = root.getScene();

    // Remove previous theme classes
    root.getStyleClass().removeAll("dark-theme", "light-theme", "sakura-theme");

    // Add the new theme class
    root.getStyleClass().add(theme);

    scene.getStylesheets().add(Objects.requireNonNull(
        getClass().getResource("/themes.css")).toExternalForm());
  }
}
