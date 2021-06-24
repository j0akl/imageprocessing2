package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * Command used by the controller to change the visibility of a layer in the model.
 */
public class ChangeVisibility implements Command {

  /**
   * Changes the visibility of the currently selected layer.
   * @param image - the model to act on.
   * @throws IllegalArgumentException - if there is no model loaded.
   */
  @Override
  public void go(IPModel image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.changeVisibility();
  }
}
