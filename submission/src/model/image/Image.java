package model.image;

import java.io.IOException;
import java.util.List;
import model.operation.Operation;
import model.pixel.Pixel;

/**
 * Image interface.
 */
public interface Image {

  // ------ display();

  /**
   * Saves image.
   * @throws IOException throws exception if unable to save.
   */
  void save() throws IOException;

  /**
   * Helper that saves the given filename in PPM format.
   * @param name the file itself as a string.
   * @throws IOException exception thrown if unable to save.
   */
  void saveAs(String name) throws IOException;

  /**
   * Generates the checkerboard.
   * @param w width.
   * @param h height.
   * @param rgb values of red, blue, and green.
   */
  void generateCheckerboard(int w, int h, double[] rgb);


  /**
   * Apply given operation given in parameters. Uses helper method
   * which performs the actual manipulation.
   * @param op operation.
   */
  void apply(Operation op);

  /**
   * Gets the grid of pixels.
   * @return the list of list of pixels, the grid.
   */
  List<List<Pixel>> getPixels();
}
