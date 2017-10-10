# HandDraw
Android library to add a touch to draw widget to your android application.
It can also be used for capturing signatures
<img src="https://user-images.githubusercontent.com/22410153/31006432-46943664-a51a-11e7-8aae-9ae61f0f9ac6.png" width="300" height=auto>
<img src="https://user-images.githubusercontent.com/22410153/31006480-6e73f098-a51a-11e7-8d90-763256f3088c.png" width="300" height=auto/>


# Support
Minimum sdk is 15.
# Add With Gradle Dependency
## Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
## Add the dependency
    compile 'com.github.gauravyadav673:HandDraw:v1.02'
    
## Add HandDraw to layout
    <com.raodevs.touchdraw.TouchDrawView
        android:id="@+id/canvas"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
You can resize and position this view according to your needs.
## Customization Through XML
    <!--Change paint Color-->
        app:text_color="#ffffff"
    <!--Change stroke size -->
        app:text_size="10f"
    <!--Change background color-->
        app:bg_color="#000000"
## Example : Full Customization
    <com.raodevs.touchdraw.TouchDrawView
        android:id="@+id/canvas"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:text_color="#ffffff"
        app:text_size="10f"
        app:bg_color="#000000"/>
        
# Access hand draw from java
    TouchDrawView touchDrawView; //declaring
    //put below line inside the onCreate() method
    touchDrawView = (TouchDrawView)findViewById(R.id.canvas); //defining
    
## Developers can interact more by using following code
    touchDrawView.setPaintColor(Color.MAGENTA);// for changing paint color
    touchDrawView.setBGColor(Color.BLUE);// for changing background color
    touchDrawView.setStrokeWidth(20f);// for changing stroke width
	///****//
	touchDrawView.clear();//for clearing the whole view(can be redone by using redo function)
    touchDrawView.undo();//for undoing last stroke
	touchDrawView.redo();//for redoing latest changes
	///***///
	touchDrawView.saveFile(folderName, fileName)//saves the current view in .jpeg format
	touchDrawView.getFile()//returns current view in Bitmap format
After adding this to your project you can enjoy your custom handDraw view.
