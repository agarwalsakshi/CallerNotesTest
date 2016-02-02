package android.cybrilla.com.callernotestest;

/**
 * Created by sakshiagarwal on 01/02/16.
 */
import android.cybrilla.com.callernotestest.StandOutWindow;
import android.app.Activity;
import android.os.Bundle;

public class StandOutExampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StandOutWindow.closeAll(this, MultiWindow.class);

        StandOutWindow.show(this, MultiWindow.class, StandOutWindow.DEFAULT_ID);

        finish();
    }
}