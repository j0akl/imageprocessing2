package commands;

import java.io.IOException;
import model.image.IPModel;

public class Save implements Command {
  public void go(IPModel image) throws IOException {
    image.save();
  }
}
