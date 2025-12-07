package com.mareyn.group06project02.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mareyn.group06project02.ChoreLogger;
import com.mareyn.group06project02.database.entities.Chore;
import com.mareyn.group06project02.database.entities.Group;
import com.mareyn.group06project02.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Chore.class, User.class, Group.class}, version = 4, exportSchema = false)
public abstract class ChoreScoreDatabase extends RoomDatabase {
  public static final String DATABASE_NAME = "choreScoreDatabase";
  public static final String CHORE_TABLE = "chore";
  public static final String GROUP_TABLE = "groups";
  public static final String USER_TABLE = "user";

  private static volatile ChoreScoreDatabase INSTANCE;
  private static final int NUMBER_OF_THREADS = 4;
  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  public abstract ChoreDAO choreDAO();

  public abstract GroupDAO groupDAO();

  public abstract UserDAO userDAO();

  public static ChoreScoreDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (ChoreScoreDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
              context.getApplicationContext(),
              ChoreScoreDatabase.class,
              DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .addCallback(addDefaultValues)
            .build();
        }
      }
    }
    return INSTANCE;
  }

  private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      Log.i(ChoreLogger.ID, "DATABASE CREATED");
      databaseWriteExecutor.execute(() -> {
        // Code for this is in "LoginActivity.java".
      });
    }
  };
}
