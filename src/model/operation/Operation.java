package model.operation;

import java.util.List;
import model.image.Image;
import model.pixel.Pixel;

/**
 * Operation interface.
 */
public interface Operation {
  List<List<Pixel>> applyToBasic(Image img);
}
