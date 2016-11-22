package glassa.tacoma.uw.edu.testinglab.authenticate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import glassa.tacoma.uw.edu.testinglab.R;
import glassa.tacoma.uw.edu.testinglab.SignInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }

    public interface OnRegisterListener {
        public void register(Account account);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText userIdText = (EditText) v.findViewById(R.id.email_login_text);
        final EditText pwdText = (EditText) v.findViewById(R.id.pwd_text);
        Button signInButton = (Button) v.findViewById(R.id.register_button);



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIdText.getText().toString();
                String pwd = pwdText.getText().toString();

                Log.i("tony", "userID: " + userID);
                Log.i("tony", "pwd: " + pwd);

                try {
                    Account account = new Account(userID, pwd);

                    ((SignInActivity) getActivity()).register(account);

                } catch (IllegalArgumentException e){
                    if (e.toString().contains("Invalid Email")) {
                        Toast.makeText(v.getContext(), "Invalid Email", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(v.getContext(), "Invalid Password", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });



        return v;
    }



}
