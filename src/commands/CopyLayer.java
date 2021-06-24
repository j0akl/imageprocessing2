package commands;

import model.image.IPModel;

/**
 * Command used by the controller to copy a layer in the model.
 */
public class CopyLayer implements Command {

  String layername;

  /**
   * Initializes the name of the new layer.
   * @param layername - the name of the new layer.
   */
  public CopyLayer(String layername) {
    this.layername = layername;
  }

  /**
   * Changes the visibility of the given layer in the given model.
   * @param image - the model to act on.
   * @throws IllegalArgumentException - if there is no model present.
   */
  @Override
  public void go(IPModel image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.copyLayer(layername);
  }
}
