package technology.tylersprojects.casper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void listData(View v) {
        ExecuteCommand.launchService(this, ExecuteCommand.Command.WIPE);
    }
}
