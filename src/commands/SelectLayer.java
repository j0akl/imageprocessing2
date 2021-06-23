package commands;

import model.image.IPModel;

public class SelectLayer implements Command {

  String layername;

  public SelectLayer(String layername) {
    this.layername = layername;
  }

  public void go(IPModel image) throws IllegalArgumentException {
    image.selectLayer(layername);
  }
}
