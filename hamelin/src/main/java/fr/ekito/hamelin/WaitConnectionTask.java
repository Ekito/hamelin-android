package fr.ekito.hamelin;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
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
        Log.i(TAG, "begin check");
        boolean check = checkConnectivity();
        Log.i(TAG, "end check");
        publishProgress(check);
        return null;
    }

    /**
     * Check network and update url
     */
    private boolean checkConnectivity() {
        boolean checked = false;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.i(TAG, "check wifi : "+mWifi.isConnected());
        if (mWifi.isConnected()) {
            // test wifi url
            url = context.getString(R.string.wifi_url);
            checked = NetworkTools.checkWebSite(url);

            if (!checked) {
                Log.i(TAG, "get internet url");
                url = context.getString(R.string.internet_url);
            }
        }else{
            Log.i(TAG, "no wifi. get internet url");
            url = context.getString(R.string.internet_url);
        }

        return checked;
    }


    @Override
    protected void onProgressUpdate(Boolean... v) {
        webView = (WebView) context.findViewById(R.id.webView);
        if (webView != null) {
            try {
                Log.i(TAG, "stop loading ...");
                webView.stopLoading();
                Log.i(TAG, "load web page : " + url);
                webView.loadUrl(url);
                if (!v[0]) {
                    OfflineConnectionTask offlineCheck = new OfflineConnectionTask(context,webView);
                    offlineCheck.execute();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error onProgressUpdate ", e);
                IntentTools.quitToHome(context);
            }
        }
        else{
            Log.e(TAG, "Webview is null");
        }
    }
}
