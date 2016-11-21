package tonyklee.tacoma.uw.edu.testinglab;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tonyklee.tacoma.uw.edu.testinglab.menu.MenuActivity;
import tonyklee.tacoma.uw.edu.testinglab.util.SharedPreferenceEntry;
import tonyklee.tacoma.uw.edu.testinglab.util.SharedPreferencesHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(
                sharedPreferences);
        SharedPreferenceEntry entry = sharedPreferencesHelper.getLoginInfo();

        if (entry.isLoggedIn()){
            TextView email = (TextView) v.findViewById(R.id.email_login_text);
            email.setText("You are logged in as " + entry.getEmail());
        }
        Button menuButton = (Button) v.findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        return v;

    }

}
