package com.mareyn.group06project02.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Chore;
import com.mareyn.group06project02.database.entities.Group;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {
  private final ChoreScoreRepository repository;

  public GroupViewModel(Application application) {
    super(application);
    repository = ChoreScoreRepository.getRepository(application);
  }

  public LiveData<List<Group>> getAllGroups() {
    return repository.getAllGroups();
  }

  public void insert(Chore chore) {
    repository.insertChore(chore);
  }
}
