package model.operation;

import model.image.BasicImage;
import model.pixel.Pixel;

public interface Operation {
  void applyToBasic(BasicImage img);
}
