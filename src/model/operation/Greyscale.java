package model.operation;


/**
 * Class that represents the greyscale filter.
 */
public class Greyscale extends AbstractColorOp {

  /**
   * Constructor for greyscale. Image will become grey.
   */
  public Greyscale() {
    super(new double[][] {
        { 0.2126, 0.7152, 0.0722 },
        { 0.2126, 0.7152, 0.0722 },
        { 0.2126, 0.7152, 0.0722 },
    });
  }
}
