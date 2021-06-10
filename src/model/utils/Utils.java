package model.utils;

/**
 * Utils class.
 */
public class Utils {

  /**
   * Multiplies the matrix by the correct values to get the new
   * matrix with proper values.
   * @param manip
   * @param rgb
   * @return a matrix
   */
  public static double[] matmul(double[][] manip, double[] rgb) {
    double[] toReturn = new double[manip.length];

    for (int i = 0; i < rgb.length; i++) {
      toReturn[i] = rgb[0] * manip[i][0] + rgb[1] * manip[i][1] + rgb[2] * manip[i][2];
    }

    return toReturn;
  }


  /**
   * Clamp ensures resulting image can be properly saved and displayed. This avoids overflow
   * and underflow while saving images.
   * @param arr
   */
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

