import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import model.image.BasicLayer;
import model.image.BasicLayeredImage;
import model.image.Layer;
import model.image.LayeredImage;
import model.pixel.Pixel;
import org.junit.Before;
import org.junit.Test;

public class TestLayeredImage {

  LayeredImage baseLayeredImage;
  LayeredImage snailLayeredImage;

  @Before
  public void setup() {
    baseLayeredImage = new BasicLayeredImage(5, 5);
    snailLayeredImage = new BasicLayeredImage(256, 256);
  }

  @Test
  // test the constructor
  public void testConstructor() {
    baseLayeredImage.selectLayer("Base Layer");
    List<List<Pixel>> basePixels = baseLayeredImage.getPixelsFromSelectedLayer();
    Layer checkerboard = new BasicLayer();
    checkerboard.generateCheckerboard(5, 5, new double[] {255., 255., 255.});
    assertEquals("Checkerboard was not generated correctly",
        checkerboard.getPixels(),
        basePixels);
  }


  @Test
  // test adding a layer from an image file
  public void testAddLayer() throws FileNotFoundException {
    snailLayeredImage.addLayer("testLayer", "res/snail.ppm");
    snailLayeredImage.selectLayer("testLayer");
    Layer snail = new BasicLayer("res/snail.ppm");
    assertEquals("Layer not imported correctly",
        snailLayeredImage.getPixelsFromSelectedLayer(),
        snail.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  // test adding a layer with the wrong dimensions
  public void testWrongDimensionLayer() throws FileNotFoundException {
    baseLayeredImage.addLayer("test", "res/snail.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  // test validate layername on null names
  public void testNullLayername() {
    baseLayeredImage.addLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  // test validate layername on duplicate names
  public void testDuplicateLayername() {
    baseLayeredImage.addLayer("Base Layer");
  }

  @Test
  // test select layer and getSelectedLayer
  public void testSelectLayer() {
    baseLayeredImage.addLayer("test");
    baseLayeredImage.selectLayer("test");
    assertEquals("Select layer not working correctly",
        "test",
        baseLayeredImage.getSelectedLayer());
  }

  @Test(expected = IllegalArgumentException.class)
  // test select layer throws exception on null
  public void testNullSelectLayer() {
    baseLayeredImage.selectLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  // test select layer throws exception on layer that isn't present
  public void testNonExistentSelectLayer() {
    baseLayeredImage.selectLayer("test");
  }

  @Test(expected = IllegalArgumentException.class)
  // test removing a layer
  public void testRemoveLayer() {
    baseLayeredImage.addLayer("test");
    baseLayeredImage.selectLayer("test");
    baseLayeredImage.remove();
    // should throw exception
    baseLayeredImage.selectLayer("test");
  }

  @Test(expected = IllegalArgumentException.class)
  // test removing base layer
  public void testRemoveBaseLayer() {
    baseLayeredImage.remove();
  }
}
