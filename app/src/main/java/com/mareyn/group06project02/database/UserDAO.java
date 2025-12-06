package com.mareyn.group06project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mareyn.group06project02.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(User... chore);

  @Update
  void update(User user);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE)
  List<User> getAllRecords();

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE familyId == :groupId")
  List<User> getUsersByGroupId(int groupId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE familyId == :groupId")
  LiveData<List<User>> getUsersByGroupId2(int groupId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE familyId == :groupId AND isAdmin = 0")
  LiveData<List<User>> getNormalUsersByGroup(int groupId);

  @Query("DELETE FROM " + ChoreScoreDatabase.USER_TABLE)
  void deleteAllRecords();

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE username == :username")
  LiveData<User> getUserByUsername(String username);

  @Query("SELECT userId FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE username == :username")
  LiveData<Integer> getUserIdByUsername(String username);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE userId = :userId")
  User getUserById(int userId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE userId = :userId")
  LiveData<User> getUserById2(int userId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE username = :username")
  User getUserByUsername2(String username);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE isAdmin = 0")
  LiveData<List<User>> getAllNormalUsers();

  @Query("DELETE FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE username = :username")
  void deleteUserByUsername(String username);

  @Delete
  void deleteUser(User user);
}
