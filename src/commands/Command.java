package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * This interface is used by the GUIController to perform actions on the model.
 * The view passes the name of the command to the controller, which then performs
 * the given action.
 */
public interface Command {

  /**
   * Performs an action on the given image.
   * @param image - the model to act on.
   * @throws IOException - specific to each implementation
   * @throws IllegalStateException - specific to each implementation
   * @throws IllegalArgumentException - specific to each implementation
   */
  void go(IPModel image)
      throws IOException, IllegalStateException, IllegalArgumentException;
}
