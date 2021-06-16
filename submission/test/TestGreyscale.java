import static org.junit.Assert.assertArrayEquals;

import java.util.List;
import model.operation.OperationFactory.OpType;
import model.pixel.Pixel;
import org.junit.Test;

public class TestGreyscale extends TestOperation {

  public TestGreyscale() {
    super(OpType.GREYSCALE);
  }

  // test that the filter is applied correctly
  @Test
  public void testColorCorrection() {
    super.testColorCorrectionsOnCheckerboard(new double[]{175.772, 175.772, 175.772});
  }

}
