package technology.tylersprojects.casper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        Log.i("Casper", "app is getting destroyed");
        super.onDestroy();
    }
}
