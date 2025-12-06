package com.mareyn.group06project02.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mareyn.group06project02.database.ChoreScoreDatabase;

import java.util.Objects;

@Entity(tableName = ChoreScoreDatabase.GROUP_TABLE)
public class Group {
  @PrimaryKey(autoGenerate = true)
  private int groupId2;

  private int groupId;
  private String name;

  /**
   * The group creation is not handled in an async way, so the
   * initialization races, causing duplicate "familyId"s. Manually
   * specify to work around. But this doesn't happen with "user"s
   * so not sure.
   */
  public Group(int groupId, String name) {
    this.groupId = groupId;
    this.name = name;
  }

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
    return groupId == group.groupId && groupId2 == group.groupId2 && Objects.equals(name, group.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, groupId2, name);
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

  public int getGroupId2() {
    return groupId2;
  }

  public void setGroupId2(int groupId2) {
    this.groupId2 = groupId2;
  }
}
