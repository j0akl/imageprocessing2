package commands;

import model.image.IPModel;

/**
 * Command used to change the selected layer in an image.
 */
public class SelectLayer implements Command {

  String layername;

  /**
   * Takes the name of the layer to select.
   * @param layername - the layer to select.
   */
  public SelectLayer(String layername) {
    this.layername = layername;
  }

  /**
   * Tells the model which layer it should set as its new selected layer.
   * @param image - the model to act on.
   * @throws IllegalArgumentException - if the name of the layer is invalid or not present.
   * @throws IllegalStateException - if there is no image loaded.
   */
  public void go(IPModel image) throws IllegalArgumentException, IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.selectLayer(layername);
  }
}
