package commands;

import controller.IPController;
import controller.IPControllerMutableModel;
import java.io.IOException;
import model.image.BasicLayeredImage;
import model.image.IPModel;

public class NewFile implements Command {

  IPControllerMutableModel controller;
  int width;
  int height;

  public NewFile(IPControllerMutableModel controller, int width, int height) {
    this.controller = controller;
    this.width = width;
    this.height = height;
  }

  @Override
  public void go(IPModel image)
      throws IOException, IllegalStateException, IllegalArgumentException {
    controller.setModel(new BasicLayeredImage(width, height));
  }
}
