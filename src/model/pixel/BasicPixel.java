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
   * Applies the change to the image.
   * @param change type of change wanting to be applied to image.
   */

  public void apply(double[] change) {
    for (int i = 0; i < 3; i++) {
      rgb[i] = rgb[i] + change[i];
    }
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
