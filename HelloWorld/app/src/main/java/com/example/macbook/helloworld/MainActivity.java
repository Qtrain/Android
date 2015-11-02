package com.example.macbook.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;  //https://developer.android.com/reference/android/os/Bundle.html
import android.view.Menu;  //https://developer.android.com/reference/android/view/Menu.html
import android.view.MenuItem;  //https://developer.android.com/reference/android/view/MenuItem.html
import android.view.View;
import android.widget.EditText;
import android.widget.Button;




/******************************************************************************
 * The <CODE>Main Activity</CODE> is the program entry point and the actual application file which ultimately gets converted to a Dalvik executable and runs your application.
 * <p>
 * <p><dt><b>Java Source Code for this class:</b><dd>
 * <a HREF=""></a>
 ******************************************************************************/

public class MainActivity extends AppCompatActivity {


    /**
     * @param <CODE>savedInstanceState</CODE> <p>The savedInstanceState is a reference to a <a style="color:blue;" href="http://developer.android.com/reference/android/os/Bundle.html" target="_blank">Bundle</a> object that is passed into the <a style="color:blue;" href="http://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)" target="_blank">onCreate</a> method of every Android <a style="color:blue;" href="http://developer.android.com/reference/android/app/Activity.html" target="_blank">Activity</a>. Activities have the ability, under special circumstances, to restore themselves to a previous state using the data stored in this bundle. If there is no available instance data, the savedInstanceState will be null. For example, the savedInstanceState will always be null the first time an Activity is started, but may be non-null if an Activity is destroyed during rotation.</p><br>
     *                                        <a href="https://developer.android.com/reference/android/app/Activity.html>Reference</a>
     * @Override
     **/
    protected void onCreate(Bundle savedInstanceState) {  //onCreate initializes activity
        super.onCreate(savedInstanceState); //super to override
        setContentView(R.layout.activity_main); //set content with layout resource defining UI

        //R reference https://developer.android.com/reference/android/R.html
        //layout https://developer.android.com/reference/android/text/Layout.html
        //activity main is xml  layout -->
        //                            activity_main.xml
    }

    /**
     * @param <CODE>menu</CODE> <p>Initialize the contents of the Activity's standard options menu.  You
     *                          should place your menu items in to <var>menu</var>.
     *                          <p>
     *                          </p><p>This is only called once, the first time the options menu is
     *                          displayed.  To update the menu every time it is displayed, see
     *                          <code><a href="/reference/android/app/Activity.html#onPrepareOptionsMenu(android.view.Menu)">onPrepareOptionsMenu(Menu)</a></code>.
     *                          <p>
     *                          </p><p>The default implementation populates the menu with standard system
     *                          menu items.  These are placed in the <code><a href="/reference/android/view/Menu.html#CATEGORY_SYSTEM">CATEGORY_SYSTEM</a></code> group so that
     *                          they will be correctly ordered with application-defined menu items.
     *                          Deriving classes should always call through to the base implementation.
     *                          <p>
     *                          </p><p>You can safely hold on to <var>menu</var> (and any items created
     *                          from it), making modifications to it as desired, until the next
     *                          time onCreateOptionsMenu() is called.
     *                          <p>
     *                          </p><p>When you add items to the menu, you can implement the Activity's
     *                          <code><a href="/reference/android/app/Activity.html#onOptionsItemSelected(android.view.MenuItem)">onOptionsItemSelected(MenuItem)</a></code> method to handle them there.</p>
     *                          <a href="https://developer.android.com/reference/android/app/Activity.html">Reference</a>
     * @Override
     **/

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * @param <CODE>item</CODE> <p>This hook is called whenever an item in your options menu is selected.</p>
     *                          <p>The default implementation simply returns false to have the normal
     *                          processing happen (calling the item's Runnable or sending a message to
     *                          its Handler as appropriate).  You can use this method for any items
     *                          for which you would like to do processing without those other
     *                          facilities.
     *                          </p><p>Derived classes should call through to the base class for it to
     *                          perform the default menu handling.</p><p></p></div>
     *                          <h5>Returns</h5>
     *                          <p>boolean Return false to allow normal menu processing to
     *                          proceed, true to consume it here.</p>
     *                          <a href="https://developer.android.com/reference/android/view/Menu.html">Reference</a>
     * @Override
     **/
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}





