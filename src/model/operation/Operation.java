package model.operation;

import java.util.List;
import model.image.BasicImage;
import model.pixel.Pixel;

/**
 * Operation interface.
 */
public interface Operation {
  void applyToBasic(BasicImage img);
}
