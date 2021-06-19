package model.image;

import static model.utils.ImageUtil.readPPM;
import static model.utils.SaveImage.saveImage;
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
 * recent image with all filters/adjustments made to it. Implements image interface.
 */
public class BasicLayer implements Layer {

  private String filename;
  private List<List<Pixel>> grid;
  private final Stack<Operation> history;
  private boolean isVisible;

  /**
   *Initializes basic image.
   */
  public BasicLayer() {
    filename = null;
    grid = new ArrayList<>();
    history = new Stack<>();
    isVisible = true;
  }

  /**
   * Constructor used in copy() to create a new layer
   * with the copied pixels. Also used in Layer.flatten() to
   * create a new layer from the constructed pixel values.
   * @param grid - a deep copy of the pixels in an image to copy
   */
  public BasicLayer(List<List<Pixel>> grid) {
    filename = null;
    this.grid = grid;
    history = new Stack<>();
    isVisible = true;
  }


  /**
   * Constructs a basic image.
   * @param filename Filename in form of string.
   * @throws FileNotFoundException throws a file not located exception.
   * @throws IllegalStateException throws an illegal state exception.
   */
  public BasicLayer(String filename) throws FileNotFoundException, IllegalStateException {
    this.filename = filename;
    grid = readPPM(filename);
    history = new Stack<>();
    isVisible = true;
  }

  public Layer copy() {
    return new BasicLayer(getPixels());
  }

  public void changeVisibility() {
    isVisible = !isVisible;
  }


  /**
   * Saves the image with the file name.
   * @throws IOException throws exception if unable to save.
   */
  public void save() throws IOException {
    saveAs(filename);
  }


  /**
   * Saves the file in a PPM format.
   * @param filename the file itself as a string.
   * @throws IOException exception thrown if unable to save.
   */
  public void saveAs(String filename) throws IOException {
    this.filename = filename;
    saveImage(filename, this);
  }

  /**
   * Creates an image programatically by generating a checkerboard. Half
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

  /**
   * Apply given operation given in parameters. Uses helper method
   * which performs the actual manipulation.
   * @param op the operation desired to use.
   */
  public void apply(Operation op) {
    grid = op.applyToBasic(this);
    history.add(op);
  }

  /**
   * Gets the grid of pixels.
   * @return the list of list of pixels, the grid.
   */
  public List<List<Pixel>> getPixels() {
    List<List<Pixel>> toReturn = new ArrayList<>();
    for (int i = 0; i < grid.size(); i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < grid.get(0).size(); j++) {
        double[] rgb = grid.get(i).get(j).getRGB();
        Pixel copy = new BasicPixel(rgb[0], rgb[1], rgb[2]);
        row.add(copy);
      }
      toReturn.add(row);
    }
    return toReturn;
  }
}
