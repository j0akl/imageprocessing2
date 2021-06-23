package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
  private JPanel imagePanel;

  private JMenuBar menuBar;

  private JScrollPane layerScrollPane;

  private JButton testButton;

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
    setVisible(true);
  }

  private void setupMainLayout() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    add(mainPanel);

    setupHeaderPanel();
    setupToolsPanel();
    setupLayersPanel();
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
    mainPanel.add(layerScrollPane);
  }

  private void setupExportButton() {
    JButton export = createButtonWithListener("Export", "EXPORT", (ActionEvent e) -> {
      Object blendType = JOptionPane.showInputDialog(
          this, "Choose Blending Type", "Blend", JOptionPane.PLAIN_MESSAGE, null, BLEND_TYPES, BLEND_TYPES[0]);
      replaceOrAddToCommandArgs("blend", blendType.toString());
    });
    headerPanel.add(export);
  }

  private void setupExportAsButton() {
    JButton exportAs = createButtonWithListener("Export As", "EXPORTAS", (ActionEvent e) -> {
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
    });
    headerPanel.add(exportAs);
  }

  private void setupLoadButton() {
    JButton open = createButtonWithListener("Open", "LOAD", (ActionEvent e) -> {
      int choseFile = fileChooser.showOpenDialog(this);
      if (choseFile == JFileChooser.APPROVE_OPTION) {
        String f = fileChooser.getSelectedFile().getAbsolutePath();
        replaceOrAddToCommandArgs("filename", f);
      }
    });
    headerPanel.add(open);
  }

  private void setupNewFileButton() {
    JButton newFile = createButtonWithListener("New", "NEWFILE", (ActionEvent e) -> {
        String width = JOptionPane.showInputDialog("Enter the width");
        String height = JOptionPane.showInputDialog("Enter the height");
        replaceOrAddToCommandArgs("width", width);
        replaceOrAddToCommandArgs("height", height);
    });
    headerPanel.add(newFile);
  }


  private void setupSaveButton() {
    JButton save = createSimpleButton("Save", "SAVE");
    headerPanel.add(save);
  }

  private void setupSaveAsButton() {
    JButton saveAs = createButtonWithListener("Save As", "SAVEAS", (ActionEvent e) -> {
      int choseFile = fileChooser.showOpenDialog(this);
      if (choseFile == JFileChooser.APPROVE_OPTION) {
        String rootDir = fileChooser.getSelectedFile().getAbsolutePath();
        String name = JOptionPane.showInputDialog("Select a name");
        replaceOrAddToCommandArgs("filename", rootDir + "/" + name);
      }
    });
    headerPanel.add(saveAs);
  }

  private void setupLayerAddButtons() {
    JButton addLayer = createButtonWithListener("New Layer", "ADDLAYER", (ActionEvent e) -> {
      String layername = JOptionPane.showInputDialog("Name your layer");
      replaceOrAddToCommandArgs("layername", layername);
      replaceOrAddToCommandArgs("filename", null);
    });

    JButton loadLayer = createButtonWithListener("Load Layer", "ADDLAYER", (ActionEvent e) -> {
      int choseFile = fileChooser.showOpenDialog(this);
      if (choseFile == JFileChooser.APPROVE_OPTION) {
        String filename = fileChooser.getSelectedFile().getAbsolutePath();
        String layername = JOptionPane.showInputDialog("Name your layer");
        replaceOrAddToCommandArgs("filename", filename);
        replaceOrAddToCommandArgs("layername", layername);
      }
    });
    toolsPanel.add(addLayer);
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

  private void replaceOrAddToCommandArgs(String key, String val) {
    if (commandArgs.containsKey(key)) {
      commandArgs.replace(key, val);
    } else {
      commandArgs.put(key, val);
    }
  }

  private JButton createButtonWithListener(String title, String actionCommand,
      ActionListener f) {
    JButton button = createSimpleButton(title, actionCommand);
    button.addActionListener(f);
    return button;
  }

  private JButton createSimpleButton(String title, String actionCommand) {
    JButton button = new JButton(title);
    button.addActionListener(listener);
    button.setActionCommand(actionCommand);
    return button;
  }

  @Override
  public void setImage(BufferedImage image) {

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
