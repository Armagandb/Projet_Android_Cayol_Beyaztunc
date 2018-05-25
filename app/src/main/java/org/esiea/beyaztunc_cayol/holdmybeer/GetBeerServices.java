package org.esiea.beyaztunc_cayol.holdmybeer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetBeerServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "org.esiea.beyaztunc_cayol.holdmybeer.action.FOO";
    private static final String TAG = "Mmmmh Beeeer!!";

    public GetBeerServices() {

        super("GetBeerServices");
    }

    public static void startActionFoo(Context context) {
        Intent intent = new Intent(context, GetBeerServices.class);
        intent.setAction(ACTION_FOO);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (MainActivity.BEER_UPDATE.equals(action)) {
                handleActionBeer();
            }
        }
    }

    public void handleActionBeer() {
        Log.d(TAG, "Thread service name: " + Thread.currentThread().getName());
        URL url = null;

        try {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "biers.json"));
                Log.d(TAG, "We got 'em beers!");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.BEER_UPDATE));
            } else {
                Log.e(TAG, "CONNECTION ERROR" + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf,0,len);
            }
            out.close();
            in.close();
            Log.d(TAG, "Finished loading this file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleActionFoo() {
        Log.d(TAG, "handled foo");
    }

}

