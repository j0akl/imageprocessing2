package commands;

import java.io.IOException;
import model.image.BlendType;
import model.image.IPModel;

public class Export implements Command {

  BlendType t;

  public Export(BlendType t) {
    this.t = t;
  }

  @Override
  public void go(IPModel image) throws IOException, IllegalArgumentException {
    image.export(t);
  }
}
