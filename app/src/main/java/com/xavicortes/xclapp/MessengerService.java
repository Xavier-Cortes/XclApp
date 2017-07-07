package com.xavicortes.xclapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.*;

/**
 * Created by Xavi on 06/07/2017.
 */

public class MessengerService extends Service {
    /** Command to the service to display a message */
    static final int MSG_MARCHA = 1;
    static final int MSG_PAUSA = 2;
    static final int MSG_PARO = 3;

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_MARCHA:


                    break;
                case MSG_PAUSA:


                    break;
                case MSG_PARO:


                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}