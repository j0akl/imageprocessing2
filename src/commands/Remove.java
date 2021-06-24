package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * Command used to remove the selected layer from a model.
 */
public class Remove implements Command {

  /**
   * Removes the image from the given model.
   * @param image - the model to act on.
   * @throws IllegalStateException - if there is no image loaded.
   */
  @Override
  public void go(IPModel image) throws IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.remove();
  }
}
