package utils;

import static utils.ImageUtil.readPPM;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import model.image.BasicLayer;
import model.image.Layer;
import model.pixel.BasicPixel;
import model.pixel.Pixel;

/**
 * Utils class which is necessary for conducting the math and clamping
 * so values do not go out of bound.
 */
public class Utils {

  /**
   * Multiplies the matrix by the correct values to get the new
   * matrix with proper values.
   * @param manip the matrix that has the constant values.
   * @param rgb the r,g,b values being multiplied.
   * @return a newly calculated matrix.
   */
  public static double[] multVectorBy3x3Matrix(double[][] manip, double[] rgb) {
    double[] toReturn = new double[manip.length];

    for (int i = 0; i < rgb.length; i++) {
      toReturn[i] = rgb[0] * manip[i][0] + rgb[1] * manip[i][1] + rgb[2] * manip[i][2];
    }

    return toReturn;
  }


  /**
   * Clamp ensures resulting image can be properly saved and displayed. This avoids overflow
   * and underflow while saving images.
   * @param arr the array of doubles.
   */
  public static void clamp(double[] arr) {
    for (int i = 0; i < 3; i++) {
      if (arr[i] < 0) {
        arr[i] = 0;
      }
      if (arr[i] > 255) {
        arr[i] = 255;
      }
    }
  }

  /**
   * Helper method for saving files that checks if the file is in ppm format.
   * @param filename - the filename.
   * @return - a boolean discerning whether or not a file is ppm.
   * @throws IllegalArgumentException - the filname is null.
   */
  private static boolean checkPPM(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename was null");
    }
    String tail = filename.substring(filename.length() - 4);
    return tail.equalsIgnoreCase(".ppm");
  }

  /**
   * Static method used to save layers. Exports the layer with the given filetype.
   * @param filename - the location to export to.
   * @param layer - the layer to export.
   * @throws IOException - if there is an issue writing to the file.
   */
  public static void saveLayer(String filename, Layer layer) throws IOException {
    if (checkPPM(filename)) {
      saveLayerPPM(filename, layer);
      return;
    }
    BufferedImage img = makeBIFromLayer(layer);
    String tail = filename.substring(filename.length() - 3);
    File f = new File(filename);
    ImageIO.write(img, tail, f);
  }

  /**
   * Makes a BufferedImage object from a given layer. Used for exporting and displaying images.
   * @param layer - the layer to convert
   * @return - a buffered image of the passed layer
   */
  public static BufferedImage makeBIFromLayer(Layer layer) {
    List<List<Pixel>> grid = layer.getPixels();
    int h = grid.size();
    int w = grid.get(0).size();
    BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        Pixel pixel = grid.get(j).get(i);
        double[] fromPixel = pixel.getRGB();
        int rgb = (int) fromPixel[0];
        rgb = (int) ((rgb << 8) + fromPixel[1]);
        rgb = (int) ((rgb << 8) + fromPixel[2]);
        img.setRGB(i, j, rgb);
      }
    }
    return img;
  }

  /**
   * Static method that handles I/O for loading new layers.
   * @param filename - the layer to load.
   * @return - the grid of pixels made up by that layer.
   * @throws IOException - if there is an issue reading the file.
   * @throws IllegalArgumentException - if the filename is invalid.
   */
  public static List<List<Pixel>> loadLayer(String filename)
      throws IOException, IllegalArgumentException {
    if (checkPPM(filename)) {
      return readPPM(filename);
    }
    List<List<Pixel>> grid = new ArrayList<>();
    BufferedImage img = ImageIO.read(new File(filename));
    for (int j = 0; j < img.getHeight(); j++) {
      List<Pixel> row = new ArrayList<>();
      for (int i = 0; i < img.getWidth(); i++) {
        int pixel = img.getRGB(i, j);
        Color color = new Color(pixel);
        row.add(new BasicPixel(color.getRed(), color.getGreen(), color.getBlue()));
      }
      grid.add(row);
    }
    return grid;
  }

  /**
   * Saves a layer in PPM3 format.
   * @param filename - the location to save the file to.
   * @param layer - the layer to save.
   * @throws IOException - if there is an issue writing to the file.
   */
  public static void saveLayerPPM(String filename, Layer layer) throws IOException {
    File f = new File(filename);
    writeToPPM(f, layer.getPixels());
  }

  /**
   * Writes given file in PPM format.
   * @param f file name user is working with.
   * @throws IOException  exception thrown if unable to write to PPM format.
   */
  private static void writeToPPM(File f, List<List<Pixel>> grid) throws IOException {
    FileWriter fw = new FileWriter(f);
    fw.write("P3" + System.lineSeparator());
    fw.write(grid.get(0).size()
        + " "
        + grid.size()
        + System.lineSeparator());
    fw.write("255" + System.lineSeparator());
    for (int j = 0; j < grid.size(); j++) {
      StringBuilder row = new StringBuilder();
      for (int i = 0; i < grid.get(0).size(); i++) {
        double[] rgb = grid.get(j).get(i).getRGB();
        row.append((int) rgb[0])
            .append(System.lineSeparator())
            .append((int) rgb[1])
            .append(System.lineSeparator())
            .append((int) rgb[2])
            .append(System.lineSeparator());
      }
      fw.write(row.toString());
    }
    fw.close();
  }

  /**
   * Method used to handle I/O for loading layered images.
   * @param directory - the directory to load the image from.
   * @return - a map of fields for the LayeredImage.
   * @throws IOException - if there is an issue reading the files.
   */
  public static Map<String, Object> loadLayeredImage(String directory)
      throws IOException {
    Map<String, Object> fieldsToReturn = new HashMap<>();
    Map<String, Layer> layers = new LinkedHashMap<>();
    File config = new File(directory + "/config.txt");
    BufferedReader configReader = new BufferedReader(new FileReader(config));
    String widthAndHeight = configReader.readLine();
    String[] wh = widthAndHeight.split(" ");
    int width = Integer.parseInt(wh[0]);
    int height = Integer.parseInt(wh[1]);
    String line = configReader.readLine();
    String layername = "";
    while (line != null) {
      Pattern pattern = Pattern.compile("/(?:.(?!/))+$");
      Matcher matcher = pattern.matcher(line);
      if (matcher.find()) {
        layername = matcher.group();
        layername = layername.substring(1, layername.length() - 4);
      } else {
        throw new IllegalStateException("Could not get the layername from the config file");
      }
      layers.put(layername, new BasicLayer(line));
      line = configReader.readLine();
    }
    fieldsToReturn.put("width", width);
    fieldsToReturn.put("height", height);
    fieldsToReturn.put("layers", layers);
    fieldsToReturn.put("layername", layername);
    return fieldsToReturn;
  }

  /**
   * Method used to save a layered image. Creates a new directory or overwrites
   * and existing one with the files needed to represent a layered image.
   * @param name - the name of the file.
   * @param width - the width of the images.
   * @param height - the height of the images.
   * @param layers - a map containing the layers.
   * @throws IOException - if there is an issue writing the files.
   */
  public static void saveLayeredImage(String name, int width, int height, Map<String, Layer> layers)
      throws IOException {
    if (name == null) {
      throw new IllegalArgumentException("Must provide a name");
    }
    File dir = new File(name);
    dir.mkdirs();
    File configFile = new File(name + "/" + "config.txt");
    FileWriter textWriter = new FileWriter(configFile);
    textWriter.write("" + width + " " + height + "\n");
    for (Map.Entry<String, Layer> layerItem : layers.entrySet()) {
      Layer layer = layerItem.getValue();
      String nameToSaveLayerAs = name + "/" + layerItem.getKey() + ".ppm";
      layer.saveAs(nameToSaveLayerAs);
      textWriter.write(nameToSaveLayerAs + "\n");
    }
    textWriter.close();
  }

}

