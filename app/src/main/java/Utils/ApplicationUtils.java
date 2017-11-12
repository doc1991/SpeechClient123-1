package Utils;

import android.content.Context;
import Applications.CallTel;
import Applications.MapsIntent;
import Applications.MediaIntents;

/**
 * Created by bill on 11/7/17.
 */

public class ApplicationUtils {


    public static String Selection(String application, String search, Context context) {


        switch (application) {
            case "play_video":
                return MediaIntents.newYoutube(search, context);
            case "make_call":
                return CallTel.TriggerCall(search, context);
            case "directions":
                return MapsIntent.GoogleMaps(search,context);
            case "play_music":
                return MediaIntents.MusicPlayer(search,context);
            default:
                return "Λάθος εντολή";
        }
    }

}
