package fr.ekito.hamelin;

import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class WaitConnectionTask extends AsyncTask<String, Boolean, String> {

	private static final String TAG = "HAMELIN-WaitConnection";

	private Activity context;

    private WebView webView;

    private String url;

	public WaitConnectionTask(Activity c) {
		this.context = c;
	}

	protected String doInBackground(String... args) {

        boolean check = checkConnectivity();
        publishProgress(check);
		return null;
	}

    /**
     * Check network and update url
     */
    private boolean checkConnectivity() {
        boolean checked = false;
        // test wifi url
        url = context.getString(R.string.wifi_url);
        checked = NetworkTools.checkWebSite(context,url);
        if (!checked){
            Log.i(TAG,"can't connect to wifi");
            url = context.getString(R.string.internet_url);
            checked = NetworkTools.checkWebSite(context,url);
            Log.i(TAG,"can't connect to internet site ? "+checked);
            // test internet url
        }
        return checked;
    }


	@Override
	protected void onProgressUpdate(Boolean... v) {
		if (v[0]) {
            webView = (WebView) context.findViewById(R.id.webView);
            if (webView != null) {

                // webview js enable
                webView.getSettings().setJavaScriptEnabled(true);

                // lock webview touch
                webView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                Log.i(TAG,"loading web page : "+url);
                webView.loadUrl(url);
            }
		}
        else{
            Log.i(TAG,"offline view");
            // launch offline view
            Intent i = new Intent(context, OfflineActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivityForResult(i, 1);
        }
	}
}
