import static org.junit.Assert.assertEquals;
import static model.utils.Utils.multiplyRGB3x3;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TestUtils {

  double[] testRGB;
  double[][] basicMatrix;

  // set up fixtures
  @Before
  public void setup() {
    testRGB = new double[] { 5, 10, 20 };
    basicMatrix = new double[][] {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 3, 2, 4 },
    };
  }

  // test for matrixMutliplication 3x3
  @Test
  public void testMatmul3x3() {
    double[] temp = multiplyRGB3x3(basicMatrix, testRGB);
    System.out.println(temp[0]);
    System.out.println(temp[1]);
    System.out.println(temp[2]);
  }


}
