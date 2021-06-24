package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * Command used to save the model.
 */
public class Save implements Command {

  /**
   * Tells the model to save itself to the location it was most recently saved to.
   * @param image - the model to act on.
   * @throws IOException - if there is an error saving the file.
   * @throws IllegalStateException - if there is no image loaded.
   */
  public void go(IPModel image) throws IOException, IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.save();
  }
}
