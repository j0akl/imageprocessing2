package commands;

import java.io.IOException;
import model.image.IPModel;

public class Remove implements Command {
  @Override
  public void go(IPModel image) throws IOException, IllegalArgumentException {
    image.remove();
  }
}
