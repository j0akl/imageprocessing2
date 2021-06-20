package model.image;

import java.io.IOException;
import java.util.List;
import model.operation.Operation;
import model.pixel.Pixel;

/**
 * Represents an image made up of multiple layers. Each layer
 * is stored as a Layer object in a map with a name. Provides
 * means to manipulate each layer, save, load, and export the
 * entire layered image.
 */
public interface LayeredImage {

  /**
   * Changes the current active layer to the one given as a parameter.
   * @param layername - the new layer to select.
   * @throws IllegalArgumentException - if the layer to select does not exist
   *                                    or is null.
   */
  void selectLayer(String layername) throws IllegalArgumentException;

  /**
   * Returns the current selected layer.
   * @return - the name of the selected layer.
   */
  String getSelectedLayer();

  /**
   * Returns the pixel grid from the current selected layer.
   * @return - pixels from the selected layer.
   */
  List<List<Pixel>> getPixelsFromSelectedLayer();

  /**
   * Adds an empty base layer to the top of the layer stack.
   * @param layername - the name of the new layer.
   * @throws IllegalArgumentException - if the layername is invalid.
   */
  void addLayer(String layername) throws IllegalArgumentException;

  /**
   * Adds a new layer from an image file.
   * @param layername - the name of the new layer.
   * @param filename - the filename for the image to add.
   * @throws IllegalArgumentException - if the name is invalid or the
   *                                    height and width of the new image
   *                                    do not match this layeredimage's.
   * @throws IOException - if there is an error loading the files.
   */
  void addLayer(String layername, String filename)
      throws IllegalArgumentException, IOException;

  /**
   * Creates a new layer from a copy of an existing one.
   * @param newname - the name for the new layer.
   * @throws IllegalArgumentException - if the new name is invalid.
   */
  void copyLayer(String newname) throws IllegalArgumentException;

  /**
   * Removes a layer from the layeredImage.
   * @throws IllegalArgumentException - if the layer is the base layer.
   */
  void remove() throws IllegalArgumentException;

  /**
   * Applies an operation to the currently selected layer.
   * @param op - the operation to apply.
   */
  void addFilter(Operation op);

  /**
   * Changes the visibility of the selected layer.
   */
  void changeVisibility();

  /**
   * Gets the visibility status of the selected layer.
   * @return - the visibility.
   */
  boolean getLayerVisibility();

  /**
   * Creates a white and black checkerboard, overwriting the current
   * selected layer.
   */
  void generateCheckerboard();

  /**
   * Saves this layer image in a directory, each layer is its own image.
   * Also has a config file explaining to the program how to load the file.
   * @throws IllegalStateException - if the filename is null or invalid.
   * @throws IOException - if there is an issue saving the file.
   */
  void save() throws IllegalStateException, IOException;

  /**
   * Saves this layer image under the given name.
   * @param name - the name to save the files under.
   * @throws IllegalArgumentException - if the name is invalid.
   * @throws IOException - if there is an error saving the files.
   */
  void saveAs(String name) throws IOException, IllegalArgumentException;

  /**
   * Exports this image under the current filename.
   * @param t - the type of blending to use.
   * @throws IOException - if there is an error exporting the image.
   */
  void export(BlendType t) throws IOException;

  /**
   * Exports this image under a new filename.
   * @param filename - the filename to use.
   * @param t - the blend type.
   * @throws IOException - if there is an error exporting the image.
   */
  void exportAs(String filename, BlendType t) throws IOException;
}
