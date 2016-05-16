package ro.pub.cs.systems.pdsd.practicaltest02;

import android.util.Log;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
            	
            	//creare flux de intrare
                BufferedReader bufferedReader = Utilities.getReader(socket);
                //creare flux de iesire
                PrintWriter printWriter = Utilities.getWriter(socket);
                Log.i("A", "Am intrat in run communication");
                if (bufferedReader != null && printWriter != null) {
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client !");
                    
                    // INPUT DE LA CLIENT
                    String info1 = bufferedReader.readLine();
                    String info2 = bufferedReader.readLine();
                    String info3 = bufferedReader.readLine();
                    String info4 = bufferedReader.readLine();


                    HashMap<String, Information> data = serverThread.getData();

                    Information info = null;

                    // incerc sa iau info din cache
                    if (info1 != null && !info1.isEmpty() && info1 != null && !info1.isEmpty()) {

                        if (data.containsKey(info1)) {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
                            info = data.get(info1);
                        }

                    // iau info de pe server web
                    else {

                        Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
                        
                        Log.i("rezultate", info1+" " +info2+" "+info3+" "+info4);
                        
                    	HttpClient httpClient = new DefaultHttpClient();
                        String url = Constants.WEB_SERVICE_ADDRESS +
                                Constants.NORTH + info1 +
                                "&" + Constants.SOUTH + info2 +
                                "&" + Constants.EAST + info3 +
                                "&" + Constants.WEST + info4 +
                                "&" + Constants.CREDENTIALS;         
                        HttpGet httpGet = new HttpGet(url);
                        ResponseHandler<String> responseHandlerGet = new BasicResponseHandler();
                   	 try {
                         String content = httpClient.execute(httpGet, responseHandlerGet);
      
                         JSONObject result = new JSONObject(content);
                         Log.i("vedem pagina html", result.toString());
                         
                        
                         JSONArray jsonArray = result.getJSONArray(Constants.GEONAMES);

                         for (int k = 0; k < jsonArray.length(); k++) {
                             JSONObject jsonObject = jsonArray.getJSONObject(k);
                              info =  new Information( jsonObject.getString(Constants.POPULATION),
                                     jsonObject.getString(Constants.FCODE_NAME), jsonObject.getString(Constants.NAME),
                                     jsonObject.getString(Constants.WIKIPEDIA_WEB_PAGE_ADDRESS),jsonObject.getString(Constants.COUNTRY_CODE));
                             data.put(info1,info);
                             

                         }
                         

                     } catch (ClientProtocolException clientProtocolException) {
                         Log.e(Constants.TAG, clientProtocolException.getMessage());
                         if (Constants.DEBUG) {
                             clientProtocolException.printStackTrace();
                         }
                     } catch (IOException ioException) {
                         Log.e(Constants.TAG, ioException.getMessage());
                         if (Constants.DEBUG) {
                             ioException.printStackTrace();
                         }
                     }
                        
                        
                   	 	catch (JSONException e) {
            			e.printStackTrace();
            		}
                                     
                        // se afiseaza rezultatul
                        String result = null;
                        result = info.toString();
                        printWriter.println(result);
                        printWriter.flush();
                        // s-a af rezultatul
                    }}
                }

            } catch (Exception ioException) {
                ioException.printStackTrace();

            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}