package controller;

import model.image.BasicLayeredImage;
import model.image.LayeredImage;

public class ImageProcessingController {

  private final LayeredImage image;


  public ImageProcessingController(int w, int h) {
    image = new BasicLayeredImage(w, h);
  }



}
