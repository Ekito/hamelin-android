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
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.InvocationTargetException;


public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "HAMELIN";


    private SensorManager mSensorManager;

    private Sensor sensorAccelerometer;

    private WebView webview;

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

        webview = (WebView) findViewById(R.id.webView);
        // webview js enable
        WebSettings settings = webview.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);

        // lock webview touch
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mSensorManager.registerListener(this, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");

        waitConnectionTask = new WaitConnectionTask(this);
        waitConnectionTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");

        waitConnectionTask.cancel(true);

        if (webview != null) {
            // for stopping js from web site
            webview.loadUrl("about:blank");
        }
        else{
            Log.i(TAG,"webview is null for stop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
        mSensorManager.unregisterListener(this);
    }

    public void onclick_live(View view) {
        Log.i(TAG, "going live !");

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
