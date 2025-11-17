package com.mareyn.group06project02;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests navigation behavior in the Login screen.
 *
 * <p>This test verifies that when the user taps the "Forgot Password"
 * link on the LoginActivity, the intent triggers for
 * ForgotPasswordActivity. It ensures the correct screen navigation
 * flow between login and password recovery.</p>
 */

public class LoginActivityTest {

  @Rule
  public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

  @Before
  public void setUp() {
    init();
  }

  @After
  public void tearDown() {
    release();
  }

  @Test
  public void clickForgotPassword_startForgotPasswordActivity() {
    onView(withId(R.id.forgotPassword)).perform(click());
    intended(hasComponent(ForgotPasswordActivity.class.getName()));
  }
}
