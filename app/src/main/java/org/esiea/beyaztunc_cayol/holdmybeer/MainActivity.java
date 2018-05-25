package org.esiea.beyaztunc_cayol.holdmybeer;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    TextView txt;
    DatePickerDialog dpd;
    NotificationManager notMan;
    NotificationCompat.Builder builder;

    public static final String BEER_UPDATE = "org.esiea.beyaztunc_cayol.holdmybeer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView)findViewById(R.id.textBox);
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
                Date date = new Date(i-1900, i1, i2);
                txt.setText(dateFormatter.format(date));
            }
        };
        dpd = new DatePickerDialog(this, odsl, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(getString(R.string.ctnt));
        builder.setContentTitle(getString(R.string.ttre));
        notMan = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public void doBtn(View v) {
        txt.setText(R.string.clc);
        dpd.show();
    }

    public void doBtnBack(View v) {
        txt.setText(R.string.textBoxTxt);
        Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_SHORT).show();
        notMan.notify(500, builder.build());
    }

    public void openMap(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=ESIEA")));
    }

    public void changeIntent(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toast_me) {
            Toast.makeText(getApplicationContext(), getString(R.string.roastme), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toast_that) {
            Toast.makeText(getApplicationContext(), getString(R.string.roastThat), Toast.LENGTH_SHORT).show();
        } if (id == R.id.toast_bread) {
            Toast.makeText(getApplicationContext(), getString(R.string.roastBread), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
