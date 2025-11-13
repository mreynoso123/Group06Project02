package com.mareyn.group06project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mareyn.group06project02.database.entities.Chore;

import java.util.List;

@Dao
public interface ChoreDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Chore chore);

  @Query("SELECT * FROM " + ChoreScoreDatabase.CHORE_TABLE)
  List<Chore> getAllRecords();

  @Query("SELECT * FROM " + ChoreScoreDatabase.CHORE_TABLE + " WHERE userId = :loggedInUserId ORDER BY choreId DESC")
  LiveData<List<Chore>> getAllChoresByUserId(int loggedInUserId);
}
