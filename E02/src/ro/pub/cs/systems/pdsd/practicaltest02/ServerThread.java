package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import android.util.Log;

public class ServerThread extends Thread {
	private int port = 0;
    private ServerSocket serverSocket = null;

    private HashMap<String, Information> data = null;
    
    
    //-------------------------------------------------------------------------------------
    public ServerThread(int port) {
    	
    	// deschidem portul pe care serverul asculta
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
        // pregatim hashmapul
        this.data = new HashMap<String, Information>();
    }
    //-------------------------------------------------------------------------------------
    
    
    // functia run pentru thread
    @Override
    public void run() {
    	Log.i("A", "a intrat in run");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i(Constants.TAG, "[SERVER] Waiting for a connection...");

                // acceptam conexiunea
                Socket socket = serverSocket.accept();
                Log.i("A", "s-a conectat");
                Log.i(Constants.TAG, "[SERVER] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stopThread() {
        if (serverSocket != null) {
            interrupt();
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
    }
    

    
    
    // seteri si geteri
    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocker(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public synchronized void setData(String key, Information value) {
        this.data.put(key, value);
    }

    public synchronized HashMap<String, Information> getData() {
        return data;
    }

}
