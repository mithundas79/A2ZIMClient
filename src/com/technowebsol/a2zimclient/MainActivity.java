package com.technowebsol.a2zimclient;


import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String HOST = "talk.google.com";
	public static final int PORT = 5222;
	public static final String SERVICE = "gmail.com";

	private EditText loginEdit;
	private EditText passwordEdit;
	private Button loginButton;
	private Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loginEdit = (EditText) findViewById(R.id.loginEdit);
		passwordEdit = (EditText) findViewById(R.id.passwordEdit);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);
		
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectionConfiguration connConfig = new ConnectionConfiguration(HOST,
						PORT, SERVICE);

				XMPPConnection connection = new XMPPConnection(connConfig);
				
				try {
					// Connect to the server
					connection.connect();
					connection.login(loginEdit.getText().toString(), passwordEdit.getText().toString());

					//Presence presence = new Presence(Presence.Type.available);
					//presence.setStatus("I'm available");
					//connection.sendPacket(presence);
					if (connection.isConnected()) {
						Log.d("Connection:", "Success");
						Toast.makeText(getApplicationContext(), "Connected",
								Toast.LENGTH_SHORT).show();
					}
					if (connection.isAuthenticated()) {
						Log.d("Authentication:", "Success");
						Toast.makeText(getApplicationContext(),
								"Authentication: Success", Toast.LENGTH_SHORT)
								.show();
						Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
						chatIntent.putExtra("loginEdit", loginEdit.getText().toString());
						chatIntent.putExtra("passwordEdit", passwordEdit.getText().toString());
						MainActivity.this.startActivity(chatIntent);
					} else {
						Log.d("Authentication:", "Failed");
						Toast.makeText(getApplicationContext(),
								"Invalid username or password", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (XMPPException ex) {
					connection = null;
					Log.e("Connection:", ex.getMessage());
					Toast.makeText(getApplicationContext(), "Unable to connect to server",
							Toast.LENGTH_SHORT).show();
					// Unable to connect to server
				}

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
