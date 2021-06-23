package commands;

import model.image.IPModel;

public class CopyLayer implements Command {

  String layername;

  public CopyLayer(String layername) {
    this.layername = layername;
  }

  @Override
  public void go(IPModel image) throws IllegalArgumentException {
    image.copyLayer(layername);
  }
}
