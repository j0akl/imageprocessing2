package commands;

import java.io.IOException;
import model.image.IPModel;

public class SaveAs implements Command {

  String filename;

  public SaveAs(String filename) {
    this.filename = filename;
  }

  public void go(IPModel image) throws IOException {
    image.saveAs(filename);
  }

}
