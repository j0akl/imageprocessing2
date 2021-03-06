package model.pixel;

/**
 * Pixel interface.
 */
public interface Pixel {

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
