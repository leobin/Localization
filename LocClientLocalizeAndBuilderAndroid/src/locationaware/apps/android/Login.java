package locationaware.apps.android;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.localization.server.ServerAPI;

public class Login extends Activity implements OnClickListener{

	EditText userNameEtext;
	EditText passwordEtext;
	Button loginButton;
	
	Handler myHandler = new Handler();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
                        
        userNameEtext = (EditText) findViewById(R.id.login_username_edittext);
        passwordEtext = (EditText) findViewById(R.id.login_password_edittext);
                
        loginButton = (Button) findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(this);
    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_login_button:		
			ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo == null || !networkInfo.isConnected()) {
				myHandler.post(new FailConnectInternet());
				return;
			}
			
			loginButton.setEnabled(false);
			loginButton.setFocusable(false);
			
			(new Thread(new LoginRunnable(this))).start();
			
			break;

		default:
			break;
		}
	}

	private class LoginRunnable implements Runnable{

		Activity activity;
		
		public LoginRunnable(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			String userName = userNameEtext.getText().toString();
			String password = passwordEtext.getText().toString();
			
			//implement checking login true or false
			LocalizationApplication.loginUser = (new ServerAPI()).checkLoginUser(userName, password);
			
			if (LocalizationApplication.loginUser != null) {
				activity.finish();
			} else {
				myHandler.post(new LoginFail());
			}
			
			myHandler.post(new LoginDone());
		}
	}
	
	private class LoginDone implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			loginButton.setEnabled(true);
			loginButton.setFocusable(true);
		}
	}
	
	private class LoginFail implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			(Toast.makeText(getApplicationContext(),
					"Wrong username or password",
					Toast.LENGTH_LONG)).show();		}
	}
	
	private class FailConnectInternet implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			(Toast.makeText(getApplicationContext(),
					"Fail to connect internet",
					Toast.LENGTH_LONG)).show();		}
	}
}
