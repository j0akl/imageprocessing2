package commands;

import controller.IPControllerMutableModel;
import java.io.IOException;
import model.image.BasicLayeredImage;
import model.image.IPModel;

/**
 * Command used to load a new IPImage to the controller.
 */
public class Load implements Command {

  String directory;
  IPControllerMutableModel controller;

  /**
   * Initializes the directory, and keeps track of the controller to mutate.
   * @param controller - the controller to assign the loaded image to.
   * @param directory - the directory in which to find the files for the
   *                  image to be loaded.
   */
  public Load(IPControllerMutableModel controller, String directory) {
    this.controller = controller;
    this.directory = directory;
  }

  /**
   * Creates a new model from the given directory, and gives it to the controller.
   * @param image - not used in this implementation.
   * @throws IOException - if there is an issue loading the model.
   * @throws IllegalArgumentException - if there is an issue with the name of the directory.
   */
  @Override
  public void go(IPModel image)
      throws IOException, IllegalArgumentException {
      controller.setModel(new BasicLayeredImage(directory));
  }
}
