# HandDraw
Android library to add a touch-to-draw view to your Android application.
It can also be used for capturing signatures.

<img src="https://user-images.githubusercontent.com/22410153/31006432-46943664-a51a-11e7-8aae-9ae61f0f9ac6.png" width="300" height=auto>
<img src="https://user-images.githubusercontent.com/22410153/31006480-6e73f098-a51a-11e7-8d90-763256f3088c.png" width="300" height=auto/>

# Support
Minimum SDK is 21.

# Add With Gradle Dependency
## Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## Add the dependency
```groovy
implementation 'com.github.gauravyadav673:HandDraw:v1.02'
```

## Add HandDraw to layout
```xml
<com.raodevs.touchdraw.TouchDrawView
    android:id="@+id/canvas"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
You can resize and position this view according to your needs.

## Customization Through XML
```xml
<!-- Change paint color -->
app:paint_color="#ffffff"
<!-- Change stroke width -->
app:paint_width="10f"
<!-- Change background color -->
app:bg_color="#000000"
```

## Example: Full Customization
```xml
<com.raodevs.touchdraw.TouchDrawView
    android:id="@+id/canvas"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:paint_color="#ffffff"
    app:paint_width="10f"
    app:bg_color="#000000"/>
```

# Access HandDraw from Java
```java
TouchDrawView touchDrawView; // declare

// inside onCreate()
touchDrawView = (TouchDrawView) findViewById(R.id.canvas);
```

## Available API
```java
touchDrawView.setPaintColor(Color.MAGENTA); // change paint color
touchDrawView.setBGColor(Color.BLUE);       // change background color
touchDrawView.setStrokeWidth(20f);          // change stroke width

touchDrawView.clear(); // clear the whole view (can be redone)
touchDrawView.undo();  // undo last stroke
touchDrawView.redo();  // redo last undone stroke

touchDrawView.saveFile(folderName, fileName); // saves as .jpeg to app-scoped external storage
touchDrawView.getFile();                      // returns current view as a Bitmap

// Eraser
touchDrawView.setEraserMode(true);   // switch to eraser mode
touchDrawView.setEraserMode(false);  // switch back to draw mode (restores previous color & width)
touchDrawView.isEraserMode();        // returns true if eraser is currently active
touchDrawView.setEraserWidth(50f);   // set eraser stroke width (default 30f)
```

> **Note:** `saveFile()` saves to the app's scoped external storage directory
> (`Android/data/<package>/files/<folderName>/`). No `WRITE_EXTERNAL_STORAGE`
> permission is required on Android 10 and above.

## How can you contribute?
Contributors are most welcome. You can add comments to the code, report issues or bugs, or contribute on any of the following topics:
> 1) Implementing a color picker.
> 2) Adding more to this list
