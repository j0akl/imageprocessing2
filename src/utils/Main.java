package utils;

import static model.operation.OperationFactory.createOp;

import controller.IPController;
import controller.IPControllerGUI;
import controller.IPControllerText;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.image.BasicLayer;
import model.image.BasicLayeredImage;
import model.image.BlendType;
import model.image.Layer;
import model.image.IPModel;
import model.operation.OperationFactory.OpType;
import view.IPFrame;
import view.IPView;

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

    IPModel layered = new BasicLayeredImage(256, 256);
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
    IPModel model = new BasicLayeredImage(256, 256);
    File f = new File("commandSet1.txt");
    IPController controller = new IPControllerText(model, new FileReader(f), System.out);
    controller.start();
  }

  private static void mainGUI() {
    JFrame.setDefaultLookAndFeelDecorated(false);
    IPController controller = new IPControllerGUI();
    controller.start();

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException
        | ClassNotFoundException
        | InstantiationException
        | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * Main method to test the functionality of the program.
   *
   * @param args filename as string array of arguments.
   */
  public static void main(String[] args) throws FileNotFoundException {
    mainGUI();
  }
}
