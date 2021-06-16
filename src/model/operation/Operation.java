package model.operation;

import java.util.List;
import model.image.Layer;
import model.pixel.Pixel;

/**
 * Operation interface.
 */
public interface Operation {

  /**
   * Applies operation to given image.
   * @param img the image that is being changed.
   * @return a list of list of pixels
   */
  List<List<Pixel>> applyToBasic(Layer img);
}
