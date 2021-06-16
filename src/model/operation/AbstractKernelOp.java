package model.operation;

import java.util.ArrayList;
import java.util.List;
import model.image.Layer;
import model.pixel.BasicPixel;
import model.pixel.Pixel;

/**
 * Abstract class for kernel operation.
 */
public class AbstractKernelOp implements Operation {

  private final double[][] kernel;

  /**
   * Constructor for abstract kernel operation.
   * @param matrix the matrix from operations with constant values.
   */
  public AbstractKernelOp(double[][] matrix) {
    this.kernel = matrix;
  }

  /**
   * Applies kernel algorithm to the list of list of pixels.
   * @param pixels the image represented as a list of list of pixels.
   * @param x representing rows
   * @param y representing columns
   * @return the new Pixel with proper manipulated values.
   */
  private Pixel applyKernel(List<List<Pixel>> pixels, int x, int y) {
    int kernelX = kernel[0].length;
    int kernelY = kernel.length;

    int topLeftX = x - kernelX / 2;
    int topLeftY = y - kernelY / 2;

    double[] newRGB = { 0, 0, 0 };
    for (int j = 0; j < kernel.length; j++) {
      for (int i = 0; i < kernel[0].length; i++) {
        double[] rgb;
        if (topLeftY + j < 0 || topLeftX + i < 0
            || j + topLeftY  > pixels.size() - 1
            || i + topLeftX > pixels.get(0).size() - 1) {
          rgb = new double[] { 0, 0, 0 };
        } else {
          Pixel toMultiply = pixels.get(topLeftY + j).get(topLeftX + i);
          rgb = toMultiply.getRGB();
        }
        for (int c = 0; c < rgb.length; c++) {
          newRGB[c] += rgb[c] * kernel[j][i];
        }
      }
    }
    return new BasicPixel(newRGB[0], newRGB[1], newRGB[2]);
  }


  /**
   * Applying to basic image using kernel method. Not every operation/filter
   * uses kernel algorithm to manipulate image.
   * @param img the image being altered.
   * @return the new list of list of pixels.
   */
  @Override
  public List<List<Pixel>> applyToBasic(Layer img) {
    List<List<Pixel>> pixels = img.getPixels();
    List<List<Pixel>> newPixels = new ArrayList<>();
    for (int j = 0; j < pixels.size(); j++) {
      List<Pixel> row = pixels.get(j);
      List<Pixel> newRow = new ArrayList<>();
      for (int i = 0; i < row.size(); i++) {
        newRow.add(applyKernel(pixels, i, j));
      }
      newPixels.add(newRow);
    }
    return newPixels;
  }
}
