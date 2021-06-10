package model.operation;

public class Blur extends AbstractKernelOp {

  public Blur() {
    super(new double[][] {
        { 0.0625, 0.125, 0.0625 },
        { 0.125, 0.25, 0.125 },
        { 0.0625, 0.125, 0.0625 }
    });
  }
}
