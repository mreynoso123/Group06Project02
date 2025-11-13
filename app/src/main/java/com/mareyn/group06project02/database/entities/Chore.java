package com.mareyn.group06project02.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mareyn.group06project02.database.ChoreScoreDatabase;

import java.util.Objects;

@Entity(tableName = ChoreScoreDatabase.CHORE_TABLE)
public class Chore {
  @PrimaryKey(autoGenerate = true)
  private int choreId;
  private int userId;
  private String dueDate;
  private String title;
  private String description;
  private int points;

  // Complete = 1 and Active/Incomplete = 0
  private int status;

  @Override
  public String toString() {
    return "Chore{" +
      "choreId=" + choreId +
      ", userId=" + userId +
      ", dueDate=" + dueDate +
      ", title='" + title + '\'' +
      ", description='" + description + '\'' +
      ", points=" + points +
      ", status=" + status +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Chore chore = (Chore) o;
    return choreId == chore.choreId && userId == chore.userId && points == chore.points && status == chore.status && Objects.equals(dueDate, chore.dueDate) && Objects.equals(title, chore.title) && Objects.equals(description, chore.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(choreId, userId, dueDate, title, description, points, status);
  }

  public int getChoreId() {
    return choreId;
  }

  public void setChoreId(int choreId) {
    this.choreId = choreId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
