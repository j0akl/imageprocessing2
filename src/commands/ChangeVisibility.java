package commands;

import java.io.IOException;
import model.image.IPModel;

public class ChangeVisibility implements Command {
  @Override
  public void go(IPModel image) throws IOException, IllegalArgumentException {
    image.changeVisibility();
  }
}
