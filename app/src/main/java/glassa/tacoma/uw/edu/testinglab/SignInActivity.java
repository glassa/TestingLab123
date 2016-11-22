package glassa.tacoma.uw.edu.testinglab;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import glassa.tacoma.uw.edu.testinglab.LoginFragment;
import glassa.tacoma.uw.edu.testinglab.authenticate.Account;
import glassa.tacoma.uw.edu.testinglab.authenticate.RegisterFragment;
import glassa.tacoma.uw.edu.testinglab.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.testinglab.util.SharedPreferencesHelper;


public class SignInActivity extends AppCompatActivity implements RegisterFragment.OnRegisterListener{

    private final static String USER_ADD_URL = "http://cssgate.insttech.washington.edu/~tonyklee/addUser.php?";
    SharedPreferencesHelper mSharedPreferencesHelper;
    public Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Instantiate a SharedPreferencesHelper.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);
        SharedPreferenceEntry entry = mSharedPreferencesHelper.getLoginInfo();

        if (entry.isLoggedIn()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new RegisterFragment())
                    .commit();
        }

    }

    public void register(Account account) {

        mAccount = account;

        Log.i("register", mAccount.getEmail());
        Log.i("register", mAccount.getPassword());


        String url = buildUserURL(account.getEmail(), account.getPassword());

        Log.i("register", url);
        RegisterAsyncTask task = new RegisterAsyncTask();
        task.execute(url);

// Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (id == R.id.action_logout) {
            SharedPreferenceEntry entry = new SharedPreferenceEntry(false,"");
            mSharedPreferencesHelper.savePersonalInfo(entry);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class RegisterAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add user, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext()
                            , "User successfully registered!"
                            , Toast.LENGTH_LONG)
                            .show();
//                    Log.i("tony", mAccount.getEmail());
//                    Log.i("tony", mAccount.getPassword());

                    SharedPreferenceEntry entry = new SharedPreferenceEntry(
                            true, mAccount.getEmail());


                    mSharedPreferencesHelper.savePersonalInfo(entry);

                } else {
                    Toast.makeText(getApplicationContext(), "Failed to register: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                    SharedPreferenceEntry entry = new SharedPreferenceEntry(false,"");
                    mSharedPreferencesHelper.savePersonalInfo(entry);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private String buildUserURL(String userID, String userPwd) {

        StringBuilder sb = new StringBuilder(USER_ADD_URL);

        try {
            String email = userID;
            sb.append("email=");
            sb.append(email);


            String pwd = userPwd;
            sb.append("&pwd=");
            sb.append(URLEncoder.encode(pwd, "UTF-8"));


            Log.i("SignInFragment", sb.toString());

            Log.i("SignInFragment", "============================ The json string: " + sb);

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }
}
