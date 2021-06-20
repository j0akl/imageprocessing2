package utils;

import static model.operation.OperationFactory.createOp;

import controller.IPController;
import controller.ImageProcessingController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import model.image.BasicLayer;
import model.image.BasicLayeredImage;
import model.image.BlendType;
import model.image.Layer;
import model.image.LayeredImage;
import model.operation.OperationFactory.OpType;

/**
 * Main method container.
 */
public class Main {

  private static void mainFromPt1(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "res/buck.ppm";
    }

    for (OpType op : OpType.values()) {
      Layer img;
      try {
        img = new BasicLayer(filename);
      } catch (IOException e) {
        System.out.println("File not found!");
        return;
      }
      img.apply(createOp(op));
      try {
        img.saveAs(filename.substring(0, filename.length() - 4) + op.toString() + ".ppm");
      } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("failed");
      }
    }
  }

  private static void mainFromPt2(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "";
    }

    LayeredImage layered = new BasicLayeredImage(256, 256);
    try {
      layered.addLayer("snail1", "res/snail.ppm");
      layered.addLayer("snail2", "res/snailBLUR.ppm");
      layered.addLayer("snail3", "res/snailGREYSCALE.ppm");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      layered.saveAs("res/snaillayers");
    } catch (IOException e) {
      System.out.println("Error saving files");
    }

    try {
      layered.exportAs("res/snailAVG.ppm", BlendType.AVG);
      layered.exportAs("res/snailADD.ppm", BlendType.ADD);
    } catch (IOException e) {
      System.out.println("Error exporting");
    }
  }

  private static void mainController() throws FileNotFoundException {
    LayeredImage model = new BasicLayeredImage(256, 256);
    File f = new File("res/commands.txt");
    IPController controller = new ImageProcessingController(model, new InputStreamReader(System.in), System.out);
    controller.go();
  }

  /**
   * Main method to test the functionality of the program.
   *
   * @param args filename as string array of arguments.
   */
  public static void main(String[] args) throws FileNotFoundException {
    mainController();
  }
}
