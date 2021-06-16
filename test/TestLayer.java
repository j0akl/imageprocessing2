//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import model.image.BasicLayer;
import model.image.Layer;
import model.operation.Blur;
import model.operation.Operation;
//import model.operation.OperationFactory;
//import model.operation.Sepia;
import model.pixel.Pixel;
//import org.junit.Assert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test image class.
 */
public class TestLayer {

  Layer checkerboard;

  @Before
  public void setup() {
    this.checkerboard = new BasicLayer();
    checkerboard.generateCheckerboard(10, 10, new double[]{255, 255, 255});
  }

  @Test
  public void testCheckerboard() {
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    for (int j = 0; j < checkerPixels.size(); j++) {
      for (int i = 0; i < checkerPixels.get(0).size(); i++) {
        if (i % 2 == 0 && j % 2 == 0) {
          assertArrayEquals("Black pixel not found at " + i + ", " + j,
              checkerPixels.get(j).get(i).getRGB(), new double[]{0., 0., 0.}, 0.0);
        } else if (j % 2 != 0 && i % 2 != 0) {
          assertArrayEquals("Black pixel not found at " + i + ", " + j,
              checkerPixels.get(j).get(i).getRGB(), new double[]{0., 0., 0.}, 0.0);
        } else {
          assertArrayEquals("White pixel not found at " + i + ", " + j,
              checkerPixels.get(j).get(i).getRGB(), new double[]{255., 255., 255.}, 0.0);
        }
      }
    }
  }
}
