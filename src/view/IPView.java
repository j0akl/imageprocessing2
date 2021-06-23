package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.swing.JFrame;

public interface IPView {
  void setImage(BufferedImage img);

  Map<String, String> getCommandArgs();

  void renderMessageInPopup(String message);
}
