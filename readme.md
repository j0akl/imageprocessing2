# Image Processing

Jake Lynn and Maya Janicki

## Design Explaination

Our model is separated into 3 parts, Pixels, Images, and Operations.

The Pixels contain data about individual pixels, they are pretty self explainatory.

Images contain data about images. The grid of pixels that makes up and image
is created by a List<List<Pixel>> that allows us to easily access
a pixel at a specific coordinate.

There are two ways to save an image. When an image is loaded, its filename is stored
as a field on the BasicImage object. If the save method is used, the file with the
name stored in BasicImage.filename is overwritten. If the saveAs method is used,
a new file is created (unless one with the same name already exists), and the image
is saved there. The new filename is also saved in the object.

Loading an image is done using the readPPM method given to us in the assignment.

The apply method in Image takes an Operation, and sends the image object to the operation
to be manipulated. The Operation takes a copy of the grid, manipulates it, and returns the
changed grid. This grid is then assigned to the grid field of the image, and the operation
is added to the history field of the Image. We expect to add an undo and redo method
in some later assignment, so we decided to add that now.

We tried to abstract as much functionality out of each operation as possbile. The Kernal operations
will work for any MxM kernal (so far as we have tested). The color operations will also work
for any size matrix. We decided to abstract matrix multiplication out to a static util matmul.

Clamp was also sent to a static util, it clamps the value of a double[] to [0, 255].

## Explaination for week 2

We decided that the easiest way to represent layered images would be to store a map
of BasicImages (now BasicLayers) with their names. The layered images can be saved to a directory,
loaded, layers can be added by adding to the map, removed, etc. We decided that, from a GUI standpoint,
it made the most sense for the client to select a layer and operate on it, rather than specifying
the layer during each operation. This will make it easier to tell what buttons do, and what is being
manipulated.

We decided to add an export function that flattens the layers, either by adding or averaging,
and saves the result to its own image file rather than a directory. This mimics the behavior of applications
like Photoshop, which can save both .psd and other image formats.

All File I/O is handled by static methods. This is better than having the operations directly
controlled by the model.

## Explaination for Week 3

We used the command pattern to send commands from the view to the controller to the model.
For each operation, a command object was created that would delegate the work to the model.
The view would keep the name of the command as the actionCommand on each element so the controller
(which was the action listener) could decode the commands and execute them.

The same script files from last week should work as long as the jar is launched from the res/ folder.
Run the first script, it will run the second one by itself.

## Changelog
### Week 2

- BasicImage became Layer
  - BasicImage is basically a layer already, just changed the name
- Added visibility functionality to Layer
- Changed getPixels to return a deep copy of the pixels
- Added the copy() method to the layer class
- added .equals and .hashCode to pixel
- added a constructor to layer to take pixels
- moved file i/o to a static method (from the model)

### Week 3

- Added a BlendType, topmostlayer. Gets the topmost layer for exporting.
- Made the flatten methods on LayeredImage public to allow for easier viewing.
- Added a field to layeredImage to keep track of the export name for a file
- Changed the name of LayeredImage to IPModel for consistency
- added getLayerNames to IPImage

## Citations

Both images taken from:
<a>https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html</a>
