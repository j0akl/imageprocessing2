package model.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BasicLayeredImage implements LayeredImage {

  private String selectedLayer;
  private String filename;
  private final Map<String, Layer> layers;
  private final int width;
  private final int height;

  public BasicLayeredImage(int w, int h) {
    width = w;
    height = h;
    filename = null;
    layers = new LinkedHashMap<>();
    Layer base = new BasicLayer();
    base.generateCheckerboard(w, h, new double[] {255., 255., 255.});
    layers.put("Base Layer", base);
    selectedLayer = "Base";
  }

  public void selectLayer(String layername) throws IllegalArgumentException {
    if (layername == null) {
      throw new IllegalArgumentException("Layername was null");
    }
    if (!layers.containsKey(layername)) {
      throw new IllegalArgumentException("Layer not found");
    }
    selectedLayer = layername;
  }

  public String getSelectedLayer() {
    return selectedLayer;
  }

  private void validateLayername(String layername) throws IllegalArgumentException {
    if (layername == null) {
      throw new IllegalArgumentException("Must provide a name for a new layer");
    }
    if (layers.containsKey(layername)) {
      throw new IllegalArgumentException("A layer with this name already exists");
    }
  }

  @Override
  public void addLayer(String layername) throws IllegalArgumentException {
    validateLayername(layername);
    layers.put(layername, new BasicLayer());
  }

  @Override
  public void addLayer(String layername, String filename)
      throws IllegalArgumentException, FileNotFoundException {
    validateLayername(layername);
    Layer layer = new BasicLayer(filename);
    if (height != layer.getPixels().size() || width != layer.getPixels().get(0).size()) {
      throw new IllegalArgumentException("Layer to be added was not of correct size");
    }
    layers.put(layername, layer);
  }

  @Override
  public void copyLayer(String newname) throws IllegalArgumentException {
    Layer layerToCopy = layers.get(selectedLayer);
    validateLayername(newname);
    layers.put(newname, layerToCopy.copy());
  }

  @Override
  public void remove() throws IllegalArgumentException {
    layers.remove(selectedLayer);
  }

  @Override
  public void changeVisibility() throws IllegalArgumentException {
    layers.get(selectedLayer).changeVisibility();
  }

  @Override
  public void generateCheckerboard() throws IllegalArgumentException {
    layers.get(selectedLayer).generateCheckerboard(width, height, new double[] {255., 255., 255.});
  }

  @Override
  public void save() throws IllegalStateException, IOException {
    if (filename == null) {
      throw new IllegalStateException("This file has not been saved. Use saveAs");
    }
    saveAs(filename);
  }

  @Override
  public void saveAs(String name) throws IllegalArgumentException, IOException {
    File dir = new File(name);
    dir.mkdirs();
    File configFile = new File(name + "/" + "config.txt");
    FileWriter textWriter = new FileWriter(configFile);
    textWriter.write("" + width + " " + height);
    for (Map.Entry<String, Layer> layerItem : layers.entrySet()) {
      Layer layer = layerItem.getValue();
      String nameToSaveLayerAs = name + "/" + layerItem.getKey() + ".ppm";
      layer.saveAs(nameToSaveLayerAs);
      textWriter.write(nameToSaveLayerAs + "\n");
    }
  }

  @Override
  public void load(String directory) {
  }

  @Override
  public void flatten(BlendType t) {

  }

  @Override
  public void export() {

  }

  @Override
  public void exportAs(String filename) {

  }
}
