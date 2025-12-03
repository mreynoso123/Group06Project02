package com.mareyn.group06project02.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.entities.Chore;
import com.mareyn.group06project02.database.entities.Group;
import com.mareyn.group06project02.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

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

    Future<ChoreScoreRepository> future = ChoreScoreDatabase.databaseWriteExecutor.submit(
      new Callable<ChoreScoreRepository>() {
        @Override
        public ChoreScoreRepository call() throws Exception {
          return new ChoreScoreRepository(application);
        }
      }
    );

    try {
      return future.get();
    } catch (InterruptedException | ExecutionException err) {
      Log.i("KEY", "Problem with getRepository()");
      // err.printStackTrace();
    }

    return null;
  }

  // User Repository Methods
  public void insertUser(User... user) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.insert(user);
    });
  }

  public void updateUser(User user) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.update(user);
    });
  }

  public LiveData<User> getUserByUserName(String username) {
    return userDAO.getUserByUsername(username);
  }

  public LiveData<Integer> getUserIdByUsername(String username) {
    return userDAO.getUserIdByUsername(username);
  }

  // Chore Repository methods
  public void insertChore(Chore chore) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      choreDAO.insert(chore);
    });
  }

  public LiveData<List<Chore>> getAllChoresByUserId(int loggedInUserId) {
    return choreDAO.getAllChoresByUserId(loggedInUserId);
  }

  public LiveData<List<Chore>> getAllActiveChores() {
    return choreDAO.getAllActiveChores();
  }

  public LiveData<List<Chore>> getCompletedChoresByUserId(int loggedInUserId) {
    return choreDAO.getCompletedChoresByUserId(loggedInUserId);
  }

  public LiveData<List<Chore>> getActiveChoresByUserId(int loggedInUserId) {
    return choreDAO.getActiveChoresByUserId(loggedInUserId);
  }

  public LiveData<Integer> getTotalScoreCompletedChoresById(int loggedInUserId) {
    return choreDAO.getTotalScoreCompletedChoresById(loggedInUserId);
  }

  public void getUserById(int userId, Consumer<User> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      User user = userDAO.getUserById(userId);
      callback.accept(user);
    });
  }

  public void getUserByUsername(String username, Consumer<User> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      User user = userDAO.getUserByUsername2(username);
      callback.accept(user);
    });
  }

  public void insertGroup(Group... group) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      groupDAO.insert(group);
    });
  }

  public void deleteAllUsers() {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.deleteAllRecords();
    });
  }

  public void updateChoreStatus(int choreId) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      choreDAO.updateChoreStatus(choreId);
    });
  }
}
