package model.operation;

import java.util.List;
import model.image.Image;
import model.pixel.Pixel;

/**
 * Abstract class for kernal operation.
 */
public class AbstractKernalOp implements Operation {

  private final double[][] kernal;

  public AbstractKernalOp(double[][] matrix) {
    this.kernal = matrix;
  }


  private void applyKernal(List<List<Pixel>> pixels, int x, int y) {
    Pixel toAlter = pixels.get(y).get(x);

    int kernalX = kernal[0].length;
    int kernalY = kernal.length;

    int topLeftX = x - kernalX / 2;
    int topLeftY = y - kernalY / 2;

    double[] changeInRGB = { 0, 0, 0 };
    for (int j = 0; j < kernal.length; j++) {
      for (int i = 0; i < kernal[0].length; i++) {
        double[] rgb;
        if (topLeftY + j < 0 || topLeftX + i < 0
            || j + topLeftY  > pixels.size()
            || i + topLeftX > pixels.get(0).size()) {
          rgb = new double[] { 0, 0, 0 };
        } else {
          Pixel toMultiply = pixels.get(topLeftY + j).get(topLeftX + i);
          rgb = toMultiply.getRGB();
        }
        for (int c = 0; c < rgb.length; c++) {
          changeInRGB[c] += rgb[c] * kernal[j][i];
        }
      }
    }
    for (int i = 0; i < changeInRGB.length; i++) {
      changeInRGB[i] = changeInRGB[i] - toAlter.getRGB()[i];
    }
    toAlter.apply(changeInRGB);
  }

  @Override
  public void applyToBasic(Image img) {
    List<List<Pixel>> pixels = img.getPixels();
    for (int j = 0; j < pixels.size(); j++) {
      List<Pixel> row = pixels.get(j);
      for (int i = 0; i < row.size(); i++) {
        applyKernal(pixels, i, j);
      }
    }
  }
}
