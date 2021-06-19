package model.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import model.image.Layer;
import model.pixel.Pixel;

public class SaveImage {

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
