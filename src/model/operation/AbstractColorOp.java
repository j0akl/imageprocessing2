package model.operation;

import static model.utils.Utils.matmul;

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
   * @param matrix
   */
  public AbstractColorOp(double[][] matrix) {
    this.matrix = matrix;
  }


  /**
   * Applies operation to basic image given.
   * @param img
   */
  public void applyToBasic(Image img) {
      for (List<Pixel> row : img.getPixels()) {
      for (Pixel p : row) {
        double[] change = matmul(matrix, p.getRGB());
        p.apply(change);
      }
    }
  }
}
