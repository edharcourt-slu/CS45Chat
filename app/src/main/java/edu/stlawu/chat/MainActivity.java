package edu.stlawu.chat;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button connect;
    private Button send;
    private EditText send_text;
    private TextView chat_log;
    private EditText ip_address;
    private ConnectionManager connectionManager;

    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = findViewById(R.id.connect);
        send = findViewById(R.id.send);
        send_text = findViewById(R.id.send_text);
        chat_log = findViewById(R.id.chat_log);
        ip_address = findViewById(R.id.ip_address);

        // set up click listeners
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread client_thread =
                        new Thread(connectionManager.client);

                // TODO lots of error checking on the
                // ip_address
                connectionManager.setIp_addr(
                        ip_address.getText().toString()
                );

                client_thread.start();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do we have a connection manager?
                // Assume we do.
                // TODO fix later if no connection

                runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            MainActivity
                                .this
                                .connectionManager.getTo().println(
                                MainActivity
                                    .this
                                    .send_text.getText().toString()
                                );
                            }
                    }
            );

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectionManager =
            new ConnectionManager(this);

        // Create the server thread
        Thread server_thread = new Thread(connectionManager.server);
        server_thread.start();

    }

    public void appendChat(final String s) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chat_log.append(s);
            }
        });
    }


}
