package edu.stlawu.chat;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Handle all of the socket code
 */
public class ConnectionManager {

    // We will be one or the other
    private ServerSocket server_sock;
    private Socket client_sock;

    // line buffered text reading from
    // device we are connected to
    private BufferedReader from;

    // Writing lines to the connected device
    private PrintWriter to;

    private boolean connected;
    private final static int PORT = 12345;
    private static String LOG_TAG =
            ConnectionManager.class.getSimpleName();

    private Activity act;

    private String ip_addr;

    public ConnectionManager(Activity act) {
        server_sock = null;
        client_sock = null;
        to = null;
        from = null;
        connected = false;
        this.act = act;
    }

    public PrintWriter getTo() {
        return to;
    }

    public Activity getAct() {
        return act;
    }

    public BufferedReader getFrom() {
        return from;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    // create a server thread
    Runnable server = new Runnable() {
        @Override
        public void run() {
            // wait for a connection
            try {
                server_sock = new ServerSocket(PORT);
                client_sock = server_sock.accept();
                Log.i(LOG_TAG, "Connected to " +
                        client_sock.getInetAddress());
                from = new BufferedReader(
                    new InputStreamReader(
                        client_sock.getInputStream()
                    )
                );

                to = new PrintWriter(
                    client_sock.getOutputStream(),
                    true
                );

            }
            catch (SocketException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Socket exception");
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Something bad happened");
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Bad port number");
            }

            connected = true;

            // Start the reader
            Thread t = new Thread(
                          new Reader(
                             ConnectionManager.this));
            t.start();
        }
    };


    Runnable client = new Runnable() {

        @Override
        public void run() {

            // TODO can't be both a client and a server
            try {
                client_sock = new Socket(getIp_addr(), PORT);
                from = new BufferedReader(
                        new InputStreamReader(
                                client_sock.getInputStream()
                        )
                );
                to = new PrintWriter(client_sock.getOutputStream());
            } catch (IOException e) {
                Log.e(LOG_TAG, "Could not connect to server");
                e.printStackTrace();
            }

            connected = true;
            Log.i(LOG_TAG, "Connected to " + getIp_addr());

            // Start the reader
            Thread t = new Thread(
                    new Reader(
                            ConnectionManager.this));
            t.start();

        }
    };


}
