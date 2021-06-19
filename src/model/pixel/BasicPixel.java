package model.pixel;

import static model.utils.Utils.clamp;

import java.util.Objects;

/**
 * Basic pixel class that implements the pixel. The purpose of this class
 * is to handle the individual pixel, retrieving the RGB values and changing
 * them with new given values.
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

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof BasicPixel)) {
      return false;
    }
    BasicPixel o = (BasicPixel) other;
    return Math.abs(this.rgb[0] - o.rgb[0]) < 0.001
      && Math.abs(this.rgb[1] - o.rgb[1]) < 0.001
      && Math.abs(this.rgb[2] - o.rgb[2]) < 0.001;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rgb[0], this.rgb[1], this.rgb[2]);
  }

}
