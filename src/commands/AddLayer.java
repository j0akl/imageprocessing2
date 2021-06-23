package commands;

import java.io.IOException;
import model.image.IPModel;

public class AddLayer implements Command {

  String layername;
  String filename;

  public AddLayer(String layername, String filename) {
    this.layername = layername;
    this.filename = filename;
  }

  @Override
  public void go(IPModel image) throws IOException, IllegalArgumentException {
    if (filename == null) {
      image.addLayer(layername);
    } else {
      image.addLayer(layername, filename);
    }
  }
}
