package opensecurity.ajin.sslpinning;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.thoughtcrime.ssl.pinning.util.PinningHelper;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends ActionBarActivity {
    public Button request;
    public Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        request=(Button)findViewById(R.id.button);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String[] pins                 = new String[] {"87dbd45fb0928d4e1df81567e7f2abafd62b6775"}; //Valid till 28-01-2028
                    URL url                       = new URL("https://en.wikipedia.org/wiki/Main_Page");
                    HttpsURLConnection connection = PinningHelper.getPinnedHttpsURLConnection(getBaseContext(), pins, url);
                   byte[] data = new byte[4096];
                    connection.getInputStream().read(data);

                    Log.i("SSL-PINNING", "Success No MITM" + data);

                }
                catch(Exception e)
                {


                    Log.i("SSL-PINNING", " MITM Detected!!!!!: " + e.getMessage().toString());
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
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
