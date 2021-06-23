import static org.junit.Assert.assertEquals;

import controller.IPController;
import controller.IPControllerText;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import model.image.BlendType;
import model.image.Layer;
import model.image.IPModel;
import model.operation.Operation;
import model.pixel.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ImageProcessing controller methods and functionality.
 */
public class TestTextController {

  Appendable output;
  IPModel model;

  /**
   * Mock model for testing the controller. Uses the output appendable
   * to track inputs passed.
   */
  private static class MockModel implements IPModel {

    private final Appendable ap;

    private MockModel(Appendable methodsCalled) {
      this.ap = methodsCalled;
    }

    public void generateCheckerboard() {
      // unused by controller
    }

    public List<String> getLayerNames() {
      return null;
    }

    public void copyLayer(String newname) {
      try {
        ap.append(newname);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void selectLayer(String layername) {
      try {
        ap.append(layername);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    public String getSelectedLayer() {
      return null;
    }

    public List<List<Pixel>> getPixelsFromSelectedLayer() {
      return null;
    }

    public void addLayer(String layername) {
      try {
        ap.append(layername);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void addLayer(String layername, String filename) {
      try {
        ap.append(layername).append(" ").append(filename);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void remove() {
      try {
        ap.append("remove");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void addFilter(Operation op) {
      try {
        ap.append(op.getClass().getSimpleName());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void changeVisibility() {
      try {
        ap.append("changedvis");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public boolean getLayerVisibility() {
      return true;
    }

    public void save() {
      try {
        ap.append("save");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void saveAs(String name) {
      try {
        ap.append(name);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void export(BlendType t) {
      try {
        ap.append(t.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public Layer topmostLayer() {return null;}
    public Layer flattenAvgLayers() {return null;}
    public Layer flattenAddLayers() {return null;}

    public void exportAs(String name, BlendType t) {
      try {
        ap.append(name).append(" ").append(String.valueOf(t));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Before
  public void setup() {
    output = new StringBuffer();
    model = new MockModel(output);
  }

  @Test
  // test the create command works correctly
  public void testCreate() {
    Readable in = new StringReader("create layer1; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to create",
        "layer1",
        output.toString());
  }

  @Test
  // test the create command works correctly from a file
  public void testCreateFromFile() {
    Readable in = new StringReader("create layer1 filename; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to create",
        "layer1 filename",
        output.toString());
  }

  @Test
  // test the copy command
  public void testCopy() {
    Readable in = new StringReader("copy layer1; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to copy",
        "layer1",
        output.toString());
  }

  @Test
  // test the save command
  public void testSave() {
    Readable in = new StringReader("save; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to save",
        "save",
        output.toString());
  }

  @Test
  // test the saveas command
  public void testSaveAs() {
    Readable in = new StringReader("saveas filename; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to saveas",
        "filename",
        output.toString());
  }

  @Test
  // test the select command
  public void testSelect() {
    Readable in = new StringReader("select layer; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to select",
        "layer",
        output.toString());
  }

  @Test
  // test the export command
  public void testExport() {
    Readable in = new StringReader("export avg; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to export",
        "AVG",
        output.toString());
  }

  @Test
  // test the exportAs command
  public void testExportAs() {
    Readable in = new StringReader("exportas avg filename; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to export",
        "filename AVG",
        output.toString());
  }

  @Test
  // test the filter command
  public void testFilter() {
    Readable in = new StringReader("filter sepia; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to filter",
        "Sepia",
        output.toString());
  }

  @Test
  // test the remove command
  public void testRemove() {
    Readable in = new StringReader("create layer1; select layer1; remove; q");
    IPController controller = new IPControllerText(model, in, System.out);
    controller.start();

    assertEquals("Wrong input to remove",
        "layer1layer1remove",
        output.toString());
  }
}
