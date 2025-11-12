package com.mareyn.group06project02.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mareyn.group06project02.database.ChoreScoreDatabase;

import java.util.Objects;

@Entity(tableName = ChoreScoreDatabase.GROUP_TABLE)
public class Group {
  @PrimaryKey(autoGenerate = true)
  private int groupId;
  private String name;

  @Override
  public String toString() {
    return "Group{" +
      "groupId=" + groupId +
      ", name='" + name + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return groupId == group.groupId && Objects.equals(name, group.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, name);
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
