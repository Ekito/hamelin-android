package fr.ekito.hamelin;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by arnaud on 11/06/14.
 */
public class IntentTools {

    public static void quitToHome(Activity a) {
        a.finish();
        Intent i = new Intent(a, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.startActivity(i);
    }

}
