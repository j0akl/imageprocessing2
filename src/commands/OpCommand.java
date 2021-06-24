package commands;

import static model.operation.OperationFactory.createOp;

import model.image.IPModel;
import model.operation.Operation;
import model.operation.OperationFactory.OpType;

/**
 * Command used to perform one of the image
 * manipulation operations present in the program.
 */
public class OpCommand implements Command {

  Operation op;

  /**
   * Uses an operation creator to create an Operation based on the given
   * OpType.
   * @param op - the type of operation to create.
   */
  public OpCommand(OpType op) {
    this.op = createOp(op);
  }

  /**
   * Gives the image the operation which it should perform.
   * @param image - the model to act on.
   * @throws IllegalStateException - if there is no model loaded.
   */
  public void go(IPModel image) throws IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    image.addFilter(op);
  }
}
