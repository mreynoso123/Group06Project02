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

public class LandingPageActivityToChoreActivities {
  @Before
  public void setUp() {
    init();
  }

  @After
  public void tearDown() {
    release();
  }

  @Test
  public void LandingPageActivity_GoesToChildChoreDisplayActivity() {
    ChoreScoreRepository repository = ChoreScoreRepository.getRepository(ApplicationProvider.getApplicationContext());
    ChoreScoreDatabase db = ChoreScoreDatabase.getDatabase(ApplicationProvider.getApplicationContext());
    db.userDAO().deleteAllRecords();

    User admin2 = new User(0, "admin2", "admin2", "", true);
    db.userDAO().insert(admin2);

    User user9 = new User(0, "user9", "user9", "", false);
    db.userDAO().insert(admin2);

    Group testGroup1 = new Group(0, "test-group1");
    db.groupDAO().insert(testGroup1);

    User insertedUser = db.userDAO().getUserByUsername2("admin2");

    Intent intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(
      ApplicationProvider.getApplicationContext(),
      user9.getUsername(),
      user9.getUserId(),
      insertedUser.getUsername(),
      insertedUser.getUserId(),
      false
    );

    try (ActivityScenario<ChildChoreDisplayActivity> scenario = ActivityScenario.launch(intent)) {
      onView(withId(R.id.currentScoreTextView)).perform(click());
      intended(hasComponent(ChildChoreDisplayActivity.class.getName()));
    }
  }

  @Test
  public void LandingPageActivity_GoesToParentChoreDisplayActivity() {
    ChoreScoreRepository repository = ChoreScoreRepository.getRepository(ApplicationProvider.getApplicationContext());
    ChoreScoreDatabase db = ChoreScoreDatabase.getDatabase(ApplicationProvider.getApplicationContext());
    db.userDAO().deleteAllRecords();

    User admin2 = new User(0, "admin2", "admin2", "", true);
    db.userDAO().insert(admin2);

    User user9 = new User(0, "user9", "user9", "", false);
    db.userDAO().insert(admin2);

    Group testGroup1 = new Group(0, "test-group1");
    db.groupDAO().insert(testGroup1);

    User insertedUser = db.userDAO().getUserByUsername2("admin2");

    Intent intent = ParentChoreDisplayActivity.parentChoreDisplayActivityIntentFactory(
      ApplicationProvider.getApplicationContext(),
      user9.getUsername(),
      user9.getUserId(),
      insertedUser.getUsername(),
      insertedUser.getUserId(),
      false
    );

    try (ActivityScenario<ParentChoreDisplayActivity> scenario = ActivityScenario.launch(intent)) {
      onView(withId(R.id.createNewChoreTextView)).perform(click());
      intended(hasComponent(ParentChoreDisplayActivity.class.getName()));
    }
  }
}
