package model.utils;

public class Utils {

  public static double[] multiplyRGB3x3(double[][] manip, double[] rgb) {
    double[] toReturn = new double[]{ 0, 0, 0 };

    for (int i = 0; i < 3; i++) {
      toReturn[i] = rgb[0] * manip[i][0] + rgb[1] * manip[i][1] + rgb[2] * manip[i][2];
    }

    return toReturn;
  }

  public static void clamp(double[] arr) {
    for (int i = 0; i < 3; i++) {
      if (arr[i] < 0) {
        arr[i] = 0;
      }
      if (arr[i] > 255) {
        arr[i] = 255;
      }
    }
  }
}

