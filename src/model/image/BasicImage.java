package model.image;

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

  public final List<List<Pixel>> grid;
  private final Stack<Operation> history;

  /**
   * Basic image constructor.
   */
  public BasicImage() {
    grid = new ArrayList<>();
    history = new Stack<>();
  }

  public void save() {}

  public void saveAs(String filename) {}

  public void load(String filename) {}

  public void apply(Operation op) {
    op.applyToBasic(this);
  }

}
