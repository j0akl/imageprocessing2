package controller;

import java.awt.event.ActionListener;
import model.image.IPModel;

/**
 * Interface to expose the public methods of the GUI Controller.
 * Adds the method setModel to the IPController interface.
 * Implements ActionListener so the view can use the controller as an
 * action listener.
 */
public interface IPControllerMutableModel extends IPController, ActionListener {

  /**
   * Changes the model the controller and view are using. Used when loading images.
   * @param model - the model to use.
   */
  void setModel(IPModel model);

}
