package com.mareyn.group06project02;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.mareyn.group06project02.database.ChoreScoreDatabase;
import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Group;
import com.mareyn.group06project02.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>This test verifies that when the user taps the ChoreDisplay
 * button on the LandingPageActivity, the intent triggers for
 * ParentChoreDisplayActivity. It ensures the correct screen navigation
 * flow between the landing page and ParentChoreDisplayActivity.</p>
 */

public class ParentChoreDisplayActivityTest {
  private ChoreScoreRepository repository;

  @Before
  public void setUp() {
    init();
  }

  @After
  public void tearDown() {
    release();
  }

  @Test
  public void clickToChoreDisplay_startParentChoreDisplay() {

    repository = ChoreScoreRepository.getRepository(ApplicationProvider.getApplicationContext());

    ChoreScoreDatabase ChoreScoreDB = ChoreScoreDatabase.getDatabase(ApplicationProvider.getApplicationContext());
    ChoreScoreDB.userDAO().deleteAllRecords();

    User admin2 = new User(1, "admin2", "admin2", "", true);
    ChoreScoreDB.userDAO().insert(admin2);

    Group testGroup1 = new Group(1, "test-group1");
    ChoreScoreDB.groupDAO().insert(testGroup1);

    Intent intent = LandingPageActivity.landingPageActivityIntentFactory(
      ApplicationProvider.getApplicationContext(),
      "admin2",
      0);

    try (ActivityScenario<LandingPageActivity> scenario = ActivityScenario.launch(intent)) {
      onView(withId(R.id.toChoreDisplayButton)).perform(click());
      intended(hasComponent(ParentChoreDisplayActivity.class.getName()));
    }
  }
}
