package model.image;

import model.operation.Operation;

public interface Image {

  // ------ display();

  void save();

  void saveAs(String name);

  void load(String name);

  void apply(Operation op);
}
