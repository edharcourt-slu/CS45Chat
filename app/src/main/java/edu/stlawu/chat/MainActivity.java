package edu.stlawu.chat;

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
                // TODO
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectionManager = new ConnectionManager();

        // Create the server thread
        Thread server_thread = new Thread(connectionManager.server);
        server_thread.start();

    }


}
