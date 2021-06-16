//import static org.junit.Assert.assertArrayEquals;

//import java.util.List;
import static org.junit.Assert.assertArrayEquals;

import java.util.List;
//import model.image.BasicImage;
//import model.image.Image;
//import model.operation.Greyscale;
import model.operation.OperationFactory.OpType;
//import model.pixel.Pixel;
import model.pixel.Pixel;
//import org.junit.Assert;
//import org.junit.Test;

/**
 * Class for testing greyscale.
 */
public class TestGreyscale extends TestOperation {

  /**
   * Testing greyscale.
   */
  public TestGreyscale() {
    super(OpType.GREYSCALE);
  }


  /**
   * Test that the filter is applied corectly.
   */
  public void testColorCorrection() {
    super.testColorCorrectionsOnCheckerboard(new double[]{175.772, 175.772, 175.772});
    checkerboard.apply(op);
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    assertArrayEquals("Image not filtered correctly",
        new double[]{30.0, 50.0, 25.0},
        checkerPixels.get(0).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{165.0, 255.0, 137.5},
        checkerPixels.get(0).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{30.0, 50.0, 25.0},
        checkerPixels.get(0).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{165.0, 255.0, 137.5},
        checkerPixels.get(1).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{120.0, 200.0, 100.0},
        checkerPixels.get(1).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{165.0, 255.0, 137.5},
        checkerPixels.get(1).get(2).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{30.0, 50.0, 25.0},
        checkerPixels.get(2).get(0).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{165.0, 255.0, 137.5},
        checkerPixels.get(2).get(1).getRGB(),
        0.001);
    assertArrayEquals("Image not filtered correctly",
        new double[]{30.0, 50.0, 25.0},
        checkerPixels.get(2).get(2).getRGB(),
        0.001);
  }
}


