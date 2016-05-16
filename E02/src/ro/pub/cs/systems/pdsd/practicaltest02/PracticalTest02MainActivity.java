package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends Activity {
	
	//----------------gui-----------------------
		private EditText serverPortEditText = null;
	    private Button connectButton = null;
	    private EditText     clientAddressEditText    = null;
	    private EditText     clientPortEditText       = null;
	    private EditText     infoEditText1             = null;
	    private EditText     infoEditText2             = null;
	    private Button       getInfoButton = null;
	    private TextView     infoTextView  = null;

	    //-----------server&client Threads------------
	    private ServerThread serverThread             = null;
	    private ClientThread clientThread             = null;
		
		//-----------------listeneriButoane------------------------
	    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
	    private class ConnectButtonClickListener implements Button.OnClickListener {

	        public void onClick(View view) {

	            String serverPort = serverPortEditText.getText().toString();
	            if (serverPort == null || serverPort.isEmpty()) {
	                Toast.makeText(getApplicationContext(),"Server port should be filled!",Toast.LENGTH_SHORT).show();
	                return;
	            }
	            infoTextView.setText("Connect...");
	            serverThread = new ServerThread(Integer.parseInt(serverPort));
	            if (serverThread.getServerSocket() != null)
	                serverThread.start();
	        }
	    }

	    private GetInfoButtonClickListener getInfoButtonClickListener = new GetInfoButtonClickListener();
	    private class GetInfoButtonClickListener implements Button.OnClickListener {
	        public void onClick(View view) {
	        	
	        	//---------------------info-----------------------------------
	        	
	            String clientAddress = clientAddressEditText.getText().toString();
	            String clientPort = clientPortEditText.getText().toString();

	            String info1 = infoEditText1.getText().toString();
	            String info2 = infoEditText2.getText().toString();

	            infoTextView.setText("GetInfo");
	       
	            clientThread = 
	            		new ClientThread(clientAddress, Integer.parseInt(clientPort),info1,info2,infoTextView);
	            clientThread.start();

	        }
	    }
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_main);
		
		//----------------------Campuri&Butoane------------------------
				serverPortEditText = (EditText)findViewById(R.id.editTextPortServer);     
				connectButton = (Button)findViewById(R.id.buttonCon);
		        clientAddressEditText = (EditText)findViewById(R.id.editTextAddr);
		        clientPortEditText = (EditText)findViewById(R.id.editTextPortClient);
		        infoEditText1 = (EditText)findViewById(R.id.editTextInfo1);
		        infoEditText2 = (EditText)findViewById(R.id.editTextInfo2);      
		        getInfoButton = (Button)findViewById(R.id.buttonGet);
		        infoTextView = (TextView)findViewById(R.id.textView1);
				
		      //----------------------ListenerButoane------------------------
		        connectButton.setOnClickListener(connectButtonClickListener);
		        getInfoButton.setOnClickListener(getInfoButtonClickListener);
				
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
