package controller;

import model.image.BasicLayeredImage;
import model.image.LayeredImage;

public class ImageProcessingController {

  private final LayeredImage image;


  public ImageProcessingController() {
    image = new BasicLayeredImage(5, 5);
  }

}
