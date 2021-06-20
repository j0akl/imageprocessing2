package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import model.image.BasicLayeredImage;
import model.image.BlendType;
import model.image.LayeredImage;
import model.operation.Blur;
import model.operation.Greyscale;
import model.operation.Sepia;
import model.operation.Sharpen;

/**
 * A text implementation of the IPController. Can read from
 * an input stream and manipulate the given image.
 */
public class ImageProcessingController implements IPController {

  private LayeredImage model;
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructor for ImageProcessingController.
   * @param model - the image to start with.
   * @param rd - a readable to get input from.
   * @param ap - the output to use.
   * @throws IllegalArgumentException - if any of the arguments are null.
   */
  public ImageProcessingController(LayeredImage model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("One or more I/O parameter is null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    this.model = model;
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Renders the given message to the appendable.
   * @param message - the message to render.
   * @throws IllegalStateException - if there was an error outputting to the appendable.
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      ap.append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not write message");
    }
  }

  /**
   * Logic used by the controller to create a layer, either from a file
   * or as an empty layer.
   * @param scan - a scanner object to read input from.
   */
  private void createLayer(Scanner scan) {
    String command = scan.next();
    if (command.contains(";")) {
      command = command.substring(0, command.length() - 1);
      model.addLayer(command);
      renderMessage("Layer " + command + " created.");
    } else {
      String filename = scan.next();
      if (filename.contains(";")) {
        filename = filename.substring(0, filename.length() - 1);
        try {
          model.addLayer(command, filename);
          renderMessage("Layer " + command + " created from " + filename);
        } catch (IOException | IllegalArgumentException e) {
          renderMessage(e.getMessage());
        }
      } else {
        renderMessage("Commands must end in a semicolon. Try again.");
      }
    }
  }

  /**
   * Logic to load a previously saved layer image.
   * @param scan - scanner to read input from.
   */
  private void loadLayeredImage(Scanner scan) {
    String command = scan.next();
    if (command.contains(";")) {
      try {
        model = new BasicLayeredImage(command.substring(0, command.length() - 1));
        renderMessage("Image loaded from " + command);
      } catch (IOException e) {
        renderMessage(e.getMessage());
      }
    } else {
      renderMessage("Commands must end in a semicolon. Try again");
    }
  }

  /**
   * Logic to save a layered image.
   * @param scan - the scanner to read input from.
   * @param command - the most recent typed command.
   */
  private void saveLayeredImage(Scanner scan, String command) {
    if (command.equals("save;")) {
      try {
        model.save();
        renderMessage("Image saved.");
      } catch (IOException e) {
        renderMessage(e.getMessage());
      }
    } else if (command.equals("saveas")) {
      String filename = scan.next();
      if (filename.contains(";")) {
        try {
          model.saveAs(filename.substring(0, filename.length() - 1));
          renderMessage("Image saved at " + filename);
        } catch (IOException e) {
          renderMessage(e.getMessage());
        }
      } else {
        renderMessage("Commands must end in a semicolon. Try again.");
      }
    }
  }

  /**
   * Logic used to select a layer from the image.
   * @param scan - the scanner to read input from.
   */
  private void selectLayer(Scanner scan) {
    String command = scan.next();
    if (command.contains(";")) {
      try {
        command = command.substring(0, command.length() - 1);
        model.selectLayer(command);
        renderMessage("Layer " + model.getSelectedLayer() + " selected.");
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    } else {
      renderMessage("Command must end in semicolon. Try again");
    }
  }

  /**
   * Logic used to export images.
   * @param scan - the scanner to read input from
   * @param command - the most recently entered command.
   */
  private void exportImage(Scanner scan, String command) {
    if (command.equals("export")) {
      command = scan.next();
      BlendType t;
      if (command.equals("add;")) {
        t = BlendType.ADD;
      } else {
        t = BlendType.AVG;
      }
      try {
        model.export(t);
        renderMessage("Image exported.");
      } catch (IOException e) {
        renderMessage(e.getMessage());
      }
    } else if (command.equals("exportas")) {
      command = scan.next();
      BlendType t;
      if (command.equals("add")) {
        t = BlendType.ADD;
      } else {
        t = BlendType.AVG;
      }
      String filename = scan.next();
      if (filename.contains(";")) {
        filename = filename.substring(0, filename.length() - 1);
      } else {
        renderMessage("Command must end in a semicolon. Try again.");
      }
      try {
        model.exportAs(filename, t);
        renderMessage("Exported to " + filename);
      } catch (IOException e) {
        renderMessage(e.getMessage());
      }
    } else {
      renderMessage("Invalid command, try again.");
    }
  }

  /**
   * Logic used to add filters to layers.
   * @param scan - the scanner to read input from.
   */
  private void addFilter(Scanner scan) {
    String command = scan.next();
    if (command.contains(";")) {
      command = command.substring(0, command.length() - 1);
      switch (command) {
        case "sepia":
          model.addFilter(new Sepia());
          break;
        case "blur":
          model.addFilter(new Blur());
          break;
        case "greyscale":
          model.addFilter(new Greyscale());
          break;
        case "sharpen":
          model.addFilter(new Sharpen());
          break;
        default:
          renderMessage("Invalid filter type, try again.");
      }
    } else {
      renderMessage("Command must end in a semicolon, try again.");
    }
  }

  /**
   * Logic used to remove a layer from the image.
   */
  private void removeLayer() {
    model.remove();
    renderMessage("Layer removed.");
  }

  /**
   * Logic used to begin executing commands from a file.
   * @param scan - the scanner to read input from.
   */
  private void executeFromPassedFile(Scanner scan) {
    String command = scan.next();
    if (command.contains(";")) {
      command = command.substring(0, command.length() - 1);
      try {
        execute(new Scanner(new FileReader(new File(command))));
      } catch (FileNotFoundException e) {
        renderMessage(e.getMessage());
      }
    } else {
      renderMessage("Invalid command, must end in semicolon.");
    }
  }

  /**
   * Logic used to copy the selected layer into a new layer.
   * @param scan - the scanner to read input from.
   */
  private void copyLayer(Scanner scan) {
    String command = scan.next();
    if (command.contains(";")) {
      command = command.substring(0, command.length() - 1);
      model.copyLayer(command);
    } else {
      renderMessage("Invalid command, try again.");
    }
  }

  /**
   * Method used to start the program. Initializes the execute method
   * with the readable passed at controller initialization.
   */
  @Override
  public void start() {
    execute(new Scanner(rd));
  }


  /**
   * Contains the logic for running the controller. Uses the scanner passed to it
   * to get commands and execute them on the model.
   *
   * @param scan - the scanner to use when reading commands.
   */
  private void execute(Scanner scan) {
    String command = scan.next();
    while (!(command.equals("q") || command.equals("q;"))) {
      if (command.equals("create")) {
        createLayer(scan);
      } else if (command.equals("copy")) {
        copyLayer(scan);
      } else if (command.equals("load")) {
        loadLayeredImage(scan);
      } else if (command.contains("save")) {
        saveLayeredImage(scan, command);
      } else if (command.equals("select")) {
        selectLayer(scan);
      } else if (command.contains("export")) {
        exportImage(scan, command);
      } else if (command.equals("filter")) {
        addFilter(scan);
      } else if (command.equals("remove;")) {
        removeLayer();
      } else if (command.equals("exec")) {
        executeFromPassedFile(scan);
      } else {
        renderMessage("Invalid command, try again.");
      }
      command = scan.next();
    }
    renderMessage("Program ended.");
  }
}
