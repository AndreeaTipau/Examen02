package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
import android.widget.TextView;

public class ClientThread extends Thread {
    
	 private Socket socket;
	
	//-----------------------------------
	private String address;
    private int port;
    private String info1;
    private String info2;
    private TextView infoTextView;
    
    
   

    public ClientThread(String address, int port, String info1,String info2,TextView infoTextView) {
        this.address = address;
        this.port = port;
        this.info1 = info1;
        this.info2 = info2;
        this.infoTextView = infoTextView;

    }

    //------------------------------------
    
    @Override
    public void run() {
       try {
           socket = new Socket(address, port);
           BufferedReader bufferedReader = Utilities.getReader(socket);
           PrintWriter printWriter = Utilities.getWriter(socket);

           if (bufferedReader != null && printWriter != null) {
               //--------------ce trimit la server-----------
        	   printWriter.println(info1);
               printWriter.flush();
               printWriter.println(info2);
               printWriter.flush();
               printWriter.println("-22.4");
               printWriter.flush();
               printWriter.println("55.2");
               printWriter.flush();

              

               //-------------ce primesc de la server si afisez-----------
               String info = null;
               while ((info = bufferedReader.readLine()) != null) {
                   final String finalizedInfo = info;
                   infoTextView.post(new Runnable() {
                       @Override
                       public void run() {
                           infoTextView.setText(finalizedInfo + "\n");
                           Log.d("aici", finalizedInfo);
                       }
                   });
               }

           }
       } catch (UnknownHostException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}