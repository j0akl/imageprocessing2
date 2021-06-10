package model.operation;

import static model.utils.Utils.matmul;

import java.util.Arrays;
import java.util.List;
import model.image.Image;
import model.pixel.Pixel;

/**
 * Abstract class for coloring operations.
 */
public abstract class AbstractColorOp implements Operation {

  private final double[][] matrix;

  /**
   * Constructor for filter operation.
   * @param matrix matrix
   */
  public AbstractColorOp(double[][] matrix) {
    this.matrix = matrix;
  }


  /**
   * Applies operation to basic image given.
   * @param img image
   */
  public List<List<Pixel>> applyToBasic(Image img) {
    List<List<Pixel>> pixels = img.getPixels();
    for (List<Pixel> row : pixels) {
      for (Pixel p : row) {
        double[] change = matmul(matrix, p.getRGB());
        p.changeRGB(change);
      }
    }
    return pixels;
  }
}
