package com.saydur.ai;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.telecom.Call;
import android.telecom.CallScreeningService;

import java.text.DateFormat;
import java.util.Date;

public class SaydurCallScreeningService extends CallScreeningService {
    public static final String CHANNEL_ID = "saydur_call_alerts";

    @Override
    public void onScreenCall(Call.Details details) {
        // Always allow the call through; this app only identifies and notifies.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                && details.getCallDirection() == Call.Details.DIRECTION_INCOMING) {
            String number = "Unknown number";
            Uri handle = details.getHandle();
            if (handle != null && handle.getSchemeSpecificPart() != null) {
                number = handle.getSchemeSpecificPart();
            }
            String time = DateFormat.getDateTimeInstance().format(new Date());
            getSharedPreferences("saydur", MODE_PRIVATE)
                    .edit().putString("last_call", number + " • " + time).apply();
            showCallNotification(number);
        }

        CallResponse response = new CallResponse.Builder()
                .setDisallowCall(false)
                .setRejectCall(false)
                .setSilenceCall(false)
                .build();
        respondToCall(details, response);
    }

    private void showCallNotification(String number) {
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "SAYDUR AI call alerts",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        Intent open = new Intent(this, MainActivity.class);
        PendingIntent pending = PendingIntent.getActivity(
                this, 0, open,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? new Notification.Builder(this, CHANNEL_ID)
                : new Notification.Builder(this);

        Notification notification = builder
                .setSmallIcon(android.R.drawable.sym_call_incoming)
                .setContentTitle("SAYDUR AI • Incoming SIM call")
                .setContentText("Caller: " + number)
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Incoming call detected. Caller: " + number
                                + ". Your phone will ring normally; SAYDUR AI does not answer."))
                .setContentIntent(pending)
                .setAutoCancel(true)
                .build();

        manager.notify((int) System.currentTimeMillis(), notification);
    }
}
