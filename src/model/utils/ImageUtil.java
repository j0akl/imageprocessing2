package model.utils;

import static model.operation.OperationFactory.createOp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import model.image.BasicImage;
import model.image.Image;
//import model.operation.Blur;
//import model.operation.Greyscale;
import model.operation.OperationFactory.OpType;
//import model.operation.Sepia;
//import model.operation.Sharpen;
import model.pixel.BasicPixel;
import model.pixel.Pixel;


/**
 * This class contains a method to read a PPM image, and return a grid of the pixels. We changed
 * this method to return a list of list of pixels rather than a String.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and return the grid of pixels.
   *
   * @param filename the path of the file.
   * @return a grid of the pixels read from the file.
   */
  public static List<List<Pixel>> readPPM(String filename) throws FileNotFoundException,
      IllegalStateException {
    Scanner sc;

    List<List<Pixel>> image = new ArrayList<>();

    sc = new Scanner(new FileInputStream(filename));

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Incompatible file type. File needs to begin with P3.");
    }
    // leaving these so the file is read correctly
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        row.add(new BasicPixel(r, g, b));
      }
      image.add(row);
    }
    return image;
  }

  /**
   * Main method to test the functionality of the program.
   *
   * @param args filename as string array of arguments.
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "res/snail.ppm";
    }

    for (OpType op : OpType.values()) {
      Image img;
      try {
        img = new BasicImage(filename);
      } catch (FileNotFoundException e) {
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
}

