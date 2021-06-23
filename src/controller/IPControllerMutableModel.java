package controller;

import java.awt.event.ActionListener;
import model.image.IPModel;

public interface IPControllerMutableModel extends IPController, ActionListener {

  void setModel(IPModel model);

}
