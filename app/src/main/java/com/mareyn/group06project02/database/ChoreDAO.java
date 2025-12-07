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

  @Query("DELETE FROM " + ChoreScoreDatabase.CHORE_TABLE)
  void deleteAllRecords();

  @Query("SELECT SUM(points) FROM " + ChoreScoreDatabase.CHORE_TABLE + (" WHERE userId = :loggedInUserId AND status = 1"))
  LiveData<Integer> getTotalScoreCompletedChoresById(int loggedInUserId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.CHORE_TABLE + " WHERE userId = :loggedInUserId ORDER BY choreId DESC")
  LiveData<List<Chore>> getAllChoresByUserId(int loggedInUserId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.CHORE_TABLE + " WHERE status = 0 ORDER BY choreId DESC")
  LiveData<List<Chore>> getAllActiveChores();

  @Query("SELECT * FROM " + ChoreScoreDatabase.CHORE_TABLE + " WHERE userId = :loggedInUserId AND status = 1 ORDER BY choreId DESC")
  LiveData<List<Chore>> getCompletedChoresByUserId(int loggedInUserId);

  @Query("SELECT * FROM " + ChoreScoreDatabase.CHORE_TABLE + " WHERE userId = :loggedInUserId AND status = 0 ORDER BY choreId DESC")
  LiveData<List<Chore>> getActiveChoresByUserId(int loggedInUserId);

  @Query("UPDATE " + ChoreScoreDatabase.CHORE_TABLE + " SET status = 1 WHERE choreId = :choreId")
  void updateChoreStatus(int choreId);
}
