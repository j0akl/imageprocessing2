package model.operation;

/**
 * Class for the operations.
 * Can add more filters.
 */
public class OperationFactory {

  /**
   * Enumeration for the type of operation.
   */
  public enum OpType {
    BLUR, SHARPEN, GREYSCALE, SEPIA
  }

  // mostly used for testing operations
  /**
   * Which filter/operation should be used on the image.
   * @param op operation type.
   * @return the operation user wants to work with.
   * @throws IllegalArgumentException exception of Invalid operation type.
   */
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
