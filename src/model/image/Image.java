package model.image;

import java.util.List;
import model.operation.Operation;
import model.pixel.Pixel;

public interface Image {

  // ------ display();

  void save();

  void saveAs(String name);

  void load(String name);

  void apply(Operation op);

  List<List<Pixel>> getPixels();
}
