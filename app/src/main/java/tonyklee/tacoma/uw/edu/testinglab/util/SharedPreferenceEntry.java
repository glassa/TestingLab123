package tonyklee.tacoma.uw.edu.testinglab.util;

/**
 * Created by Tony on 11/14/2016.
 */

/**
 * Model class containing personal information that will be
 * saved to SharedPreferences.
 * Add to this class any other app related information
 * that needs to be cached.
 */
public class SharedPreferenceEntry {

    // Name of the user.
    private boolean mIsLoggedIn = false;

    // Email address of the user.
    private final String mEmail;

    // Add others here..

    public SharedPreferenceEntry(boolean loggedIn, String email) {
        mIsLoggedIn = loggedIn;
        mEmail = email;
//        Log.i("SPEntry", email);
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public String getEmail() {
        return mEmail;
    }
}
