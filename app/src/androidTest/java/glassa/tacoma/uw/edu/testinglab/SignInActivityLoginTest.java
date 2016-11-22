package glassa.tacoma.uw.edu.testinglab;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Random;


import glassa.tacoma.uw.edu.testinglab.util.SharedPreferencesHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by aglas on 11/17/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SignInActivityLoginTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    @Test
    public void testARegister() {
        onView(withId(R.id.action_logout))
                .perform(click());
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(7) + 1)
                + (random.nextInt(8) + 1) + (random.nextInt(9) + 1)
                + (random.nextInt(100) + 1) + (random.nextInt(4) + 1)
                + "@uw.edu";

        // Type text and then press the button.
        onView(withId(R.id.email_login_text))
                .perform(typeText(email));
        onView(withId(R.id.pwd_text))
                .perform(typeText("test1@#"));
        onView(withId(R.id.register_button))
                .perform(click());

        onView(withText("User successfully registered!"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLoginFragment() {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mActivityRule.getActivity());
        SharedPreferencesHelper sharedPreferencesHelper = new
                SharedPreferencesHelper(sharedPreferences);
        String email = sharedPreferencesHelper.getLoginInfo().getEmail();
        onView(allOf(withId(R.id.email_login_text)
                , withText("You are logged in as " + email)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testMenuActivity() {
        onView(withId(R.id.menu_button))
                .perform(click());
        onView(allOf(withId(R.id.welcome_text)
                , withText("Welcome!")))
                .check(matches(isDisplayed()));
    }



}
