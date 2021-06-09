package model.image;

import static model.utils.ImageUtil.readPPM;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import model.operation.Operation;
import model.pixel.Pixel;

/**
 * Class for a basic image, a sequence of pixels. The history will represent the most
 * recent image with all filters/adjustments made to it.
 */
public class BasicImage implements Image {

  private String filename;
  private final List<List<Pixel>> grid;
  private final Stack<Operation> history;


  public BasicImage() {
    filename = null;
    grid = new ArrayList<>();
    history = new Stack<>();
  }

  public BasicImage(String filename) throws FileNotFoundException, IllegalStateException {
    this.filename = filename;
    grid = readPPM(filename);
    history = new Stack<>();
  }

  public void save() {
    saveAs(filename);
  }

  public void generateCheckerboard(int x, int y) {

  }

  public void saveAs(String filename) {}

  public void apply(Operation op) {
    op.applyToBasic(this);
    history.add(op);
  }

  public List<List<Pixel>> getPixels() {
    return new ArrayList<>(this.grid);
  }
}
