package fr.ekito.hamelin;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

public class OfflineConnectionTask extends AsyncTask<String, Boolean, String> {

    private static final String TAG = "HAMELIN-OfflineConnection";
    private final WebView webView;

    private Activity context;

    private String url;

    public OfflineConnectionTask(Activity c, WebView webView) {
        this.context = c;
        this.webView = webView;
    }

    protected String doInBackground(String... args) {
        boolean check = checkConnectivity();
        Log.i(TAG, "check offline site : "+check);
        publishProgress(check);
        return null;
    }

    /**
     * Check network and update url
     */
    private boolean checkConnectivity() {
        boolean checked = false;
        // test wifi url
        url = context.getString(R.string.internet_url);
        checked = NetworkTools.checkWebSite(url);
        return checked;
    }


    @Override
    protected void onProgressUpdate(Boolean... v) {
        if(v[0]) {
            webView.stopLoading();
            webView.loadUrl(url);
        } else{
            Log.i(TAG, "show offline view");
            // launch offline view
            Intent i = new Intent(context, OfflineActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivityForResult(i, 1);
        }
    }
}
