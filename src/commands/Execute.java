package commands;

import controller.IPController;
import controller.IPControllerText;
import java.io.FileReader;
import java.io.IOException;
import model.image.BasicLayeredImage;
import model.image.IPModel;

/**
 * Command used by the controller to execute scripts passed as text files.
 */
public class Execute implements Command {

  String filename;
  StringBuilder output;

  /**
   * Initializes the filename to take the script from and the
   * StringBuilder to send the output of the operation to.
   * @param filename - the name of the script.
   * @param output - the place to output.
   */
  public Execute(String filename, StringBuilder output) {
    this.output = output;
    this.filename = filename;
  }

  /**
   * Creates a new TextController to execute the text script. Executes the script,
   * and sends the output back to the controller to be displayed by the view.
   * @param image - the model to act on.
   * @throws IOException - if there is an issue loading the script.
   */
  @Override
  public void go(IPModel image)
      throws IOException {
    IPController textController;
    if (image == null) {
      textController = new IPControllerText(new BasicLayeredImage(0, 0), new FileReader(filename), output);
    } else {
      textController = new IPControllerText(image, new FileReader(filename), output);
    }
    textController.start();
  }
}
