package controller;

/**
 * Interface used to control the program. Makes use of the model,
 * and later on a view, to allow the user to manipulate images.
 */
public interface IPController {

  /**
   * Runs the program. Uses the image and user input to
   * manipulate the layers contained in the model.
   */
  void start();

}
