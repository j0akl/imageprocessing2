package commands;

import controller.IPControllerMutableModel;
import java.io.IOException;
import model.image.BasicLayeredImage;
import model.image.IPModel;

public class Load implements Command {

  String directory;
  IPControllerMutableModel controller;

  public Load(IPControllerMutableModel controller, String directory) {
    this.controller = controller;
    this.directory = directory;
  }

  @Override
  public void go(IPModel image)
      throws IOException, IllegalStateException, IllegalArgumentException {
      controller.setModel(new BasicLayeredImage(directory));
  }
}
