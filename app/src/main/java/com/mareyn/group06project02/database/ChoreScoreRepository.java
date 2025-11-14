package com.mareyn.group06project02.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.entities.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ChoreScoreRepository {
  private ChoreDAO choreDAO;
  private GroupDAO groupDAO;
  private UserDAO userDAO;

  private static ChoreScoreRepository repository;

  public ChoreScoreRepository(Application application) {
    ChoreScoreDatabase db = ChoreScoreDatabase.getDatabase(application);
    this.choreDAO = db.choreDAO();
    this.groupDAO = db.groupDAO();
    this.userDAO = db.userDAO();
  }

  public static ChoreScoreRepository getRepository(Application application) {
    if (repository != null) {
      return repository;
    }

    Future<ChoreScoreRepository> future = ChoreScoreDatabase.databaseWriteExecutor.submit(new Callable<ChoreScoreRepository>() {
      @Override
      public ChoreScoreRepository call() throws Exception {
        repository = new ChoreScoreRepository(application);
        return repository;
      }
    });

    try {
      return future.get();
    } catch (InterruptedException | ExecutionException err) {
      Log.i("KEY", "Problem with getRepository()");
      err.printStackTrace();
    }

    return null;
  }

  public void insertUser(User... user) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.insert(user);
    });
  }

  public LiveData<User> getUserByUserName(String username) {
    return userDAO.getUserByUsername(username);
  }
}
