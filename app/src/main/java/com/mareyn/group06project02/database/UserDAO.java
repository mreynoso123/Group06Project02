package com.mareyn.group06project02.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mareyn.group06project02.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(User... chore);

  @Query("SELECT * FROM " + ChoreScoreDatabase.USER_TABLE)
  List<User> getAllRecords();
}
