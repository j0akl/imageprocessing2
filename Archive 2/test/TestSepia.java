import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import model.operation.OperationFactory.OpType;
import model.operation.Sepia;
import model.pixel.Pixel;
import org.junit.Test;

public class TestSepia extends TestOperation {

  public TestSepia() {
    super(OpType.SEPIA);
  }

  // test that the sepia color is added correctly
  @Test
  public void testColorCorrection() {
    super.testColorCorrectionsOnCheckerboard(new double[]{219.86, 195.88, 152.54});
  }
}
