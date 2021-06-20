import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import model.image.BasicLayer;
import model.image.BasicLayeredImage;
import model.image.Layer;
import model.image.LayeredImage;
import model.operation.Blur;
import model.pixel.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for LayeredImage methods and functionality.
 */
public class TestLayeredImage {

  LayeredImage baseLayeredImage;
  LayeredImage snailLayeredImage;

  Layer snailLayer1;
  Layer snailLayer2;
  Layer snailLayer3;

  Layer checkerboard;

  @Before
  public void setup() {
    baseLayeredImage = new BasicLayeredImage(5, 5);
    snailLayeredImage = new BasicLayeredImage(256, 256);

    checkerboard = new BasicLayer();
    checkerboard.generateCheckerboard(5, 5, new double[] {255., 255., 255.});

    try {
      snailLayeredImage.addLayer("snail1", "res/snail.ppm");
      snailLayeredImage.addLayer("snail2", "res/snailBLUR.ppm");
      snailLayeredImage.addLayer("snail3", "res/snailGREYSCALE.ppm");
      snailLayer1 = new BasicLayer("res/snail.ppm");
      snailLayer2 = new BasicLayer("res/snailBLUR.ppm");
      snailLayer3 = new BasicLayer("res/snailGREYSCALE.ppm");
    } catch (IOException e) {
      throw new IllegalStateException("Could not add layers");
    }
  }

  @Test
  // test the constructor
  public void testConstructor() {
    baseLayeredImage.selectLayer("Base Layer");
    List<List<Pixel>> basePixels = baseLayeredImage.getPixelsFromSelectedLayer();
    assertEquals("Checkerboard was not generated correctly",
        checkerboard.getPixels(),
        basePixels);
  }


  @Test
  // test adding a layer from an image file
  public void testAddLayer() throws IOException {
    snailLayeredImage.selectLayer("snail1");
    Layer snail = new BasicLayer("res/snail.ppm");
    assertEquals("Layer not imported correctly",
        snailLayeredImage.getPixelsFromSelectedLayer(),
        snail.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  // test adding a layer with the wrong dimensions
  public void testWrongDimensionLayer() throws IOException {
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

  @Test
  // test adding a filter
  public void testAddFilter() {
    baseLayeredImage.addFilter(new Blur());
    checkerboard.apply(new Blur());
    assertEquals("Filter not applied to layer",
        checkerboard.getPixels(),
        baseLayeredImage.getPixelsFromSelectedLayer());
  }

  @Test
  // test generating checkerboard on new layer
  public void testGenerateCheckerboard() {
    baseLayeredImage.addLayer("test");
    baseLayeredImage.selectLayer("test");
    baseLayeredImage.generateCheckerboard();
    assertEquals("Checkerboard not generated correctly",
        checkerboard.getPixels(),
        baseLayeredImage.getPixelsFromSelectedLayer());
  }

  @Test
  // test saving and loading a layered image
  public void testSaveAndLoadLayeredImage() {
    try {
      snailLayeredImage.saveAs("res/snaillayers");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    LayeredImage snailLoaded;
    try {
      snailLoaded = new BasicLayeredImage("res/snaillayers");
    } catch (IOException e) {
      throw new IllegalStateException("image was not loaded");
    }

    snailLoaded.selectLayer("snail1");
    assertEquals("First layer not loaded",
        snailLayer1.getPixels(),
        snailLoaded.getPixelsFromSelectedLayer());
    snailLoaded.selectLayer("snail2");
    assertEquals("Second layer not loaded",
        snailLayer2.getPixels(),
        snailLoaded.getPixelsFromSelectedLayer());
    snailLoaded.selectLayer("snail3");
    assertEquals("Third layer not loaded",
        snailLayer3.getPixels(),
        snailLoaded.getPixelsFromSelectedLayer());
  }
}
