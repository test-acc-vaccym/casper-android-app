package technology.tylersprojects.casper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listData(View v) {
        ExecuteCommand.launchService(this, ExecuteCommand.Command.GET_CONTACTS);
        /*
        // Note if you call SU then SH the SH will prob run with SU priv so always run it in new intent
        List<String> resp = Shell.SU.run("lss");
        // on fail the resp size is zero!!
        if(resp.size() == 0) {
            Log.i(TAG, "its zero");
            Shell.SU.clearCachedResults();
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<resp.size(); i++) {
            sb.append(resp.get(i)).append("\n");
        }
        ((TextView) findViewById(R.id.output)).setText(sb.toString());
        resp = Shell.SU.run("ls /data/data/com.sec.knox.switcher/");
        if(resp.size() == 0) {
            Log.i(TAG, "its zero");
        }
        for(int i=0; i<resp.size(); i++) {
            sb.append(resp.get(i)).append("\n");
        }
        Log.i(TAG, sb.toString());
        */
    }
}
