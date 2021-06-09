package model.image;

import java.io.IOException;
import java.util.List;
import model.operation.Operation;
import model.pixel.Pixel;

public interface Image {

  // ------ display();

  void save() throws IOException;

  void saveAs(String name) throws IOException;

  void generateCheckerboard(int w, int h);

  void apply(Operation op);

  List<List<Pixel>> getPixels();
}
