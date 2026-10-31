package com.saydur.ai;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout chatLayout;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(20, 20, 20, 20);
        main.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("SAYDUR AI");
        title.setTextSize(28);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        main.addView(title);

        ScrollView scroll = new ScrollView(this);
        chatLayout = new LinearLayout(this);
        chatLayout.setOrientation(LinearLayout.VERTICAL);
        chatLayout.setPadding(5, 20, 5, 20);
        scroll.addView(chatLayout);

        main.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1
        ));

        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);

        input = new EditText(this);
        input.setHint("এখানে লিখুন...");
        bottom.addView(input, new LinearLayout.LayoutParams(
                0, -2, 1
        ));

        Button send = new Button(this);
        send.setText("Send");
        bottom.addView(send);

        main.addView(bottom);

        send.setOnClickListener(v -> {
            String message = input.getText().toString().trim();

            if (!message.isEmpty()) {
                addMessage("আপনি: " + message);

                addMessage("SAYDUR AI: আপনার মেসেজ পেয়েছি। "
                        + "AI সংযোগ এখনো তৈরি করা হয়নি।");

                input.setText("");
            }
        });

        setContentView(main);
        addMessage("SAYDUR AI-তে স্বাগতম!");
    }

    void addMessage(String message) {
        TextView text = new TextView(this);
        text.setText(message);
        text.setTextSize(17);
        text.setTextColor(Color.BLACK);
        text.setPadding(10, 12, 10, 12);
        chatLayout.addView(text);
    }
}
