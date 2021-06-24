package commands;

import java.io.IOException;
import model.image.BlendType;
import model.image.IPModel;

/**
 * Command used to export an image to the given filename using the given
 * blending style.
 */
public class ExportAs implements Command {

  String filename;
  BlendType t;

  /**
   * Initializes the filename and the blend type.
   * @param filename - the name of the file to export to.
   * @param t - the method used to blend the layers.
   */
  public ExportAs(String filename, BlendType t) {
    this.filename = filename;
    this.t = t;
  }


  /**
   * Exports the image to the given filename using the given blendtype.
   * @param image - the model to act on.
   * @throws IOException - if there is an error writing to the file.
   * @throws IllegalArgumentException - if the filename is invalid.
   * @throws IllegalStateException - if there is no image loaded.
   */
  @Override
  public void go(IPModel image)
      throws IOException, IllegalStateException, IllegalArgumentException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.exportAs(filename, t);
  }
}
