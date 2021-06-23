package controller;

import static utils.Utils.makeBIFromLayer;

import commands.AddLayer;
import commands.ChangeVisibility;
import commands.Command;
import commands.Commands;
import commands.CopyLayer;
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

public class IPControllerGUI implements IPControllerMutableModel {

  IPModel model;
  IPView view;
  Map<Commands, Function<Map<String, String>, Command>> commands;
  Map<String, String> commandArgs;

  public IPControllerGUI() {
    commands = new HashMap<>();
    setupCommands();

    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty(
        "com.apple.mrj.application.apple.menu.about.name", "Name");
    this.view = new IPFrame(this);
  }

  @Override
  public void setModel(IPModel model) {
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      commandArgs = view.getCommandArgs();
      Commands cmdName = Commands.valueOf(e.getActionCommand());
      Command cmd = commands.get(cmdName).apply(commandArgs);
      if (cmd != null) {
        try {
          cmd.go(model);
        } catch (IOException | IllegalStateException | IllegalArgumentException err) {
          view.renderMessageInPopup(err.getMessage());
        }
      }
      // NullPointerException means the action was cancelled.
    } catch (NullPointerException ignored) {  }
    view.setImage(makeBIFromLayer(model.topmostLayer()));
  }

  private void setupCommands() {
    commands.put(Commands.REMOVE, s -> new Remove());
    commands.put(Commands.NEWFILE, s -> new NewFile(this,
        Integer.parseInt(s.get("width")), Integer.parseInt(s.get("height"))));
    commands.put(Commands.LOAD, s -> new Load(this, s.get("filename")));
    commands.put(Commands.ADDLAYER, s -> new AddLayer(s.get("layername"), s.get("filename")));
    commands.put(Commands.SAVE, s -> new Save());
    commands.put(Commands.SAVEAS, s -> new SaveAs(s.get("filename")));
    commands.put(Commands.CHANGEVIS, s -> new ChangeVisibility());
    commands.put(Commands.COPY, s -> new CopyLayer(s.get("layername")));
    commands.put(Commands.EXPORT, s -> new Export(BlendType.valueOf(s.get("blend"))));
    commands.put(Commands.EXPORTAS, s -> new ExportAs(s.get("filename"), BlendType.valueOf(s.get("blend"))));
    commands.put(Commands.GENCHECKERBOARD, s -> new GenCheckerboard());
    commands.put(Commands.OPCOMMAND, s -> new OpCommand(OpType.valueOf(s.get("op"))));
    commands.put(Commands.SELECT, s -> new SelectLayer(s.get("layername")));
  }

  @Override
  public void start() {
  }
}
