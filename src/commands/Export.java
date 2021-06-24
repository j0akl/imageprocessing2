package commands;

import java.io.IOException;
import model.image.BlendType;
import model.image.IPModel;

/**
 * Command used to export an image file.
 */
public class Export implements Command {

  BlendType t;

  /**
   * Initializes the method by which to blend the layers.
   * @param t - the blend type.
   */
  public Export(BlendType t) {
    this.t = t;
  }

  /**
   * Exports the image contained in the model to an image file.
   * @param image - the model to act on.
   * @throws IOException - if there is an error writing the file.
   * @throws IllegalArgumentException - if the file has not been exported before.
   * @throws  IllegalStateException - if there is no image loaded.
   */
  @Override
  public void go(IPModel image)
      throws IOException, IllegalStateException, IllegalArgumentException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.export(t);
  }
}
