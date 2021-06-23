package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class IPFrame extends JFrame implements IPView {

  private Map<String, String> commandArgs;

  private JPanel headerPanel;
  private JPanel mainPanel;
  private JPanel toolsPanel;
  private JPanel layerPanel;

  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenu layerMenu;

  private JScrollPane layerScrollPane;
  private JScrollPane imageScrollPane;

  private JLabel display;

  private final ActionListener listener;

  private final JFileChooser fileChooser;

  private static final String[] BLEND_TYPES = new String[] { "AVG", "ADD", "TOP" };

  public IPFrame(ActionListener listener) {
    super();

    IPFrame.setDefaultLookAndFeelDecorated(false);

    fileChooser = new JFileChooser(".");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    this.listener = listener;

    commandArgs = new HashMap<>();

    setSize(1200, 600);
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setupMainLayout();
    setImage(null);
    setVisible(true);
  }

  private void setupMainLayout() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    add(mainPanel);

    setupMenuBar();
    setupHeaderPanel();
    setupToolsPanel();
    setupLayersPanel();
    setupImagePanel();
  }

  private void setupMenuBar() {
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    layerMenu = new JMenu("Layer");
    menuBar.add(layerMenu);

    setupFileMenu();
    setupLayerMenu();
  }

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
  }

  private void setupLayerMenu() {
    JMenuItem newLayer = createJMenuItemWithListener("New Layer", "ADDLAYER", this::addLayerActionListener);
    layerMenu.add(newLayer);

    JMenuItem loadLayer = createJMenuItemWithListener("Load Layer", "ADDLAYER", this::loadLayerActionListener);
    layerMenu.add(loadLayer);

    JMenuItem selectLayer = createJMenuItemWithListener("Select...", "SELECT", this::selectLayerActionListener);
    layerMenu.add(selectLayer);

    JMenuItem copyLayer = createJMenuItemWithListener("Copy", "COPY", this::copyLayerActionListener);
    layerMenu.add(copyLayer);

    JMenuItem removeLayer = createSimpleJMenuItem("Remove", "REMOVE");
    layerMenu.add(removeLayer);

    JMenuItem changeLayerVisibility = createSimpleJMenuItem("Change Visibility", "CHANGEVIS");
    layerMenu.add(changeLayerVisibility);
  }

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

  private void setupToolsPanel() {
    toolsPanel = new JPanel();
    toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.PAGE_AXIS));
    toolsPanel.setBackground(Color.GRAY);
    toolsPanel.setBorder(new LineBorder(Color.BLACK));
    mainPanel.add(toolsPanel, BorderLayout.LINE_START);
    setupOperationButtons();
    setupLayerAddButtons();
  }

  private void setupLayersPanel() {
    layerPanel = new JPanel();
    layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.PAGE_AXIS));
    layerPanel.setBackground(Color.GRAY);
    layerPanel.setBorder(new LineBorder(Color.BLACK));
    layerScrollPane = new JScrollPane(layerPanel);
    mainPanel.add(layerScrollPane, BorderLayout.LINE_END);
  }

  private void setupImagePanel() {
    display = new JLabel();
    imageScrollPane = new JScrollPane(display);
    mainPanel.add(imageScrollPane, BorderLayout.CENTER);
  }

  private void setupExportButton() {
    JButton export = createButtonWithListener("Export", "EXPORT", this::exportActionListener);
    headerPanel.add(export);
  }

  private void setupExportAsButton() {
    JButton exportAs = createButtonWithListener("Export As", "EXPORTAS", this::exportAsActionListener);
    headerPanel.add(exportAs);
  }

  private void setupLoadButton() {
    JButton open = createButtonWithListener("Open", "LOAD", this::loadActionListener);
    headerPanel.add(open);
  }

  private void setupNewFileButton() {
    JButton newFile = createButtonWithListener("New", "NEWFILE",
        this::newFileActionListener);
    headerPanel.add(newFile);
  }

  private void setupSaveButton() {
    JButton save = createSimpleButton("Save", "SAVE");
    headerPanel.add(save);
  }

  private void setupSaveAsButton() {
    JButton saveAs = createButtonWithListener("Save As", "SAVEAS", this::saveAsActionListener);
    headerPanel.add(saveAs);
  }

  private void setupLayerAddButtons() {
    JButton addLayer = createButtonWithListener("New Layer", "ADDLAYER", this::addLayerActionListener);
    toolsPanel.add(addLayer);

    JButton loadLayer = createButtonWithListener("Load Layer", "ADDLAYER", this::loadLayerActionListener);
    toolsPanel.add(loadLayer);
  }

  private void setupOperationButtons() {
    JButton blurButton = createButtonWithListener("Blur", "OPCOMMAND",
        (ActionEvent e) -> {
          commandArgs.replace("op", "BLUR");
        });
    JButton sharpenButton = createButtonWithListener("Blur", "OPCOMMAND",
        (ActionEvent e) -> {
          commandArgs.replace("op", "SHARPEN");
        });
    JButton greyscaleButton = createButtonWithListener("Greyscale", "OPCOMMAND",
        (ActionEvent e) -> {
          commandArgs.replace("op", "GREYSCALE");
        });
    JButton sepiaButton = createButtonWithListener("Sepia", "OPCOMMAND",
        (ActionEvent e) -> {
          commandArgs.replace("op", "SEPIA");
        });
    toolsPanel.add(blurButton);
    toolsPanel.add(sharpenButton);
    toolsPanel.add(greyscaleButton);
    toolsPanel.add(sepiaButton);
  }

  private void copyLayerActionListener(ActionEvent e) {
    String layername = JOptionPane.showInputDialog("Name your layer");
    replaceOrAddToCommandArgs("layername", layername);
  }

  private void selectLayerActionListener(ActionEvent e) {
    String layername = JOptionPane.showInputDialog("Select a layer");
    replaceOrAddToCommandArgs("layername", layername);
  }

  private void loadLayerActionListener(ActionEvent e) {
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String filename = fileChooser.getSelectedFile().getAbsolutePath();
      String layername = JOptionPane.showInputDialog("Name your layer");
      replaceOrAddToCommandArgs("filename", filename);
      replaceOrAddToCommandArgs("layername", layername);
    }
  }

  private void exportActionListener(ActionEvent e) {
    Object blendType = JOptionPane.showInputDialog(
        this, "Choose Blending Type", "Blend", JOptionPane.PLAIN_MESSAGE, null, BLEND_TYPES, BLEND_TYPES[0]);
    replaceOrAddToCommandArgs("blend", blendType.toString());
  }

  private void exportAsActionListener(ActionEvent e) {
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

  private void loadActionListener(ActionEvent e) {
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String f = fileChooser.getSelectedFile().getAbsolutePath();
      replaceOrAddToCommandArgs("filename", f);
    }
  }

  private void newFileActionListener(ActionEvent e) {
    String width = JOptionPane.showInputDialog("Enter the width");
    String height = JOptionPane.showInputDialog("Enter the height");
    replaceOrAddToCommandArgs("width", width);
    replaceOrAddToCommandArgs("height", height);
  }

  private void saveAsActionListener(ActionEvent e) {
    int choseFile = fileChooser.showOpenDialog(this);
    if (choseFile == JFileChooser.APPROVE_OPTION) {
      String rootDir = fileChooser.getSelectedFile().getAbsolutePath();
      String name = JOptionPane.showInputDialog("Select a name");
      replaceOrAddToCommandArgs("filename", rootDir + "/" + name);
    }
  }

  private void addLayerActionListener(ActionEvent e) {
    String layername = JOptionPane.showInputDialog("Name your layer");
    replaceOrAddToCommandArgs("layername", layername);
    replaceOrAddToCommandArgs("filename", null);
  }

  private void replaceOrAddToCommandArgs(String key, String val) {
    if (commandArgs.containsKey(key)) {
      commandArgs.replace(key, val);
    } else {
      commandArgs.put(key, val);
    }
  }

  private JMenuItem createSimpleJMenuItem(String title, String actionCommand) {
    JMenuItem item = new JMenuItem(title);
    item.setActionCommand(actionCommand);
    item.addActionListener(listener);
    return item;
  }

  private JButton createSimpleButton(String title, String actionCommand) {
    JButton button = new JButton(title);
    button.addActionListener(listener);
    button.setActionCommand(actionCommand);
    return button;
  }

  private JMenuItem createJMenuItemWithListener(String title, String actionCommand, ActionListener f) {
    JMenuItem item = createSimpleJMenuItem(title, actionCommand);
    item.addActionListener(f);
    return item;
  }

  private JButton createButtonWithListener(String title, String actionCommand,
      ActionListener f) {
    JButton button = createSimpleButton(title, actionCommand);
    button.addActionListener(f);
    return button;
  }


  @Override
  public void setImage(BufferedImage image) {
    if (image != null) {
      display.setIcon(new ImageIcon(image));
      display.setText("");
    } else {
      display.setText("Could not load image");
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
