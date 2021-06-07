package model.operation;

import static model.utils.Utils.clamp;
import static model.utils.Utils.multiplyRGB3x3;

import java.util.List;
import model.image.BasicImage;
import model.pixel.Pixel;

public abstract class AbstractFilterOp extends AbstractOperation {

  private final double[][] matrix;

  AbstractFilterOp(double[][] matrix) {
    this.matrix = matrix;
  }

  public void applyToBasic(BasicImage img) {
    for (List<Pixel> row : img.grid) {
      for (Pixel p : row) {
        double[] change = multiplyRGB3x3(matrix, p.getRGB());
        p.apply(change);
      }
    }
  }
}
