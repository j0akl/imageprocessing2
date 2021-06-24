package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * Command used to generate a checkerboard image on the currently selected layer.
 */
public class GenCheckerboard implements Command {

  /**
   * Generates a checkerboard on the layer currently selected in the given image.
   * @param image - the model to act on.
   * @throws IllegalStateException - if there is no image loaded.
   */
  @Override
  public void go(IPModel image) throws IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.generateCheckerboard();
  }
}
