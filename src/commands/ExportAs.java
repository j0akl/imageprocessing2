package commands;

import java.io.IOException;
import model.image.BlendType;
import model.image.IPModel;

public class ExportAs implements Command {

  String filename;
  BlendType t;

  public ExportAs(String filename, BlendType t) {
    this.filename = filename;
    this.t = t;
  }


  @Override
  public void go(IPModel image) throws IOException, IllegalArgumentException {

  }
}
