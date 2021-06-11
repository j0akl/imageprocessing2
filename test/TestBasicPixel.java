import model.pixel.BasicPixel;
import org.junit.Before;
import org.junit.Test;

public class TestBasicPixel {
  BasicPixel pixel;


  @Test
  public void changRGBTest() {
    BasicPixel testPixel;
    testPixel = new BasicPixel(10, 10, 10);
    testPixel.changeRGB(new double[] {100,100,100});
    System.out.println(testPixel.getRGB()[1]);
  }

  @Test
  public void getRGBTest() {
    BasicPixel pixel;
    pixel = new BasicPixel(60, 20, 200);
    pixel.getRGB();
    System.out.println(pixel.getRGB()[0]);
    System.out.println(pixel.getRGB()[1]);
    System.out.println(pixel.getRGB()[2]);
  }



}
