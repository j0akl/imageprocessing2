import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import model.image.BasicImage;
import model.image.Image;
import model.operation.Blur;
import model.operation.Operation;
import model.operation.OperationFactory;
import model.operation.Sepia;
import model.pixel.Pixel;
import org.junit.Before;
import org.junit.Test;

public class TestImage {

  Image checkerboard;

  @Before
  public void setup() {
    this.checkerboard = new BasicImage();
    checkerboard.generateCheckerboard(10, 10, new double[]{255, 255, 255});
  }

  @Test
  public void testCheckerboard() {
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();
    for (int j = 0; j < checkerPixels.size(); j++) {
      for (int i = 0; i < checkerPixels.get(0).size(); i++) {
        if (i % 2 == 0 && j % 2 == 0) {
          assertTrue("Black pixel not found at " + i + ", " + j,
              Arrays.equals(checkerPixels.get(j).get(i).getRGB(), new double[]{0., 0., 0.}));
        } else if (j % 2 != 0 && i % 2 != 0) {
          assertTrue("Black pixel not found at " + i + ", " + j,
              Arrays.equals(checkerPixels.get(j).get(i).getRGB(), new double[]{0., 0., 0.}));
        } else {
          assertTrue("White pixel not found at " + i + ", " + j,
              Arrays.equals(checkerPixels.get(j).get(i).getRGB(), new double[]{255., 255., 255.}));
        }
      }
    }
  }

  @Test
  public void testSave() throws IOException {
    setup();
    this.checkerboard.save();
  }

  @Test
  public void testWriteToFile() {
    List<List<Pixel>> grid;
    Stack<Operation> his;
    BasicImage snail = new BasicImage();
    File file = new File("res/snail.ppm");
   checkerboard.writeToFile;
    System.out.println(file);
  }

  @Test
  public void testSaveAs() throws IOException {
    File f = new File("res/snail.ppm");
    checkerboard.saveAs("res/snail.ppm");

  }

  @Test
  public void testApply() {
    Operation op = new Blur();
    checkerboard.apply(op);
    System.out.println(checkerboard);
  }

  @Test
  public void testGetPixels() {
    setup();
    checkerboard.getPixels();
    System.out.println(checkerboard.getPixels());
  }


}
