package commands;

import controller.IPController;
import controller.IPControllerMutableModel;
import java.io.IOException;
import model.image.BasicLayeredImage;
import model.image.IPModel;

/**
 * Command used to create a new model.
 */
public class NewFile implements Command {

  IPControllerMutableModel controller;
  int width;
  int height;

  /**
   * Initializes the height and width of the new image, and keeps track
   * of the controller to mutate.
   * @param controller - the controller to add the new model to.
   * @param width - the width of the new image.
   * @param height - the height of the new image.
   */
  public NewFile(IPControllerMutableModel controller, int width, int height) {
    this.controller = controller;
    this.width = width;
    this.height = height;
  }

  /**
   * Creates a new image with the given width and height, and gives it to the controller.
   * @param image - not used in this implementation.
   * @throws IllegalArgumentException - if the height or width are invalid.
   */
  @Override
  public void go(IPModel image)
      throws IllegalArgumentException {
    controller.setModel(new BasicLayeredImage(width, height));
  }
}
