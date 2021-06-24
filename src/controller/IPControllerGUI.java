package controller;

import static utils.Utils.makeBIFromLayer;

import commands.AddLayer;
import commands.ChangeVisibility;
import commands.Command;
import commands.Commands;
import commands.CopyLayer;
import commands.Execute;
import commands.Export;
import commands.ExportAs;
import commands.GenCheckerboard;
import commands.Load;
import commands.NewFile;
import commands.OpCommand;
import commands.Remove;
import commands.Save;
import commands.SaveAs;
import commands.SelectLayer;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import model.image.BlendType;
import model.image.IPModel;
import model.operation.OperationFactory.OpType;
import view.IPFrame;
import view.IPView;

/**
 * The controller for the GUI version of the application. Delegates work
 * to both the view and the model. Acts as an ActionListener for the view.
 * Uses Command objects to pass data between the view and the model.
 */
public class IPControllerGUI implements IPControllerMutableModel {

  IPModel model;
  IPView view;
  Map<Commands, Function<Map<String, String>, Command>> commands;
  Map<String, String> commandArgs;
  StringBuilder executeOutput;

  /**
   * Initializes the fields used by the object, and performs some initial setup.
   * Ensures that the menu bar is working correctly on Apple platforms, and
   * creates a new instance of the view.
   */
  public IPControllerGUI() {
    commands = new HashMap<>();
    setupCommands();

    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty(
        "com.apple.mrj.application.apple.menu.about.name", "Name");
    executeOutput = new StringBuilder();
    this.model = null;
    this.view = new IPFrame(this);
  }

  /**
   * Changes the model being used by the controller and the view.
   * @param model - the model to use.
   */
  @Override
  public void setModel(IPModel model) {
    this.model = model;
  }

  /**
   * The action listener used by the view. The view passes any arguments it wants
   * to be used to a map of argument names to arguments. Each time a command is
   * called, the commandArgs map is updated. Each action on the view corresponds to a
   * Command object, which is selected by this method and run.
   * @param e - an action event.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    commandArgs = view.getCommandArgs();
    Commands cmdName = Commands.valueOf(e.getActionCommand());
    // for model dependent operations, cancel if no model is loaded
    if (!(cmdName == Commands.LOAD || cmdName == Commands.NEWFILE || cmdName == Commands.EXECUTE)
        && model == null) {
      view.renderMessageInPopup("No Image loaded");
      return;
    }
    Command cmd = commands.get(cmdName).apply(commandArgs);
    if (cmd != null) {
      try {
        cmd.go(model);
        // show output if an execute command was called
        if (cmdName == Commands.EXECUTE) {
          view.renderMessageInPopup("Output:\n" + executeOutput.toString());
          executeOutput = new StringBuilder();
        }
      } catch (IOException | IllegalStateException | IllegalArgumentException err) {
        view.renderMessageInPopup(err.getMessage());
      }
    }
    // reset the image
    view.setImage(makeBIFromLayer(model.topmostLayer()));
    // reset the layers
    view.setLayers(model.getLayerNames());
  }

  /**
   * Initialize the command map. Maps elements of the Command enum to their
   * objects. Gets arguments from the commandArgs map.
   */
  private void setupCommands() {
    commands.put(Commands.REMOVE, s -> new Remove());
    commands.put(Commands.NEWFILE, s -> new NewFile(this,
        Integer.parseInt(s.get("width")), Integer.parseInt(s.get("height"))));
    commands.put(Commands.LOAD, s -> new Load(this, s.get("filename")));
    commands.put(Commands.ADDLAYER, s -> new AddLayer(s.get("layername"), s.get("filename")));
    commands.put(Commands.SAVE, s -> new Save());
    commands.put(Commands.SAVEAS, s -> new SaveAs(s.get("filename")));
    commands.put(Commands.EXECUTE, s -> new Execute(s.get("filename"), executeOutput));
    commands.put(Commands.CHANGEVIS, s -> new ChangeVisibility());
    commands.put(Commands.COPY, s -> new CopyLayer(s.get("layername")));
    commands.put(Commands.EXPORT, s -> new Export(BlendType.valueOf(s.get("blend"))));
    commands.put(Commands.EXPORTAS, s -> new ExportAs(s.get("filename"), BlendType.valueOf(s.get("blend"))));
    commands.put(Commands.GENCHECKERBOARD, s -> new GenCheckerboard());
    commands.put(Commands.OPCOMMAND, s -> new OpCommand(OpType.valueOf(s.get("op"))));
    commands.put(Commands.SELECT, s -> new SelectLayer(s.get("layername")));
  }

  /**
   * Unused in this implementation. The program begins as soon as the view is initialized.
   */
  @Override
  public void start() {
  }
}
