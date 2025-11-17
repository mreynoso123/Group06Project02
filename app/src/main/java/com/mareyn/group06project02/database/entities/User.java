package com.mareyn.group06project02.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mareyn.group06project02.database.ChoreScoreDatabase;

import java.util.Objects;

@Entity(tableName = ChoreScoreDatabase.USER_TABLE)
public class User {
  @PrimaryKey(autoGenerate = true)
  private int userId;
  private int familyId;
  private String email;
  private String username;
  private String password;
  private boolean isAdmin;

  public User(int familyId, String username, String password, String email, boolean isAdmin) {
    this.familyId = familyId;
    this.username = username;
    this.password = password;
    this.isAdmin = isAdmin;
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
      "userId=" + userId +
      ", familyId=" + familyId +
      ", email='" + email + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", isAdmin=" + isAdmin +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userId == user.userId && familyId == user.familyId && isAdmin == user.isAdmin && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, familyId, email, username, password, isAdmin);
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getFamilyId() {
    return familyId;
  }

  public void setFamilyId(int familyId) {
    this.familyId = familyId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
