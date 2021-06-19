package model.image;

import static utils.Utils.loadLayeredImage;
import static utils.Utils.saveLayeredImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.operation.Operation;
import model.pixel.BasicPixel;
import model.pixel.Pixel;

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
    selectedLayer = "Base Layer";
  }

  public BasicLayeredImage(String directory) throws IOException, IllegalStateException {
    filename = directory;
    Map<String, Object> fields = loadLayeredImage(directory);
    try {
      width = (int) fields.get("width");
      height = (int) fields.get("height");
      layers = (Map<String, Layer>) fields.get("layers");
      selectedLayer = (String) fields.get("layername");
    } catch (ClassCastException e) {
      throw new IllegalStateException("Fields could not be loaded");
    }
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

  @Override
  public List<List<Pixel>> getPixelsFromSelectedLayer() {
    return layers.get(selectedLayer).getPixels();
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
      throws IllegalArgumentException, IOException {
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
    if (selectedLayer.equals("Base Layer")) {
      throw new IllegalArgumentException("Cannot remove base layer");
    }
    layers.remove(selectedLayer);
    selectedLayer = "Base Layer";
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
    saveLayeredImage(name, width, height, layers);
    filename = name;
  }

  @Override
  public void addFilter(Operation op) {
    layers.get(selectedLayer).apply(op);
  }

  private Layer flattenAddLayers() {
    Layer black = new BasicLayer();
    black.generateCheckerboard(width, height, new double [] {0.,0.,0.});
    List<List<Pixel>> constructedLayerPixels = black.getPixels();
    for (Map.Entry<String, Layer> layerEntry : layers.entrySet()) {
      List<List<Pixel>> toAdd = layerEntry.getValue().getPixels();
      if (layerEntry.getValue().getVisibility()) {
        for (int j = 0; j < toAdd.size(); j++) {
          List<Pixel> rowToAdd = toAdd.get(j);
          List<Pixel> rowToReceive = constructedLayerPixels.get(j);
          for (int i = 0; i < toAdd.get(0).size(); i++) {
            // TODO remove addpixels and do the math here
            double[] baseRGB = rowToReceive.get(i).getRGB();
            double[] addRGB = rowToAdd.get(i).getRGB();
            double[] rgb = new double[] {
                baseRGB[0] + addRGB[0],
                baseRGB[1] + addRGB[1],
                baseRGB[2] + addRGB[2],
            };
            rowToReceive.set(i, new BasicPixel(rgb[0], rgb[1], rgb[2]));
          }
        }
      }
    }
    return new BasicLayer(constructedLayerPixels);
  }

  private Layer flattenAvgLayers() {
    List<List<double[]>> sum = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      sum.add(new ArrayList<>());
      List<double[]> row = sum.get(i);
      for (int j = 0; j < width; j++) {
        double[] zeros = new double[3];
        row.add(zeros);
      }
    }
    for (Map.Entry<String, Layer> layerEntry : layers.entrySet()) {
      Layer layer = layerEntry.getValue();
      if (layer.getVisibility()) {
        List<List<Pixel>> pixels = layer.getPixels();
        for (int j = 0; j < height; j++) {
          List<Pixel> row = pixels.get(j);
          for (int i = 0; i < width; i++) {
            double[] rgb = row.get(i).getRGB();
            for (int r = 0; r < 3; r++) {
              sum.get(j).get(i)[r] += rgb[r];
            }
          }
        }
      }
    }
    List<List<Pixel>> grid = new ArrayList<>();
    for (int j = 0; j < height; j++) {
      grid.add(new ArrayList<>());
      List<Pixel> row = grid.get(j);
      for (int i = 0; i < width; i++) {
        for (int r = 0; r < 3; r++) {
          sum.get(j).get(i)[r] /= layers.size();
        }
        double[] pixelVal = sum.get(j).get(i);
        row.add(new BasicPixel(pixelVal[0], pixelVal[1], pixelVal[2]));
      }
    }
    return new BasicLayer(grid);
  }

  @Override
  public void export(BlendType t) throws IOException {
    exportAs(filename, t);
  }

  @Override
  public void exportAs(String filename, BlendType t) throws IOException {
    switch (t) {
      case AVG:
        flattenAvgLayers().saveAs(filename);
        break;
      case ADD:
        flattenAddLayers().saveAs(filename);
        break;
    }
  }
}
