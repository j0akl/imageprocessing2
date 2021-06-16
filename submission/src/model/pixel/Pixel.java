package model.pixel;

import model.operation.Operation;

/**
 * Pixel interface.
 */
public interface Pixel {

  // --- display

  /**
   * Sets the rgb value of a pixel to the given value.
   * @param newRGB the value to set the pixel to.
   */
  void changeRGB(double[] newRGB);

  /**
   * Gets the RGB.
   * @return RGB
   */
  double[] getRGB();

}
