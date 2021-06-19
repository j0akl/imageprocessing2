package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.image.BasicLayer;
import model.image.Layer;
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
      System.out.println(layername);
      System.out.println(line);
      layers.put(layername, new BasicLayer(line));
      line = configReader.readLine();
    }
    fieldsToReturn.put("width", width);
    fieldsToReturn.put("height", height);
    fieldsToReturn.put("layers", layers);
    fieldsToReturn.put("layername", layername);
    return fieldsToReturn;
  }

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

