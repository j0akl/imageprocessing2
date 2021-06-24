package commands;

import java.io.IOException;
import model.image.IPModel;

/**
 * Command class used to add new layers to the model. The view will pass a name
 * for the new layer, and the layer will be added to the model. If there is a filename
 * passed, the file will be searched for and loaded if it exists.
 */
public class AddLayer implements Command {

  String layername;
  String filename;

  /**
   * Initializes the layername and filename fields. Filename can be null.
   * @param layername - the name of the new layer.
   * @param filename - the name of the file to search for if a file is being loaded.
   */
  public AddLayer(String layername, String filename) {
    this.layername = layername;
    this.filename = filename;
  }

  /**
   * Adds the new layer to the model.
   * @param image - the model to act on.
   * @throws IOException - if there is an error loading the image file.
   * @throws IllegalArgumentException - if the name of the layer is invalid.
   * @throws IllegalStateException - if there is no model loaded.
   */
  @Override
  public void go(IPModel image) throws IOException, IllegalArgumentException, IllegalStateException{
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    if (filename == null) {
      image.addLayer(layername);
    } else {
      image.addLayer(layername, filename);
    }
  }
}
