package model.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.image.BasicImage;
import model.image.Image;
import model.pixel.Pixel;

/**
 * Abstract class for kernal operation.
 */
public class AbstractKernalOp extends AbstractOperation {

  private final double[][] kernal;

  public AbstractKernalOp(double[][] matrix) {
    this.kernal = matrix;
  }

  private void applyKernal(List<List<Pixel>> pixels, int x, int y) {
    if (y > kernal.length)

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
