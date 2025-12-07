package com.mareyn.group06project02.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.ChoreLogger;
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
      Log.i(ChoreLogger.ID, "Problem with getRepository()");
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

  /**
   * Insert a user only if it's userId does not already exist in the system.
   */
  public void insertUserIfNotExists(User user, Consumer<Boolean> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      var actualUser = userDAO.getUserByUsername2(user.getUsername());
      if (actualUser == null) {
        userDAO.insert(user);
      }
      callback.accept(actualUser == null);
    });
  }

  public void insertUserIfNotExists(User user) {
    insertUserIfNotExists(user, exists -> {
    });
  }

  public void insertGroupIfNotExists(Group group, Consumer<Boolean> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      var actualGroup = groupDAO.getGroupByUsername2(group.getName());
      if (actualGroup == null) {
        groupDAO.insert(group);
      }
      callback.accept(actualGroup == null);
    });
  }

  public void getGroupById(int groupId, Consumer<Group> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      var group = groupDAO.getGroupById(groupId);
      callback.accept(group);
    });
  }

  public void insertGroupIfNotExists(Group group) {
    insertGroupIfNotExists(group, exists -> {
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

  public LiveData<List<Group>> getAllGroups() {
    return groupDAO.getAllChores();
  }

  public LiveData<List<User>> getAllNormalUsers() {
    return userDAO.getAllNormalUsers();
  }

  public LiveData<List<User>> getNormalUsersByGroup(int groupId) {
    return userDAO.getNormalUsersByGroup(groupId);
  }

  public void deleteUserByUsername(String username) {
    userDAO.deleteUserByUsername(username);
  }

  public void deleteUser(User user) {
    userDAO.deleteUser(user);
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

  public LiveData<User> getUserById2(int userId) {
    return userDAO.getUserById2(userId);
  }

  public void getUserByUsername(String username, Consumer<User> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      User user = userDAO.getUserByUsername2(username);
      callback.accept(user);
    });
  }

  public void getGroupByName(String username, Consumer<Group> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      Group group = groupDAO.getGroupByUsername2(username);
      callback.accept(group);
    });
  }

  public void getUsersByGroupId(int groupId, Consumer<List<User>> callback) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      var users = userDAO.getUsersByGroupId(groupId);
      callback.accept(users);
    });
  }

  public LiveData<List<User>> getUsersByGroupId2(int groupId) {
    return userDAO.getUsersByGroupId2(groupId);
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

  public void deleteAllGroups() {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      groupDAO.deleteAllRecords();
    });
  }

  public void deleteAllChores() {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      choreDAO.deleteAllRecords();
    });
  }

  public void updateChoreStatus(int choreId) {
    ChoreScoreDatabase.databaseWriteExecutor.execute(() -> {
      choreDAO.updateChoreStatus(choreId);
    });
  }
}
