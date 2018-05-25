package org.esiea.beyaztunc_cayol.holdmybeer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SECOND ACTIVITY";
    IntentFilter intentFilter ;
    BierAdapter ba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        intentFilter = new IntentFilter(MainActivity.BEER_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BeerUpdate(), intentFilter);

        Intent intent = new Intent(this, GetBeerServices.class);
        intent.setAction(MainActivity.BEER_UPDATE);
        this.startService(intent);

        RecyclerView rv_beer = (RecyclerView)findViewById(R.id.rv_beer);
        rv_beer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ba = new BierAdapter(getBIERSFromFile());
        rv_beer.setAdapter(ba);

    }


    public class BeerUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, intent.getAction());
            Toast.makeText(getApplicationContext(), R.string.dwl, Toast.LENGTH_SHORT).show();
            ba.setNewBiere(getBIERSFromFile());
        }
    }

    private class BierAdapter extends RecyclerView.Adapter<BierAdapter.BierHolder> {

        private JSONArray biers;

        public BierAdapter(JSONArray biers) {
            this.biers = biers;
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            BierHolder bh = new BierHolder(inflater.inflate(R.layout.rv_beer_element, parent, false));
            return bh;
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                holder.name.setText(biers.getJSONObject(position).get("name").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {

            return biers.length();
        }

        public class BierHolder extends RecyclerView.ViewHolder {
            TextView name;

            public BierHolder(View itemView) {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.rv_beer_element_name);
            }
        }

        public void setNewBiere(JSONArray biers) {
            this.biers = biers;
            notifyDataSetChanged();
        }
    }


    public JSONArray getBIERSFromFile() {
        try{
            InputStream in = new FileInputStream(getCacheDir() + "/" + "biers.json");
            byte[] buf = new byte[in.available()];
            in.read(buf);
            in.close();
            return new JSONArray(new String(buf, "UTF-8"));
        } catch(IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
