import static org.junit.Assert.assertArrayEquals;

//import java.util.Arrays;
import java.util.List;
import model.operation.OperationFactory.OpType;
import model.pixel.Pixel;
import org.junit.Test;

/**
 * Class for testing sharpness.
 */
public class TestSharpen extends TestOperation {

  public TestSharpen() {
    super(OpType.SHARPEN);
  }

  // test for the sharpen operation
  @Test
  public void testSharpen() {
    checkerboard.apply(op);
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(0).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(0).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(0).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(1).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 120.0, 200.0, 100.0 },
        checkerPixels.get(1).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(1).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(2).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(2).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(2).get(2).getRGB(),
        0.001);

  }

}
