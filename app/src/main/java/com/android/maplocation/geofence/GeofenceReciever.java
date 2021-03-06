package com.android.maplocation.geofence;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.android.maplocation.R;
import com.android.maplocation.login.LoginActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

/**
 * Created by Daman on 12/19/2017.
 */

public class GeofenceReciever extends BroadcastReceiver {
    public static final String TAG = "GeofecneReceiver";
    Context context;
    Intent broadcastIntent = new Intent();
    private static final String CHANNEL_ID = "channel_01";
    private TextView checkin,checkout;

    public GeofenceReciever() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        this.context = context;
        Log.d(TAG, "onReceive");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            handleError(intent);
        } else {
            handleEnterExit(geofencingEvent);
        }
    }

    private void handleError(Intent intent) {
        // Get the error code
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        String errorMessage = GeofenceErrorMessages.getErrorString(context,
                geofencingEvent.getErrorCode());

        // Log the error
        Log.e(TAG, "Geofence handleError:" + errorMessage);

        // Set the action and error message for the broadcast intent
        broadcastIntent

                .putExtra("GEOFENCE_STATUS", errorMessage);

        // Broadcast the error *locally* to other components in this app
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                broadcastIntent);
    }


    private void handleEnterExit(GeofencingEvent geofencingEvent) {
        Log.d(TAG, "handleEnterExit");

        // Get the type of transition (entry or exit)
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that a valid transition was reported
        if ((geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
                || (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)
               // || (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL)
        ) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();


            for (Geofence geofence : triggeringGeofences) {

                String geofenceTransitionString = getTransitionString(geofenceTransition);
                String geofenceText = geofenceTransitionString + " : " + geofence.getRequestId();
                Log.i(TAG, "Geofence Transition:" + geofenceText);

                sendEventDetailNotificatonIntent(geofenceText);


                // Create an Intent to broadcast to the app
                broadcastIntent

                        .putExtra("EXTRA_GEOFENCE_ID", geofence.getRequestId())
                        .putExtra("EXTRA_GEOFENCE_TRANSITION_TYPE", geofenceTransitionString);
                LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
            }
        } else {
            // Always log as an error
            Log.e(TAG,
                    context.getString(R.string.geofence_transition_invalid_type,
                            geofenceTransition));
        }
    }

    /*private String getNotificationTitle(String payload){
        String title=null;
        try {
            JSONObject json = new JSONObject(payload);
            if(json.has("title")) {
                title=(String) json.get("title");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return title;
    }
*/

    private String getMessage(String payload) {
        String message = null;
        try {
            JSONObject json = new JSONObject(payload);
            if (json.has("msg")) {
                message = (String) json.get("msg");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return context.getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return context.getString(R.string.geofence_transition_exited);
//            case Geofence.GEOFENCE_TRANSITION_DWELL:
//                return context.getString(R.string.geofence_transition_exited);
            default:
                return context.getString(R.string.unknown_geofence_transition);
        }
    }

    protected void sendEventDetailNotificatonIntent(String event_name) {
        Log.d(TAG, "sendEventDetailNotificatonIntent");

        int requestID = (int) System.currentTimeMillis();
        Intent event_detail_intent = new Intent(context, GetMapLocationActivity.class);
        event_detail_intent.putExtra("NotifyTitle", event_name);
        PendingIntent pIntent = PendingIntent.getActivity(context, requestID,
                event_detail_intent, 0);
        NotificationCompat.Builder notificationBuilder;
        notificationBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle(event_name)
                .setSmallIcon(R.drawable.ic_action_location);
        // Set pending intent
        notificationBuilder.setContentIntent(pIntent);

         //Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        //	defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        notificationBuilder.setDefaults(defaults);
        // Set the content for Notification
        notificationBuilder.setContentText("Please login and start your shift");
        // Set autocancel
        notificationBuilder.setAutoCancel(true);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Assign random number for multiple notification
        Random random = new Random();
        int randomNumber = random.nextInt(9999 - 1000) + 1000;


        if (notificationManager != null) {
            // Android O requires a Notification Channel.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = context.getString(R.string.app_name);
                // Create the channel for the notification
                NotificationChannel mChannel =
                        new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

                // Set the Notification Channel for the Notification Manager.
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(randomNumber /* ID of notification */, notificationBuilder.build());
        }

    }

  /*  // Create notification
    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_action_location)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID); // Channel ID
        }
        return notificationBuilder.build();
    }
*/
}

