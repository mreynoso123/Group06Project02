package com.mareyn.group06project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

  @Query("DELETE FROM " + ChoreScoreDatabase.USER_TABLE)
  void deleteAllRecords();

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE username == :username")
  LiveData<User> getUserByUsername(String username);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE userId = :userId")
  User getUserById(int userId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE + " WHERE username = :username")
  User getUserByUsername2(String username);
}
