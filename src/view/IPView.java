package view;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Exposes the public methods of the view. Allows the
 * controller to access some functions that are not present
 * in the JFrame class.
 */
public interface IPView {

  /**
   * Changes the image the view will display.
   * @param img - the view to display.
   */
  void setImage(BufferedImage img);

  /**
   * Returns a copy of the current state of the command map.
   * @return - the command argument map.
   */
  Map<String, String> getCommandArgs();

  /**
   * Tells the view what layers are present in the model being used.
   * @param layers - the layers in the model.
   */
  void setLayers(List<String> layers);

  /**
   * Tells the view to render a message in a popup window.
   * @param message - the message to render.
   */
  void renderMessageInPopup(String message);
}
