package model.pixel;

import static model.utils.Utils.clamp;

/**
 * Basic pixel class.
 */
public class BasicPixel implements Pixel {

  // INVARIANT: all 3 values are in [0, 255]
  private final double[] rgb;


  /**
   * Constructor for basic pixel.
   * @param r red value
   * @param g green value
   * @param b blue value
   */
  public BasicPixel(double r, double g, double b) {
    rgb = new double[] { r, g, b };
    clamp(rgb);
  }

  /**
   * Sets the rgb value of a pixel to the given value.
   * @param newRGB the value to set the pixel to.
   */

  public void changeRGB(double[] newRGB) {
    System.arraycopy(newRGB, 0, rgb, 0, 3);
    clamp(rgb);
  }

  /**
   * Gets the RGB.
   * @return RGB
   */
  public double[] getRGB() {
    return new double[] { rgb[0], rgb[1], rgb[2] };
  }

}
