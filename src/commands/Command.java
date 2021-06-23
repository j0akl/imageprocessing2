package commands;

import java.io.IOException;
import model.image.IPModel;

public interface Command {

  void go(IPModel image)
      throws IOException, IllegalStateException, IllegalArgumentException;


}
