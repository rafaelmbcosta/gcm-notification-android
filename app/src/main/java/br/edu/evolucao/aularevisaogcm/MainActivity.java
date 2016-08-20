package br.edu.evolucao.aularevisaogcm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class MainActivity extends Activity {
    GoogleCloudMessaging gcm;
    private static final String key = "PROJECT KEY HERE";
    EditText message;
    String register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (EditText)findViewById(R.id.mensagem);
        if (checkPlayServices()){
            message.setText("Working fine");
            gcm = GoogleCloudMessaging.getInstance(this);
            getKey();
        }
    }

    private void getKey() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    register = gcm.register(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "Key aquired: "+register;
            }

            @Override
            protected void onPostExecute(String s) {
                //SEND TO BACKEND
                message.setText(s);

            }
        }.execute();
    }




    private boolean checkPlayServices(){
        boolean servicesAvailable = true;
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (result != ConnectionResult.SUCCESS){
            servicesAvailable = false;
            Toast.makeText(getApplicationContext(), "not available", Toast.LENGTH_LONG).show();
        }
        return servicesAvailable;
    }

}
