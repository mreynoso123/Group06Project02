package com.mareyn.group06project02.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mareyn.group06project02.Database.entities.Group;

import java.util.List;

@Dao
public interface GroupDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Group group);

	@Query("SELECT * FROM " + ChoreScoreDatabase.GROUP_TABLE)
	List<Group> getAllRecords();
}
