package com.mareyn.group06project02.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mareyn.group06project02.database.entities.Group;

import java.util.List;

@Dao
public interface GroupDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Group... group);

  @Query("SELECT * FROM " + ChoreScoreDatabase.GROUP_TABLE)
  List<Group> getAllRecords();

  @Query("DELETE FROM " + ChoreScoreDatabase.GROUP_TABLE)
  void deleteAllRecords();

  @Query("SELECT * FROM " + ChoreScoreDatabase.GROUP_TABLE + " WHERE name = :name")
  Group getGroupByUsername2(String name);

  @Query("SELECT * FROM " + ChoreScoreDatabase.GROUP_TABLE + " WHERE groupId = :groupId")
  Group getGroupById(int groupId);
}
