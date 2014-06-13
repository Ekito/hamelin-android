package fr.ekito.hamelin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;


public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "HAMELIN";


    private SensorManager mSensorManager;

    private Sensor sensorAccelerometer;

    private WebView webview;

    private String url = "";
    private Menu menu;
    private WaitConnectionTask waitConnectionTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sensor management
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mSensorManager.registerListener(this, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        waitConnectionTask = new WaitConnectionTask(this);
        waitConnectionTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();

        waitConnectionTask.cancel(true);
        webview = (WebView) findViewById(R.id.webView);
        if (webview != null) {
            webview.stopLoading();
//            webview.destroy();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        webview = (WebView) findViewById(R.id.webView);
        if (webview != null) {
//            webview.stopLoading();
            webview.destroy();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    public void onclick_live(View view){
        Log.i(TAG,"going live !");

        finish();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //do nothing
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing
    }
}
