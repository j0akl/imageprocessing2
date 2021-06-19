import static utils.Utils.clamp;
import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
import static utils.Utils.matmul;

//import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test for utils.
 */
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

  // test for matrixMutliplication 3x3
  @Test
  public void testMatmul3x3() {
    double[] temp = matmul(basicMatrix, testRGB);
    System.out.println(temp[0]);
    System.out.println(temp[1]);
    System.out.println(temp[2]);
    assertArrayEquals("matmul gave wrong values.",
        new double[] { 85.0, 190.0, 115.0},
        new double[] { 85.0, 190.0, 115.0},
        0.0001);
  }

  //test for clamp with value greater than 255
  @Test (expected = ArrayIndexOutOfBoundsException.class)
  public void testOutBoundClamp() {
    double [] outBound = new double[258];
    clamp(outBound);
    System.out.println(outBound[258]);
  }

  //test for clamp with value less than 255
  @Test
  public void testInBoundClamp() {
    double [] inBound = new double[50];
    clamp(inBound);
    System.out.println(inBound);
    Assert.assertEquals(inBound,new double[50]);
  }

}
