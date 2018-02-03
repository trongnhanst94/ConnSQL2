package com.example.windows10gamer.connsql.Other;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Ban_Hang.Main_Doanhthu;
import com.example.windows10gamer.connsql.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by EVRESTnhan on 1/4/2018.
 */

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Từ: " + remoteMessage.getFrom());

        if(remoteMessage.getNotification() != null){
            Toast.makeText(this, "Nội dung thông báo: " + remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            createNotification(remoteMessage.getNotification().getBody());
        }
    }

    final public void createNotification( String messageBody) {
        Intent intent = new Intent( this , Main_Doanhthu. class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Test FCM")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
