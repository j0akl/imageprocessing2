package model.operation;

/**
 * Class to represent the sepia filter.
 */
public class Sepia extends AbstractColorOp {

  /**
   * Constructor for sepia.
   */
  public Sepia() {
    super(new double[][] {
        { 0.393, 0.769, 0.189 },
        { 0.349, 0.686, 0.168 },
        { 0.272, 0.534, 0.131 }
    });
  }
}
