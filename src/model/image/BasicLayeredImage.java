package model.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.BaseStream;
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
    selectedLayer = "Base";
  }

  public BasicLayeredImage(String directory) throws IOException, FileNotFoundException {
    filename = directory;
    layers = new LinkedHashMap<>();
    File config = new File(directory + "/config.txt");
    BufferedReader configReader = new BufferedReader(new FileReader(config));
    String widthAndHeight = configReader.readLine();
    String[] wh = widthAndHeight.split(" ");
    width = Integer.parseInt(wh[0]);
    height = Integer.parseInt(wh[1]);
    String line = configReader.readLine();
    String layername = "";
    while (line != null) {
      Pattern pattern = Pattern.compile("/(.*?)$");
      Matcher matcher = pattern.matcher(line);
      if (matcher.find())
      {
        layername = matcher.group(1);
      } else {
        throw new IllegalStateException("Could not get the layername from the config file");
      }
      layers.put(layername, new BasicLayer(line));
      line = configReader.readLine();
    }
    selectedLayer = layername;
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
  public void addFilter(Operation op) {
    layers.get(selectedLayer).apply(op);
  }

  private void flattenAddLayers() {
    Layer black = new BasicLayer();
    black.generateCheckerboard(width, height, new double [] {0.,0.,0.});
    List<List<Pixel>> constructedLayerPixels = black.getPixels();
    for (Map.Entry<String, Layer> layerEntry : layers.entrySet()) {
      List<List<Pixel>> toAdd = layerEntry.getValue().getPixels();
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
    layers.put("Flattened", new BasicLayer(constructedLayerPixels));
  }

  private void flattenAvgLayers() {
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
    layers.put("Flattened", new BasicLayer(grid));
  }

  @Override
  public void export() {

  }

  @Override
  public void exportAs(String filename) {

  }
}
