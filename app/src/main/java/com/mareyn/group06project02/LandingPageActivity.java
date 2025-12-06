package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.databinding.ActivityLandingPageBinding;
import com.mareyn.group06project02.viewHolders.GroupViewModel;
import com.mareyn.group06project02.viewHolders.UserAdapter;
import com.mareyn.group06project02.viewHolders.UserViewModel;

public class LandingPageActivity extends AppCompatActivity {
  private static String LANDING_PAGE_ACTIVITY_USERNAME = "landing-page-activity";
  private static String LANDING_PAGE_ACTIVITY_USER_ID = "landing-page-activity-userId";
  private ActivityLandingPageBinding binding;
  private ChoreScoreRepository repository = ChoreScoreRepository.getRepository(getApplication());
  ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    var username = getIntent().getStringExtra(LANDING_PAGE_ACTIVITY_USERNAME);
    int userId = getIntent().getIntExtra(LANDING_PAGE_ACTIVITY_USER_ID, -1);

    Log.e("USER-ID", "THIS IS THE USER ID: " + username);

    binding.logoutButton.setOnClickListener(view -> {
      Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    });

    repository.getUserByUsername(username, user -> {
      repository.getUsersByGroupId(user.getFamilyId(), users -> {

      });
      runOnUiThread(() -> {
        if (user == null) {
          return;
        }
        Log.e("TESTING", "Is admin: " + user.isAdmin());

        binding.landingPageTitle.setText("Welcome, " + user.getUsername());
        if (!user.getEmail().isEmpty()) {
          binding.emailText.setText("Email: " + user.getEmail());
        } else {
          binding.emailText.setText("Email: " + "N/A");
        }
        repository.getGroupById(user.getFamilyId(), group -> {
          binding.whatFamilyText.setText("You are part of GROUP: " + group.getName());
        });
        if (user.isAdmin()) {
          binding.goToAdminPageButton.setVisibility(View.VISIBLE);
          binding.adminStatus.setText("You ARE an admin");
        } else {
          binding.adminStatus.setText("You ARE NOT an admin");
        }

        var groupsViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        var usersViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        RecyclerView recyclerView = binding.activeFamilyGroupsRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff(), getApplicationContext(), user3 -> {
          Log.i("LOGGER", "CLICKED THIS: " + user3.getUsername());
          Intent intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(getApplication(), user3.getUsername(), user3.getUserId());
          startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersViewModel.getUsersWithGroupId(user.getFamilyId()).observe(this, users -> {
          adapter.submitList(users);
        });

        // List.
        //     class Item {
        //       private String title;
        //       private String description;
        //
        //       public Item(String title, String description) {
        //         this.title = title;
        //         this.description = description;
        //       }
        //
        //       public String getTitle() {
        //         return title;
        //       }
        //
        //       public String getDescription() {
        //         return description;
        //       }
        //     }
        //     class ItemAdapter extends ArrayAdapter<Item> {
        //       public ItemAdapter(Context context, List<Item> items) {
        //         super(context, 0, items);
        //       }
        //
        //       @Override
        //       public View getView(int position, View convertView, ViewGroup parent) {
        //         // if (convertView == null) {
        //         //   convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        //         // }
        //         //
        //         // Item item = getItem(position);
        //         //
        //         // TextView titleView = convertView.findViewById(R.id.itemTitle);
        //         // TextView descView = convertView.findViewById(R.id.itemDescription);
        //         //
        //         // titleView.setText(item.getTitle());
        //         // descView.setText(item.getDescription());
        //         //
        //         // return convertView;
        //         return null;
        //       }
        //     }
        //     var items = new ArrayList<Item>();
        //     items.add(new Item("", ""));
        //     ItemAdapter adapter = new ItemAdapter(this, items);
        //
        //     binding.goToAdminPageButton.setOnClickListener(view -> {
        //       var intent = AdminControlsActivity.adminControlsActivityIntentFactory(getApplicationContext(), user.getUserId());
        //       startActivity(intent);
        //     });
        //   });
        // });
        //
        // // CHILD/PARENT DISPLAY CONNECTION
        // // TODO: Link to actual button when ready
        // LiveData<User> userObserver = repository.getUserByUserName(username);
        // userObserver.observe(this, user -> {
        //   binding.landingPageTitle.setOnLongClickListener(new View.OnLongClickListener() {
        //     @Override
        //     public boolean onLongClick(View v) {
        //       Log.e("TESTING", "userID: " + userId);
        //       if (user.isAdmin()) {
        //         Log.e("TESTING", "WE'RE IN AS ADMIN");
        //         Intent intent = ParentChoreDisplayActivity.parentChoreDisplayActivityIntentFactory(getApplicationContext(), username, userId);
        //         startActivity(intent);
        //       } else {
        //         Log.e("TESTING", "WE'RE IN AS NORMIE");
        //         Intent intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(getApplicationContext(), username, userId);
        //         startActivity(intent);
        //       }
        //       return false;
        //     }
      });
    });
  }

  static Intent landingPageActivityIntentFactory(Context context, String username, int userId) {
    Intent intent = new Intent(context, LandingPageActivity.class);
    intent.putExtra(LANDING_PAGE_ACTIVITY_USER_ID, userId);
    intent.putExtra(LANDING_PAGE_ACTIVITY_USERNAME, username);
    return intent;
  }
}
