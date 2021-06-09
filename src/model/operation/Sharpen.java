package model.operation;

public class Sharpen extends AbstractKernalOp {

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