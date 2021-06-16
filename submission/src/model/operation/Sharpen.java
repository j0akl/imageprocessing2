package model.operation;

/**
 * Sharpen class extending the kernel operation class.
 */
public class Sharpen extends AbstractKernelOp {

  /**
   * Constructor for sharpens so the image can be more clearer.
   */
  public Sharpen() {
    super(new double[][] {
        {-0.125, -0.125,-0.125,-0.125,-0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125,-0.125,-0.125,-0.125},
    });
  }

}
