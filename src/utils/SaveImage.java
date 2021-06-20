package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import model.image.Layer;
import model.pixel.Pixel;

/**
 * Made under utils as a save image class made to save image. Handles the methods to deal saving
 * image and writing it to file.
 * Model is no longer handling this function.
 */
public class SaveImage {

  /**
   * Saves the image as the given filename as a string and the image that wants to be saved.
   * Model is no longer handling this method.
   * @param filename string of the filename to be saved under.
   * @param image image wanting to be saved.
   * @throws IOException throws the IO if file is not found.
   */
  public static void saveImage(String filename, Layer image) throws IOException {
    File f = new File(filename);
    writeToFile(f, image.getPixels());
  }

  /**
   * Writes given file in PPM format.
   * @param f file name user is working with.
   * @throws IOException  exception thrown if unable to write to PPM format.
   */
  private static void writeToFile(File f, List<List<Pixel>> grid) throws IOException {
    FileWriter fw = new FileWriter(f);
    fw.write("P3" + System.lineSeparator());
    fw.write(grid.get(0).size()
        + " "
        + grid.size()
        + System.lineSeparator());
    fw.write("255" + System.lineSeparator());
    for (int j = 0; j < grid.size(); j++) {
      StringBuilder row = new StringBuilder();
      for (int i = 0; i < grid.get(0).size(); i++) {
        double[] rgb = grid.get(j).get(i).getRGB();
        row.append((int) rgb[0])
            .append(System.lineSeparator())
            .append((int) rgb[1])
            .append(System.lineSeparator())
            .append((int) rgb[2])
            .append(System.lineSeparator());
      }
      fw.write(row.toString());
    }
    fw.close();
  }

}
