package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * Command used to save an image to a given filename.
 */
public class SaveAs implements Command {

  String filename;

  /**
   * Initializes the directory where the file should be saved.
   * @param filename - the name of the file to save to.
   */
  public SaveAs(String filename) {
    this.filename = filename;
  }

  /**
   * Tells the model to save itself to the given location.
   * @param image - the model to act on.
   * @throws IOException - if there is an error saving the file.
   * @throws IllegalStateException - if there is no image loaded.
   */
  public void go(IPModel image) throws IOException, IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.saveAs(filename);
  }
}
