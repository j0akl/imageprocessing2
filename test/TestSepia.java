//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertTrue;

//import java.util.Arrays;
//import java.util.List;
import static org.junit.Assert.assertArrayEquals;

import java.util.List;
//import model.image.BasicImage;
//import model.image.Image;
import model.operation.OperationFactory.OpType;
//import model.operation.Sepia;
import model.pixel.Pixel;
//import model.pixel.Pixel;
//import org.junit.Test;

/**
 * Class for testing sepia.
 */
public class TestSepia extends TestOperation {

  public TestSepia() {
    super(OpType.SEPIA);
  }


  /**
   * Test that sepia color is added correctly.
   */
  public void testColorCorrection() {
    super.testColorCorrectionsOnCheckerboard(new double[]{219.86, 195.88, 152.54});
    checkerboard.apply(op);
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    assertArrayEquals("Image not filtered correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(0).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(0).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(0).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(1).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 120.0, 200.0, 100.0 },
        checkerPixels.get(1).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(1).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(2).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 165.0, 255.0, 137.5 },
        checkerPixels.get(2).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[] { 30.0, 50.0, 25.0 },
        checkerPixels.get(2).get(2).getRGB(),
        0.001);
  }
}
