import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static model.utils.Utils.matmul;

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

  // test for matmul
  @Test
  public void testMatmul() {
    double[] result = matmul(basicMatrix, testRGB);
    assertArrayEquals("matmul gave wrong values.",
        new double[] { 85.0, 190.0, 115.0},
        result,
        0.0001);
  }
}
