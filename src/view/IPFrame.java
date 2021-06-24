package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 * An implementation of the view for this application.
 * Interacts with the controller to get data from and manipulate the model.
 */
public class IPFrame extends JFrame implements IPView {

  private final Map<String, String> commandArgs;

  private List<String> layers;
  private String selectedLayer;

  private JPanel headerPanel;
  private JPanel mainPanel;
  private JPanel toolsPanel;

  private JMenu fileMenu;
  private JMenu layerMenu;
  private JMenu toolsMenu;
  private JMenu selectLayer;

  private JLabel display;
  private JLabel selectedLayerDisplay;

  private final ActionListener listener;

  private final JFileChooser fileChooser;

  private static final String[] BLEND_TYPES = new String[] { "AVG", "ADD", "TOP" };

  /**
   * Runs initial setup for the view.
   * @param listener - the listener to add to buttons and menu items.
   */
  public IPFrame(ActionListener listener) {
    super();
    IPFrame.setDefaultLookAndFeelDecorated(false);

    // use one file select for the whole program
    fileChooser = new JFileChooser(".");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    // assigns the listener, initializes the layername array and commandArg map
    this.listener = listener;
    this.layers = new ArrayList<>();
    commandArgs = new HashMap<>();

    // sets some parameters for the window
    setSize(800, 600);
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // begins window setup
    setupMainLayout();
    setImage(null);

    // shows the window
    setVisible(true);
  }

  /**
   * Creates the main panel and calls methods to add other elements.
   */
  private void setupMainLayout() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    add(mainPanel);

    setupMenuBar();
    setupHeaderPanel();
    setupToolsPanel();
    setupImagePanel();
  }

  /**
   * Creates the dropdowns in the menu bar and calls methods to set up
   * those dropdowns.
   */
  private void setupMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    layerMenu = new JMenu("Layer");
    menuBar.add(layerMenu);

    toolsMenu = new JMenu("Operations");
    menuBar.add(toolsMenu);

    setupFileMenu();
    setupLayerMenu();
    setupToolsMenu();
  }

  /**
   * Sets up the options in the File menu.
   */
  private void setupFileMenu() {
    JMenuItem newFile = createJMenuItemWithListener("New File", "NEWFILE", this::newFileActionListener);
    fileMenu.add(newFile);

    JMenuItem open = createJMenuItemWithListener("Open...", "LOAD", this::loadActionListener);
    fileMenu.add(open);

    JMenuItem save = createSimpleJMenuItem("Save", "SAVE");
    fileMenu.add(save);

    JMenuItem saveAs = createJMenuItemWithListener("Save As...", "SAVEAS", this::saveAsActionListener);
    fileMenu.add(saveAs);

    JMenuItem export = createJMenuItemWithListener("Export", "EXPORT", this::exportActionListener);
    fileMenu.add(export);

    JMenuItem exportAs = createJMenuItemWithListener("Export As...", "EXPORTAS", this::exportAsActionListener);
    fileMenu.add(exportAs);

    JMenuItem execute = createJMenuItemWithListener("Execute Script...", "EXECUTE", this::loadActionListener);
    fileMenu.add(execute);
  }

  /**
   * Sets up the options in the Layer menu.
   */
  private void setupLayerMenu() {
    JMenuItem newLayer = createJMenuItemWithListener("New Layer", "ADDLAYER", this::addLayerActionListener);
    layerMenu.add(newLayer);

    JMenuItem loadLayer = createJMenuItemWithListener("Load Layer", "ADDLAYER", this::loadLayerActionListener);
    layerMenu.add(loadLayer);


    JMenuItem copyLayer = createJMenuItemWithListener("Copy", "COPY", this::copyLayerActionListener);
    layerMenu.add(copyLayer);

    JMenuItem removeLayer = createSimpleJMenuItem("Remove", "REMOVE");
    layerMenu.add(removeLayer);

    JMenuItem changeLayerVisibility = createSimpleJMenuItem("Change Visibility", "CHANGEVIS");
    layerMenu.add(changeLayerVisibility);

    selectLayer = new JMenu("Select...");
    layerMenu.add(selectLayer);
    // select is a bit more complicated, so delegated to helper
    setupSelectLayerMenuItem();
  }

  /**
   * Creates the select menu. Initializes all of the options to individual layers,
   * and displays which layer is currently selected.
   */
  private void setupSelectLayerMenuItem() {
    selectLayer.removeAll();
    for (String layername : layers) {
      String textToWrite;
      if (layername.equals(selectedLayer)) {
        textToWrite = layername + " (selected)";
        selectedLayerDisplay.setText("Selected: " + layername);
      } else {
        textToWrite = layername;
      }
      JMenuItem layer = createJMenuItemWithListener(textToWrite, "SELECT", (ActionEvent e) -> {
        selectLayerListenerHelper(layername);
        setupSelectLayerMenuItem();
      });
      selectLayer.add(layer);
    }
  }

  /**
   * Sets up the options in the Tools menu.
   */
  private void setupToolsMenu() {
    JMenuItem blur = createJMenuItemWithListener("Blur", "OPCOMMAND", this::blurActionListener);
    toolsMenu.add(blur);

    JMenuItem sharpen = createJMenuItemWithListener("Sharpen", "OPCOMMAND", this::sharpenActionListener);
    toolsMenu.add(sharpen);

    JMenuItem greyscale = createJMenuItemWithListener("Greyscale", "OPCOMMAND", this::greyscaleActionListener);
    toolsMenu.add(greyscale);

    JMenuItem sepia = createJMenuItemWithListener("Sepia", "OPCOMMAND", this::sepiaActionListener);
    toolsMenu.add(sepia);

    JMenuItem genCheckerboard = createSimpleJMenuItem("Generate Checkerboard", "GENCHECKERBOARD");
    toolsMenu.add(genCheckerboard);
  }

  /**
   * Creates the header of the application and calls methods to create the elements.
   */
  private void setupHeaderPanel() {
    headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
    headerPanel.setBackground(Color.GRAY);
    headerPanel.setBorder(new LineBorder(Color.BLACK));
    mainPanel.add(headerPanel, BorderLayout.PAGE_START);

    setupNewFileButton();
    setupSaveButton();
    setupLoadButton();
    setupSaveAsButton();
    setupExportButton();
    setupExportAsButton();
  }

  /**
   * Sets up the tools portion of the application and calls methods to set up
   * the individual elements.
   */
  private void setupToolsPanel() {
    toolsPanel = new JPanel();
    toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.PAGE_AXIS));
    toolsPanel.setBackground(Color.GRAY);
    toolsPanel.setBorder(new LineBorder(Color.BLACK));
    mainPanel.add(toolsPanel, BorderLayout.LINE_START);
    selectedLayerDisplay = new JLabel();
    toolsPanel.add(selectedLayerDisplay);
    setupOperationButtons();
  }

  /**
   * Creates the image panel and the scrollbar.
   */
  private void setupImagePanel() {
    display = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(display);
    mainPanel.add(imageScrollPane, BorderLayout.CENTER);
  }

  /**
   * Creates the export button.
   */
  private void setupExportButton() {
    JButton export = createButtonWithListener("Export", "EXPORT", this::exportActionListener);
    headerPanel.add(export);
  }

  /**
   * Creates up the exportAs button.
   */
  private void setupExportAsButton() {
    JButton exportAs = createButtonWithListener("Export As", "EXPORTAS", this::exportAsActionListener);
    headerPanel.add(exportAs);
  }

  /**
   * Creates the load button.
   */
  private void setupLoadButton() {
    JButton open = createButtonWithListener("Open", "LOAD", this::loadActionListener);
    headerPanel.add(open);
  }

  /**
   * Creates the new file button.
   */
  private void setupNewFileButton() {
    JButton newFile = createButtonWithListener("New", "NEWFILE",
        this::newFileActionListener);
    headerPanel.add(newFile);
  }

  /**
   * Creates the save button.
   */
  private void setupSaveButton() {
    JButton save = createSimpleButton("Save", "SAVE");
    headerPanel.add(save);
  }

  /**
   * Creates the saveAs button.
   */
  private void setupSaveAsButton() {
    JButton saveAs = createButtonWithListener("Save As", "SAVEAS", this::saveAsActionListener);
    headerPanel.add(saveAs);
  }

  /**
   * Creates the buttons for the tools.
   */
  private void setupOperationButtons() {
    JButton blurButton = createButtonWithListener("Blur", "OPCOMMAND", this::blurActionListener);
    toolsPanel.add(blurButton);

    JButton sharpenButton = createButtonWithListener("Sharpen", "OPCOMMAND", this::sharpenActionListener);
    toolsPanel.add(sharpenButton);

    JButton greyscaleButton = createButtonWithListener("Greyscale", "OPCOMMAND", this::greyscaleActionListener);
    toolsPanel.add(greyscaleButton);

    JButton sepiaButton = createButtonWithListener("Sepia", "OPCOMMAND", this::sepiaActionListener);
    toolsPanel.add(sepiaButton);
  }

  /**
   * Sets the correct arguments in the commandArgs map.
   * @param e - an action event.
   */
  private void blurActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    replaceOrAddToCommandArgs("op", "BLUR");
  }

  /**
   * Sets the correct arguments in the commandArgs map.
   * @param e - an action event.
   */
  private void sharpenActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    replaceOrAddToCommandArgs("op", "SHARPEN");
  }

  /**
   * Sets the correct arguments in the commandArgs map.
   * @param e - an action event.
   */
  private void greyscaleActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    replaceOrAddToCommandArgs("op", "GREYSCALE");
  }

  /**
   * Sets the correct arguments in the commandArgs map.
   * @param e - an action event.
   */
  private void sepiaActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    replaceOrAddToCommandArgs("op", "SEPIA");
  }

  /**
   * Sets the correct arguments in the commandArgs map.
   * @param e - an action event.
   */
  private void copyLayerActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    String layername = JOptionPane.showInputDialog("Name your layer");
    replaceOrAddToCommandArgs("layername", layername);
  }

  /**
   * Helper for creating the selection menu items.
   * @param layername - an action event.
   */
  private void selectLayerListenerHelper(String layername) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    replaceOrAddToCommandArgs("layername", layername);
    selectedLayer = layername;
  }

  /**
   * Opens a window to choose a file to load, and sends arguments to the commandMap.
   * @param e - an action event.
   */
  private void loadLayerActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String filename = fileChooser.getSelectedFile().getAbsolutePath();
      String layername = JOptionPane.showInputDialog("Name your layer");
      replaceOrAddToCommandArgs("filename", filename);
      replaceOrAddToCommandArgs("layername", layername);
    }
  }

  /**
   * Opens a window the select the BlendType to export using.
   * @param e - an action event.
   */
  private void exportActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    Object blendType = JOptionPane.showInputDialog(
        this, "Choose Blending Type", "Blend", JOptionPane.PLAIN_MESSAGE, null, BLEND_TYPES, BLEND_TYPES[0]);
    replaceOrAddToCommandArgs("blend", blendType.toString());
  }

  /**
   * Opens the filechooser to select a location, opens a dialog box to name the file,
   * and another dialog box to select the BlendType.
   * @param e - an action event.
   */
  private void exportAsActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String rootDir = fileChooser.getSelectedFile().getAbsolutePath();
      String name = JOptionPane.showInputDialog("Name your file");
      Object blendType = JOptionPane.showInputDialog(
          this, "Choose Blending Type", "Blend", JOptionPane.PLAIN_MESSAGE, null, BLEND_TYPES, BLEND_TYPES[0]);
      replaceOrAddToCommandArgs("filename", rootDir + "/" + name);
      replaceOrAddToCommandArgs("blend", blendType.toString());
    } else {
      replaceOrAddToCommandArgs("cancelled", "true");
    }
  }

  /**
   * Opens the filechooser to select a file.
   * @param e - an action event.
   */
  private void loadActionListener(ActionEvent e) {
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String f = fileChooser.getSelectedFile().getAbsolutePath();
      replaceOrAddToCommandArgs("filename", f);
    }
  }

  /**
   * Opens dialog boxes to enter the width and height.
   * @param e - an action event.
   */
  private void newFileActionListener(ActionEvent e) {
    String width = JOptionPane.showInputDialog("Enter the width");
    String height = JOptionPane.showInputDialog("Enter the height");
    replaceOrAddToCommandArgs("width", width);
    replaceOrAddToCommandArgs("height", height);
  }

  /**
   * Opens the filechooser to select the location, and a dialog box to enter the name.
   * @param e - an action event.
   */
  private void saveAsActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String rootDir = fileChooser.getSelectedFile().getAbsolutePath();
      String name = JOptionPane.showInputDialog("Select a name");
      replaceOrAddToCommandArgs("filename", rootDir + "/" + name);
    }
  }

  /**
   * Opens a dialog box for the user to name their new layer.
   * @param e - an action event.
   */
  private void addLayerActionListener(ActionEvent e) {
    try {
      checkIfImageLoaded();
    } catch (IllegalStateException err) {
      return;
    }
    String layername = JOptionPane.showInputDialog("Name your layer");
    replaceOrAddToCommandArgs("layername", layername);
    replaceOrAddToCommandArgs("filename", null);
  }

  /**
   * Method to check if there are layers to operate on.
   * Will cancel operations if no layers are present.
   * @throws IllegalStateException - if no layers are present.
   */
  private void checkIfImageLoaded() throws IllegalStateException {
    if (layers.size() == 0) {
      throw new IllegalStateException("No image loaded");
    }
  }

  /**
   * Used to add arguments to the commandArgs map.
   * @param key - the name of the argument.
   * @param val - the argument.
   */
  private void replaceOrAddToCommandArgs(String key, String val) {
    if (commandArgs.containsKey(key)) {
      commandArgs.replace(key, val);
    } else {
      commandArgs.put(key, val);
    }
  }

  /**
   * Creates a JMenuItem with the given name and actionCommand, and assigns the
   * controller as a listener.
   * @param title - the name of the item.
   * @param actionCommand - the item's action command.
   * @return - the created JMenuItem.
   */
  private JMenuItem createSimpleJMenuItem(String title, String actionCommand) {
    JMenuItem item = new JMenuItem(title);
    item.setActionCommand(actionCommand);
    item.addActionListener(listener);
    return item;
  }

  /**
   * Creates a JButton with the given name and actionCommand, and assigns the
   * controller as a listener.
   * @param title - the name of the item.
   * @param actionCommand - the item's action command.
   * @return - the created JButton.
   */
  private JButton createSimpleButton(String title, String actionCommand) {
    JButton button = new JButton(title);
    button.addActionListener(listener);
    button.setActionCommand(actionCommand);
    return button;
  }

  /**
   * Creates a JMenuItem with the given title and action command.
   * Delegates that to createSimpleJMenuItem, and has space for another ActionListener.
   * @param title - the title of the menu item.
   * @param actionCommand - the action command of the item.
   * @param f - the additional ActionListener.
   * @return - the created JMenuItem.
   */
  private JMenuItem createJMenuItemWithListener(String title, String actionCommand, ActionListener f) {
    JMenuItem item = createSimpleJMenuItem(title, actionCommand);
    item.addActionListener(f);
    return item;
  }

  /**
   * Creates a JButton with the given title and action command.
   * Delegates that to createSimpleButton, and has space for another ActionListener.
   * @param title - the title of the menu item.
   * @param actionCommand - the action command of the item.
   * @param f - the additional ActionListener.
   * @return - the created JButton.
   */
  private JButton createButtonWithListener(String title, String actionCommand,
      ActionListener f) {
    JButton button = createSimpleButton(title, actionCommand);
    button.addActionListener(f);
    return button;
  }

  public void setLayers(List<String> layers) {
    if (this.layers.size() == 0) {
      selectedLayer = layers.get(0);
    }
    // looks better for top down display
    Collections.reverse(layers);
    this.layers = layers;
    setupSelectLayerMenuItem();
  }

  @Override
  public void setImage(BufferedImage image) {
    if (image != null) {
      display.setIcon(new ImageIcon(image));
      display.setText("");
    } else {
      display.setText("No image");
      display.setIcon(null);
    }
  }

  @Override
  public Map<String, String> getCommandArgs() {
    Map<String, String> toReturn = new HashMap<>();
    for (Map.Entry<String, String> item : commandArgs.entrySet()) {
      toReturn.put(item.getKey(), item.getValue());
    }
    return toReturn;
  }

  @Override
  public void renderMessageInPopup(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}