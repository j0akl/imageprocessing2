package model.pixel;

import model.operation.Operation;

public interface Pixel {

  // --- display

  void changeRGB(double[] newRGB);

  double[] getRGB();

}
