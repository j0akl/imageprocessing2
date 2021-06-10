package model.image;

import static model.utils.ImageUtil.readPPM;
import static model.utils.Utils.clamp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import model.operation.Operation;
import model.pixel.BasicPixel;
import model.pixel.Pixel;

/**
 * Class for a basic image, a sequence of pixels. The history will represent the most
 * recent image with all filters/adjustments made to it.
 */
public class BasicImage implements Image {

  private String filename;
  private List<List<Pixel>> grid;
  private final Stack<Operation> history;

  /**
   *
   *
   */
  public BasicImage() {
    filename = null;
    grid = new ArrayList<>();
    history = new Stack<>();
  }

  /**
   * Constructs a basic image.
   * @param filename Filename in form of string.
   * @throws FileNotFoundException throws a file not located exception.
   * @throws IllegalStateException throws an illegal state exception.
   */
  public BasicImage(String filename) throws FileNotFoundException, IllegalStateException {
    this.filename = filename;
    grid = readPPM(filename);
    history = new Stack<>();
  }

  /**
   * Saves the image with the file name.
   * @throws IOException exception.
   */
  public void save() throws IOException {
    saveAs(filename);
  }

  // @maya added this helper method when writing tests
  private void writeToFile(File f) throws IOException {
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

  /**
   * Saves the file in a PPM format.
   * @param filename the file itself.
   * @throws IOException exception.
   */
  public void saveAs(String filename) throws IOException {
    File f = new File(filename);
    writeToFile(f);
    this.filename = filename;
  }

  /**
   * Creates an image programtically by generating a checkerboard. Half
   * of the squares will be black, the other will be the color
   * specified by rgb.
   * @param x to represent the rows.
   * @param y to represent the columns.
   * @param rgb the color to place in the checkerboard.
   */
  public void generateCheckerboard(int x, int y, double[] rgb) throws IllegalArgumentException {
    List<List<Pixel>> image = new ArrayList<>();
    for (int j = 0; j < y; j++) {
      List<Pixel> row = new ArrayList<>();
      for (int i = 0; i < x; i++) {
        if (j % 2 == 0 && i % 2 == 0) {
          row.add(new BasicPixel(0, 0, 0));
        } else if (j % 2 != 0 && i % 2 != 0) {
          row.add(new BasicPixel(0, 0, 0));
        } else {
          if (rgb.length != 3) {
            throw new IllegalArgumentException("RGB must be an array of length 3");
          }
          clamp(rgb);
          row.add(new BasicPixel(rgb[0], rgb[1], rgb[2]));
        }
      }
      image.add(row);
    }
    grid = image;
  }


  public void apply(Operation op) {
    grid = op.applyToBasic(this);
    history.add(op);
  }

  public List<List<Pixel>> getPixels() {
    return new ArrayList<>(this.grid);
  }
}
