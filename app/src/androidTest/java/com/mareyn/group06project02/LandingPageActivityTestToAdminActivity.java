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

public class LandingPageActivityTestToAdminActivity {
  @Before
  public void setUp() {
    init();
  }

  @After
  public void tearDown() {
    release();
  }

  @Test
  public void LandingPageActivity_GoesToAdminPage() {
    ChoreScoreRepository repository = ChoreScoreRepository.getRepository(ApplicationProvider.getApplicationContext());
    ChoreScoreDatabase db = ChoreScoreDatabase.getDatabase(ApplicationProvider.getApplicationContext());
    db.userDAO().deleteAllRecords();

    User admin2 = new User(0, "admin2", "admin2", "", true);
    db.userDAO().insert(admin2);

    Group testGroup1 = new Group(0, "test-group1");
    db.groupDAO().insert(testGroup1);

    User insertedUser = db.userDAO().getUserByUsername2("admin2");

    Intent intent = AdminControlsActivity.adminControlsActivityIntentFactory(
      ApplicationProvider.getApplicationContext(),
      insertedUser.getUsername(),
      insertedUser.getUserId());

    try (ActivityScenario<AdminControlsActivity> scenario = ActivityScenario.launch(intent)) {
      onView(withId(R.id.welcome)).perform(click());
      intended(hasComponent(AdminControlsActivity.class.getName()));
    }
  }
}
