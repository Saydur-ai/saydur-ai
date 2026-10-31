package com.saydur.ai;

import android.telecom.Call;
import android.telecom.CallScreeningService;

public class SaydurCallScreeningService extends CallScreeningService {

    @Override
    public void onScreenCall(Call.Details details) {

        CallResponse response = new CallResponse.Builder()
                .setDisallowCall(false)
                .setRejectCall(false)
                .setSkipCallLog(false)
                .setSkipNotification(false)
                .build();

        respondToCall(details, response);
    }
}
