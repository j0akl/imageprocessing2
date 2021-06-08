package model.operation;

public class Greyscale extends AbstractColorOp {


  public Greyscale() {
    super(new double[][] {
        { 0.2126, 0.7152, 0.0722 },
        { 0.2126, 0.7152, 0.0722 },
        { 0.2126, 0.7152, 0.0722 },
    });
  }

}
