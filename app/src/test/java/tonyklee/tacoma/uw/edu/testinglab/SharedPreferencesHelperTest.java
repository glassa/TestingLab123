package tonyklee.tacoma.uw.edu.testinglab;

/**
 * Created by Tony on 11/14/2016.
 */

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tonyklee.tacoma.uw.edu.testinglab.util.SharedPreferenceEntry;
import tonyklee.tacoma.uw.edu.testinglab.util.SharedPreferencesHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;


/**
 * Unit tests for the {@link SharedPreferencesHelper} that
 * mocks {@link SharedPreferences}.
 */

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final boolean TEST_LOGGED_IN = true;
    private static final String TEST_EMAIL = "test@email.com";

    private SharedPreferenceEntry mSharedPreferenceEntry;

    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void initMocks() {
        // Create SharedPreferenceEntry to persist.
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_LOGGED_IN,
                TEST_EMAIL);

        // Create a mocked SharedPreferences.
        mMockSharedPreferencesHelper = createMockSharedPreference();

        // Create a mocked SharedPreferences that fails at saving data.
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        // Save the personal information to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);

        assertThat("Checking that SharedPreferenceEntry.save... returns true",
                success, is(true));

        // Read personal information from SharedPreferences
        SharedPreferenceEntry savedSharedPreferenceEntry =
                mMockSharedPreferencesHelper.getLoginInfo();

        // Make sure both written and retrieved login information are equal.
        assertThat("Checking that SharedPreferenceEntry.isLogged has been persisted and read correctly",
                mSharedPreferenceEntry.isLoggedIn(),
                is(equalTo(savedSharedPreferenceEntry.isLoggedIn())));
        assertThat("Checking that SharedPreferenceEntry.email has been persisted and read "
                        + "correctly",
                mSharedPreferenceEntry.getEmail(),
                is(equalTo(savedSharedPreferenceEntry.getEmail())));
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // Read login information from a broken SharedPreferencesHelper
        boolean success =
                mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success,
                is(false));
    }

    /**
     * Creates a mocked SharedPreferences.
     */
    private SharedPreferencesHelper createMockSharedPreference() {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        Mockito.when(mMockSharedPreferences.getBoolean(eq(SharedPreferencesHelper.KEY_LOGGED_IN), Matchers.anyBoolean()))
                .thenReturn(mSharedPreferenceEntry.isLoggedIn());
        Mockito.when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_EMAIL), Matchers.anyString()))
                .thenReturn(mSharedPreferenceEntry.getEmail());

        // Mocking a successful commit.
        Mockito.when(mMockEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        Mockito.when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // Mocking a commit that fails.
        Mockito.when(mMockBrokenEditor.commit()).thenReturn(false);

        // Return the broken MockEditor when requesting it.
        Mockito.when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }
}
