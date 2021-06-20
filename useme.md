# Commands for Image Processing

## Rules
All commands are lowercase.
The end of a command group (a set of words that complete a single action)
should end in a semicolon (;).

## Commands

To quit the program: Type q at any time.

To create a new layer: 
- Begin the command with "create".
- if creating an empty layer, provide a name followed by a semicolon.
- if creating a layer from an image, provide the layername,
followed by a space, followed by the location of the image to load,
followed by a semicolon.
- Example: "create layer1 res/snail.ppm;".

To load a LayeredImage:
- Begin with "load".
- Follow load with the location of the directory to load from and a semicolon.
- Example: "load res/snaillayers;"

This will replace the image you are currently working on. Be sure to save first!

To select a Layer:
- Begin a line with "select".
- Follow that with the name of the layer, and a semicolon.
- Example: "select layer1;"

To export an already exported LayeredImage:
- Begin with "export".
- Follow with the type of flatten, avg or add.

To export the LayeredImage:
- Begin with "exportas".
- Follow with the type of flatten to use, either avg or add.
- Finish with the filename and a semicolon.
- Example: "exportas avg filename.png"

To copy a layer:
- Begin with "copy"
- Follow with the name for the copied layer and a semicolon. Will
  copy the currently selected layer.

To remove a Layer:
- Remove layer with "remove;".

To execute a script from a file:
- "exec" followed by the name of the file and a semicolon
- Example: "exec commands.txt;"
