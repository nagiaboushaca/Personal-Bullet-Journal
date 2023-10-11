package cs3500.pa05.controller;

import cs3500.pa05.design.AbstractToDo;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import javafx.scene.control.Hyperlink;

/**
 * class to handle hyperlink related functionality
 */
public class HyperlinkHandler {

  /**
   * method to find link from the given todo's description
   *
   * @param todo the todo to find the link from
   *
   * @return the hyperlink from the description, or null if there is none
   */
  public static Hyperlink findLink(AbstractToDo todo) {
    Hyperlink link;
    link = checkHTTP(todo);

    if (link != null) {
      URI uri = null;
      try {
        uri = new URI(link.getText());
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }

      URI finalUri = uri;
      link.setOnAction((event) -> {
        clickedLink(finalUri);
      });
      return link;
    }
    return null;
  }

  /**
   * opens the given uri
   *
   * @param uri the uri to open
   */
  public static void clickedLink(URI uri) {
    try {
      java.awt.Desktop.getDesktop().browse(uri);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * finds the hyper link from the todo description
   *
   * @param todo the todo to find the link from
   *
   * @return a hyperlink from the description
   */
  public static Hyperlink checkHTTP(AbstractToDo todo) {
    String desc = todo.getDescription();
    Scanner sc = new Scanner(desc);
    Hyperlink link = new Hyperlink();
    while (sc.hasNext()) {
      String value = sc.next();
      if (value.startsWith("http") || value.startsWith("https")) {
        if (isURL(value)) {
          link = new Hyperlink(value);
        }
      }
    }
    if (link.getText().equals("")) {
      return null;
    } else {
      return link;
    }

  }

  /**
   * checks if this string is a valid url
   *
   * @param url the url to check
   *
   * @return true if the string is a valid url
   */
  public static boolean isURL(String url) {
    try {
      (new java.net.URL(url)).openStream().close();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * removes the link from the description
   *
   * @param description the description that contains the url string
   *
   * @return the description with the string removed
   */
  public static String removeLinkDescription(String description) {
    Scanner sc = new Scanner(description);
    while (sc.hasNext()) {
      String value = sc.next();
      if (value.startsWith("http") || value.startsWith("https")) {
        if (isURL(value)) {
          return description.replace(value, "");
        }
      }
    }

    return description;
  }
}
