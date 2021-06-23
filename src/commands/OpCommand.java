package commands;

import static model.operation.OperationFactory.createOp;

import model.image.IPModel;
import model.operation.Operation;
import model.operation.OperationFactory.OpType;

public class OpCommand implements Command {

  Operation op;

  public OpCommand(OpType op) {
    this.op = createOp(op);
  }

  public void go(IPModel image) {
    image.addFilter(op);
  }
}
