package com.saydur.ai;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int REQUEST_CALL_SCREENING = 100;
    private static final int REQUEST_NOTIFICATIONS = 101;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        showHome();
        requestNotificationPermissionIfNeeded();
    }

    private void showHome() {
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(36, 56, 36, 36);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setBackgroundColor(0xFF0D1B2A);

        TextView title = new TextView(this);
        title.setText("SAYDUR AI");
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 8);
        page.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("YOUR PERSONAL AI ASSISTANT\\nবাংলা • English");
        subtitle.setTextColor(0xFF9CCBE8);
        subtitle.setTextSize(15);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 32);
        page.addView(subtitle);

        status = new TextView(this);
        status.setTextColor(0xFFFFFFFF);
        status.setTextSize(16);
        status.setText(getStatusText());
        status.setPadding(0, 16, 0, 24);
        page.addView(status);

        Button enable = new Button(this);
        enable.setText("Enable incoming-call alerts");
        enable.setOnClickListener(v -> requestCallScreeningRole());
        page.addView(enable);

        Button refresh = new Button(this);
        refresh.setText("Refresh status");
        refresh.setOnClickListener(v -> status.setText(getStatusText()));
        page.addView(refresh);

        TextView note = new TextView(this);
        note.setText("\\nPhase 1: call identification + notification.\\nAI does not answer calls in this version.\\nCloud AI and WhatsApp calling are not connected yet.");
        note.setTextColor(0xFFB8C7D3);
        note.setTextSize(13);
        note.setPadding(0, 28, 0, 0);
        page.addView(note);

        setContentView(page);
    }

    private String getStatusText() {
        boolean active = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager rm = (RoleManager) getSystemService(Context.ROLE_SERVICE);
            active = rm != null && rm.isRoleHeld(RoleManager.ROLE_CALL_SCREENING);
        }
        String last = getSharedPreferences("saydur", MODE_PRIVATE)
                .getString("last_call", "No calls captured yet");
        return "Call alert permission: " + (active ? "Enabled" : "Not enabled")
                + "\\n\\nLatest call: " + last;
    }

    private void requestCallScreeningRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager rm = (RoleManager) getSystemService(Context.ROLE_SERVICE);
            if (rm != null && rm.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)
                    && !rm.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                startActivityForResult(
                        rm.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING),
                        REQUEST_CALL_SCREENING
                );
            } else if (rm != null && rm.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                status.setText(getStatusText());
            }
        }
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33 &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                        != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATIONS);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    SaydurCallScreeningService.CHANNEL_ID,
                    "SAYDUR AI call alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Alerts when a new SIM call is received");
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (status != null) status.setText(getStatusText());
    }
}
