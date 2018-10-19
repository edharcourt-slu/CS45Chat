package edu.stlawu.chat;

import android.support.annotation.RequiresPermission;

import java.io.IOException;

public class Reader implements Runnable {

    private ConnectionManager connectionManager;

    public Reader(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {

        // TODO set connected to false somewhere
        while (connectionManager.isConnected()) {

            String s = null;
            try {
                s = connectionManager
                            .getFrom()
                            .readLine();
            } catch (IOException e) {
                // TODO Log
                // TODO lost connection abort app
                e.printStackTrace();
            }

            // put the message on the ui
            ((MainActivity) connectionManager.getAct())
                    .appendChat(s);

        }

    }
}
