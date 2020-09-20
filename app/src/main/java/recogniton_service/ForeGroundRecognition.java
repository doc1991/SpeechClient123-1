package recogniton_service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.Log;

import com.example.bill.Activities.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import applications.Constants;
import events.Events;

/**
 * Created by bill on 11/30/17.
 */

public class ForeGroundRecognition extends RecognitionService{

    private static final int NOTIFY_ID = 1;
    private final String TAG = this.getClass().getSimpleName();
    private final IBinder assistantBinder = new AssistantBinder();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //return start sticky because we dont want to close on exit of app

        if (intent != null) {
            String action = intent.getAction();
            Log.i(TAG,action);
        }

        Intent ConIntent = new Intent(Constants.NOTIFICATION_ACTION);
        PendingIntent ActionIntent = PendingIntent.getBroadcast(this, 4, ConIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        CreateNotification(ActionIntent);

        Log.i(TAG, "foreground started!!");

        return Service.START_STICKY;
    }


    //create notification load image and start service
    private void CreateNotification(PendingIntent ActionIntent) {
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this,"1");
        }
        else
        {
            builder = new Notification.Builder(this);
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_name);
        builder.setContentIntent(ActionIntent)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(bitmap)
                .setTicker(getResources().getString(R.string.Notification_Title))
                .setOngoing(true)
                .setContentTitle(getResources().getString(R.string.title_activity_gui))
                .setContentText(getResources().getString(R.string.Notification_Title));
        Notification not = builder.build();

        startForeground(NOTIFY_ID,
                not);

        Log.i(TAG, "notification created!!");


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //close notification and foreground service
        stopForeground(true);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getDefault().register(this);

        return assistantBinder;
    }

    public class AssistantBinder extends Binder {
        public ForeGroundRecognition getService() {
            return ForeGroundRecognition.this;
        }
    }


    @Subscribe
    public void isComputing(Events.ComputingRecognition event ) {

        if(event.isComputing()){
            ToastMessage("Παρακαλώ περιμένετε");
        }

    }
}