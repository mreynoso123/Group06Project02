package com.mareyn.group06project02.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Chore;

import java.util.List;

public class ChoreViewModel extends AndroidViewModel {
  private final ChoreScoreRepository repository;

  public ChoreViewModel(Application application, int userId) {
    super(application);
    repository = ChoreScoreRepository.getRepository(application);
  }

  public LiveData<List<Chore>> getAllChoresByUserId(int userId) {
    return repository.getAllChoresByUserId(userId);
  }

  public void insert(Chore chore) {
    repository.insertChore(chore);
  }
}
