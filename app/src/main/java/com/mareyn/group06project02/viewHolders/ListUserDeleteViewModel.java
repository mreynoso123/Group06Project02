package com.mareyn.group06project02.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Chore;
import com.mareyn.group06project02.database.entities.User;

import java.util.List;

public class ListUserDeleteViewModel extends AndroidViewModel {
  private Application application;

  private final ChoreScoreRepository repository;

  public ListUserDeleteViewModel(Application application) {
    super(application);
    repository = ChoreScoreRepository.getRepository(application);
    this.application = application;
  }

  public Application getApplicationContext() {
    return getApplication();
  }

  public LiveData<List<User>> getUsersWithGroupId(int userId) {
    return repository.getUsersByGroupId2(userId);
  }

  public void insert(Chore chore) {
    repository.insertChore(chore);
  }
}
