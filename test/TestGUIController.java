import commands.Command;
import commands.Commands;
import commands.Load;
import commands.Save;
import controller.IPControllerGUI;
import controller.IPControllerMutableModel;
import java.awt.event.ActionEvent;
import model.image.IPModel;
import model.operation.OperationFactory.OpType;
import org.junit.Test;

public class TestGUIController {

  IPModel model;
  IPControllerGUI controller;
  IPControllerMutableModel mockmodel;
  Command command;
  ActionEvent load;
  ActionEvent addlayer;
  ActionEvent changevisbility;
  ActionEvent copylayer;
  ActionEvent execute;
  ActionEvent export;
  ActionEvent exportas;
  ActionEvent gencheckerboard;
  ActionEvent newfile;
  ActionEvent opcommand;
  ActionEvent remove;
  ActionEvent save;
  ActionEvent saveas;
  ActionEvent selectlayer;
  Commands c;

  public void testSetModel() {
    mockmodel.setModel(model);
    mockmodel.start();
  }

  public void testCommands() {
    mockmodel.actionPerformed(load);
    mockmodel.actionPerformed(addlayer);
    mockmodel.actionPerformed(changevisbility);
    mockmodel.actionPerformed(copylayer);
    mockmodel.actionPerformed(execute);
    mockmodel.actionPerformed(export);
    mockmodel.actionPerformed(exportas);
    mockmodel.actionPerformed(gencheckerboard);
    mockmodel.actionPerformed(newfile);
    mockmodel.actionPerformed(opcommand);
    mockmodel.actionPerformed(remove);
    mockmodel.actionPerformed(save);
    mockmodel.actionPerformed(saveas);
    mockmodel.actionPerformed(selectlayer);
  }

}

