package model.image;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface LayeredImage {

  void selectLayer(String layername) throws IllegalArgumentException;

  String getSelectedLayer();

  void addLayer(String layername) throws IllegalArgumentException;

  void addLayer(String layerName, String layername)
      throws IllegalArgumentException, FileNotFoundException;

  void copyLayer(String newname) throws IllegalArgumentException;

  void remove() throws IllegalArgumentException;

  void changeVisibility();

  void generateCheckerboard();

  void save() throws IllegalStateException, IOException;

  void saveAs(String filename) throws IOException, IllegalArgumentException;

  void load(String directory);

  void flatten(BlendType t);

  void export();

  void exportAs(String filename);

}
