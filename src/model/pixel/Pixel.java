package model.pixel;

import model.operation.Operation;

public interface Pixel {

  // --- display

  void apply(double[] change);

  double[] getRGB();

}
