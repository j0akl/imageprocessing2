import java.util.List;
import model.operation.OperationFactory.OpType;
import model.pixel.Pixel;
import org.junit.Test;

public class TestSharpen extends TestOperation {

  public TestSharpen() {
    super(OpType.SHARPEN);
  }

  // test for the sharpen operation
  @Test
  public void testSharpen() {
    checkerboard.apply(op);
    List<List<Pixel>> checkerPixels = checkerboard.getPixels();

  }

}
