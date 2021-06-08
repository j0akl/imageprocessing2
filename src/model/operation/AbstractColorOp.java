package model.operation;

import static model.utils.Utils.clamp;
import static model.utils.Utils.multiplyRGB3x3;

import java.util.List;
import model.image.BasicImage;
import model.pixel.Pixel;


/**
 * Abstract class for coloring operations.
 */
public abstract class AbstractColorOp extends AbstractOperation {

  private final double[][] matrix;


  /**
   * Constructor for filter operation.
   * @param matrix
   */
  public AbstractFilterOp(double[][] matrix) {
    this.matrix = matrix;
  }


  /**
   * Applies operation to basic image given.
   * @param img
   */
  public void applyToBasic(BasicImage img) {
      for (List<Pixel> row : img.getPixels()) {
      for (Pixel p : row) {
        double[] change = multiplyRGB3x3(matrix, p.getRGB());
        p.apply(change);
      }
    }
  }
}
