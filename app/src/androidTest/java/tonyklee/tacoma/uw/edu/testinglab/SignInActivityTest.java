package tonyklee.tacoma.uw.edu.testinglab;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignInActivityTest {

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test.
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    @Before
    public void logout() {
        onView(withId(R.id.action_logout))
                .perform(click());

    }


    @Test
    public void testRegister() {

        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(4) + 1)
                + (random.nextInt(9) + 1) + (random.nextInt(7) + 1)
                + (random.nextInt(4) + 1) + (random.nextInt(100) + 1)
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
    public void testRegisterInvalidEmail() {
        // Type text and then press the button.
        onView(withId(R.id.email_login_text))
                .perform(typeText("mmuppauw.edu"));
        onView(withId(R.id.pwd_text))
                .perform(typeText("test1@#"));
        onView(withId(R.id.register_button))
                .perform(click());

        onView(withText("Invalid Email"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterInvalidPassword() {
        // Type text and then press the button.
        onView(withId(R.id.email_login_text))
                .perform(typeText("mmuppa@uw.edu"));
        onView(withId(R.id.pwd_text))
                .perform(typeText(""));
        onView(withId(R.id.register_button))
                .perform(click());

        onView(withText("Invalid Password"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }


}
