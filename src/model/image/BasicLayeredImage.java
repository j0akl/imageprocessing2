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

/**
 * Represents an image made up of multiple layers. Each layer
 * is stored as a Layer object in a map with a name. Provides
 * means to manipulate each layer, save, load, and export the
 * entire layered image.
 */
public class BasicLayeredImage implements LayeredImage {

  private String selectedLayer;
  private String filename;
  private final Map<String, Layer> layers;
  private final int width;
  private final int height;

  /**
   * Basic constructor. Uses a width and height to
   * initialize a base layer as a black and white checkerboard.
   * @param w - the width of the checkerboard
   * @param h - the height of the checkerboard
   */
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

  /**
   * Constructor to initialize a layered image from a saved layered image directory.
   * @param directory - the directory to load files from.
   * @throws IOException - if there is an error loading the files.
   * @throws IllegalStateException - if there is an error initializing the fields.
   */
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

  /**
   * Changes the current active layer to the one given as a parameter.
   * @param layername - the new layer to select.
   * @throws IllegalArgumentException - if the layer to select does not exist
   *                                    or is null.
   */
  public void selectLayer(String layername) throws IllegalArgumentException {
    if (layername == null) {
      throw new IllegalArgumentException("Layername was null");
    }
    if (!layers.containsKey(layername)) {
      throw new IllegalArgumentException("Layer not found");
    }
    selectedLayer = layername;
  }

  /**
   * Returns the current selected layer.
   * @return - the name of the selected layer.
   */
  public String getSelectedLayer() {
    return selectedLayer;
  }

  /**
   * Returns the pixel grid from the current selected layer.
   * @return - pixels from the selected layer.
   */
  @Override
  public List<List<Pixel>> getPixelsFromSelectedLayer() {
    return layers.get(selectedLayer).getPixels();
  }

  /**
   * Checks that a layername is valid when adding a new layer.
   * @param layername - the layername to validate.
   * @throws IllegalArgumentException - if the name is invalid.
   */
  private void validateLayername(String layername) throws IllegalArgumentException {
    if (layername == null) {
      throw new IllegalArgumentException("Must provide a name for a new layer");
    }
    if (layers.containsKey(layername)) {
      throw new IllegalArgumentException("A layer with this name already exists");
    }
  }

  /**
   * Adds an empty base layer to the top of the layer stack.
   * @param layername - the name of the new layer.
   * @throws IllegalArgumentException - if the layername is invalid.
   */
  @Override
  public void addLayer(String layername) throws IllegalArgumentException {
    validateLayername(layername);
    layers.put(layername, new BasicLayer());
  }

  /**
   * Adds a new layer from an image file.
   * @param layername - the name of the new layer.
   * @param filename - the filename for the image to add.
   * @throws IllegalArgumentException - if the name is invalid or the
   *                                    height and width of the new image
   *                                    do not match this layeredimage's.
   * @throws IOException - if there is an error loading the files.
   */
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

  /**
   * Creates a new layer from a copy of an existing one.
   * @param newname - the name for the new layer.
   * @throws IllegalArgumentException - if the new name is invalid.
   */
  @Override
  public void copyLayer(String newname) throws IllegalArgumentException {
    Layer layerToCopy = layers.get(selectedLayer);
    validateLayername(newname);
    layers.put(newname, layerToCopy.copy());
  }

  /**
   * Removes a layer from the layeredImage.
   * @throws IllegalArgumentException - if the layer is the base layer.
   */
  @Override
  public void remove() throws IllegalArgumentException {
    if (selectedLayer.equals("Base Layer")) {
      throw new IllegalArgumentException("Cannot remove base layer");
    }
    layers.remove(selectedLayer);
    selectedLayer = "Base Layer";
  }

  /**
   * Changes the visibility of the selected layer.
   */
  @Override
  public void changeVisibility() {
    layers.get(selectedLayer).changeVisibility();
  }

  /**
   * Creates a white and black checkerboard, overwriting the current
   * selected layer.
   */
  @Override
  public void generateCheckerboard() {
    layers.get(selectedLayer).generateCheckerboard(width, height, new double[] {255., 255., 255.});
  }

  /**
   * Gets the visibility status of the selected layer.
   * @return - the visibility.
   */
  @Override
  public boolean getLayerVisibility() {
    return layers.get(selectedLayer).getVisibility();
  }

  /**
   * Saves this layer image in a directory, each layer is its own image.
   * Also has a config file explaining to the program how to load the file.
   * @throws IllegalStateException - if the filename is null or invalid.
   * @throws IOException - if there is an issue saving the file.
   */
  @Override
  public void save() throws IllegalStateException, IOException {
    if (filename == null) {
      throw new IllegalStateException("This file has not been saved. Use saveAs");
    }
    saveAs(filename);
  }

  /**
   * Saves this layer image under the given name.
   * @param name - the name to save the files under.
   * @throws IllegalArgumentException - if the name is invalid.
   * @throws IOException - if there is an error saving the files.
   */
  @Override
  public void saveAs(String name) throws IllegalArgumentException, IOException {
    saveLayeredImage(name, width, height, layers);
    filename = name;
  }

  /**
   * Applies an operation to the currently selected layer.
   * @param op - the operation to apply.
   */
  @Override
  public void addFilter(Operation op) {
    layers.get(selectedLayer).apply(op);
  }

  /**
   * Flattens a layer for export by adding together the values of the pixels.
   * @return - a layer made up of a combination of all the visible layers in this image.
   */
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

  /**
   * Flattens all visible layers in this image into one image by
   * averaging the values of their pixels.
   * @return - a layer made up of a combination of all the visible layers.
   */
  private Layer flattenAvgLayers() {
    // this method is long, but all the functionality is closely related.
    // Decided to leave it as one unit rather than breaking it up.
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

  /**
   * Exports this image under the current filename.
   * @param t - the type of blending to use.
   * @throws IOException - if there is an error exporting the image.
   */
  @Override
  public void export(BlendType t) throws IOException {
    exportAs(filename, t);
  }

  /**
   * Exports this image under a new filename.
   * @param filename - the filename to use.
   * @param t - the blend type.
   * @throws IOException - if there is an error exporting the image.
   */
  @Override
  public void exportAs(String filename, BlendType t) throws IOException {
    if (t.equals(BlendType.AVG)) {
      flattenAvgLayers().saveAs(filename);
    } else {
      flattenAddLayers().saveAs(filename);
    }
  }
}
