package model.image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.operation.Operation;
import model.pixel.Pixel;

public interface LayeredImage {

  void selectLayer(String layername) throws IllegalArgumentException;

  String getSelectedLayer();

  List<List<Pixel>> getPixelsFromSelectedLayer();

  void addLayer(String layername) throws IllegalArgumentException;

  void addLayer(String layerName, String layername)
      throws IllegalArgumentException, IOException;

  void copyLayer(String newname) throws IllegalArgumentException;

  void remove() throws IllegalArgumentException;

  void addFilter(Operation op);

  void changeVisibility();

  void generateCheckerboard();

  void save() throws IllegalStateException, IOException;

  void saveAs(String filename) throws IOException, IllegalArgumentException;

  void export(BlendType t) throws IOException;

  void exportAs(String filename, BlendType t) throws IOException;

}
