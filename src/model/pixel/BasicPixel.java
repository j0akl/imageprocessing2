package model.pixel;

import static model.utils.Utils.clamp;

public class BasicPixel implements Pixel {

  // INVARIANT: all 3 values are in [0, 255]
  private final double[] rgb;

  public BasicPixel(double r, double g, double b) {
    rgb = new double[] { r, g, b };
    clamp(rgb);
  }

  public void apply(double[] change) {
    for (int i = 0; i < 3; i++) {
      rgb[i] = rgb[i] + change[i];
    }
    clamp(rgb);
  }


  public double[] getRGB() {
    return rgb;
  }

}
