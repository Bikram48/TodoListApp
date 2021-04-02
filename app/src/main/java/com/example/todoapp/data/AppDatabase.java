package com.example.todoapp.data;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoapp.Models.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public  abstract class AppDatabase extends RoomDatabase {

    public  static AppDatabase INSTANCE;

    public abstract TodoDao todoDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "todo.db")
                            //.allowMainThreadQueries()
                            .addCallback(CALLBACK)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback CALLBACK = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    TodoDao dao = INSTANCE.todoDao();
                    dao.deleteAll();

                }
            });

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
