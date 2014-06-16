package fr.ekito.hamelin;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by arnaud on 11/06/14.
 */
public class NetworkTools {

    private static final String TAG = "HAMELIN-NETTOOLS";

    public static boolean checkWebSite(String targetUrl) { // this is the downloader method
        boolean checkedSite = false;
        try {
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 5000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 3000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpGet httpget = new HttpGet(targetUrl);
            Log.e(TAG, "checkWebSite : " + targetUrl);
            HttpResponse response = httpclient.execute(httpget);
            int code = response.getStatusLine().getStatusCode();
            Log.e(TAG, "checkWebSite response : " + code);

            checkedSite = code >= 200 && code < 300;

        } catch (Throwable e) {
            Log.e(TAG, "checkWebSite error : " + e);
        }
        return checkedSite;
    }
}
