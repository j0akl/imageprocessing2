package model.operation;

import model.image.Image;

/**
 * Operation interface.
 */
public interface Operation {
  void applyToBasic(Image img);
}
