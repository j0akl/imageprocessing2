package model.operation;

/**
 * Blur class extending the kernel operation class.
 */
public class Blur extends AbstractKernelOp {

  /**
   * Constructor for blur so the image can be blurrier.
   */
  public Blur() {
    super(new double[][] {
        { 0.0625, 0.125, 0.0625 },
        { 0.125, 0.25, 0.125 },
        { 0.0625, 0.125, 0.0625 }
    });
  }
}
