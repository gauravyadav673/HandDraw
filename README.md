# HandDraw
Android library to add a touch to draw widget to your android application.
![img1](https://user-images.githubusercontent.com/22410153/31006432-46943664-a51a-11e7-8aae-9ae61f0f9ac6.png) 
![img2](https://user-images.githubusercontent.com/22410153/31006480-6e73f098-a51a-11e7-8d90-763256f3088c.png)


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
    compile 'com.github.gauravyadav673:HandDraw:v1.0'
    
## Add HandDraw to layout
    <com.raodevs.touchdraw.TouchEventsView
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
    <com.raodevs.touchdraw.TouchEventsView
        android:id="@+id/canvas"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:text_color="#ffffff"
        app:text_size="10f"
        app:bg_color="#000000"/>
        
# Access hand draw from java
    TouchEventsView touchEventsView; //declaring
    //put below line inside the onCreate() method
    touchEventsView = (TouchEventsView)findViewById(R.id.canvas); //defining
    
After addig this two your project you can enjoy your custom handDraw view.
