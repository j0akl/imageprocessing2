package model.operation;

public class OperationFactory {

  public enum OpType {
    BLUR, SHARPEN, GREYSCALE, SEPIA
  }

  // mostly used for testing operations
  public static Operation createOp(OpType op) throws IllegalArgumentException {
    switch (op) {
      case BLUR:
        return new Blur();
      case SHARPEN:
        return new Sharpen();
      case GREYSCALE:
        return new Greyscale();
      case SEPIA:
        return new Sepia();
      default:
        throw new IllegalArgumentException("Invalid OpType");
    }
  }

}
