import static model.operation.OperationFactory.createOp;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import model.image.BasicImage;
import model.image.Image;
import model.operation.Operation;
import model.operation.OperationFactory.OpType;
import model.pixel.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract test class for operations. This
 * class will make sure that operations
 * maintain the shape of an image, specific pixel level
 * changes will be tested in the concrete extending classes.
 **/
public abstract class TestOperation {

  OpType optype;
  Image checkerboard;
  Operation op;

  public TestOperation(OpType op) {
    this.optype = op;
  }

  @Before
  public void setup() {
    this.op = createOp(optype);
    this.checkerboard = new BasicImage();
    checkerboard.generateCheckerboard(10, 10, new double[] { 120, 200, 100 });
  }

  // test that the operation maintains height and width
  @Test
  public void testDimensions() {
    assertEquals("Checkerboard image not created correctly.",
        checkerboard.getPixels().size(), 10);
    assertEquals("Checkerboard image not created correctly.",
        checkerboard.getPixels().get(0).size(), 10);
    checkerboard.apply(op);
    assertEquals("Operation changed height of image.",
        checkerboard.getPixels().size(),
        10);
    assertEquals("Operation changed width of image.",
        checkerboard.getPixels().get(0).size(), 10);
  }

  // method used by Sepia and Greyscale to test their operations
  // on the checkerboard image
  public void testColorCorrectionsOnCheckerboard(double[] target) {
    checkerboard.apply(op);
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    for (int j = 0; j < checkerPixels.size(); j++) {
      for (int i = 0; i < checkerPixels.get(0).size(); i++) {
        if (i % 2 == 0 && j % 2 == 0) {
          assertArrayEquals("Black pixel not found at " + i + ", " + j,
              new double[]{0., 0., 0.},
              checkerPixels.get(j).get(i).getRGB(),
              0.0001);
        } else if (j % 2 != 0 && i % 2 != 0) {
          assertArrayEquals("Black pixel not found at " + i + ", " + j,
              new double[]{0., 0., 0.},
              checkerPixels.get(j).get(i).getRGB(),
              0.0001);
        } else {
          assertArrayEquals("Colored pixel not found at " + i + ", " + j,
              target,
              checkerPixels.get(j).get(i).getRGB(),
              0.0001);
        }
      }
    }
  }

}