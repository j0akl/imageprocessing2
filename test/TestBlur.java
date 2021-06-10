import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.List;
import model.operation.OperationFactory.OpType;
import model.pixel.Pixel;
import org.junit.Test;

public class TestBlur extends TestOperation {

  public TestBlur() {
    super(OpType.BLUR);
  }

  // test that the blur method works correctly
  @Test
  public void testBlur() {
    checkerboard.apply(op);
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(0).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 45.0, 75.0, 37.5 },
        checkerPixels.get(0).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(0).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 45.0, 75.0, 37.5 },
        checkerPixels.get(1).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 60.0, 100.0, 50.0 },
        checkerPixels.get(1).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 45.0, 75.0, 37.5 },
        checkerPixels.get(1).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(2).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 45.0, 75.0, 37.5 },
        checkerPixels.get(2).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not blurred correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(2).get(2).getRGB(),
        0.001);
    }
}

