package com.example.android.pets.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {PetEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "shelter.db";

    private static AppDatabase dbInstance;

    public abstract PetDao petDao();

    public static AppDatabase getDbInstance(final Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .addMigrations()
                    /*.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            AppDatabase database = AppDatabase.getDbInstance(context);
                            // Insert initial db values
                        }
                    })*/
                    .build();
        }
        return dbInstance;
    }

    public static void destroyInstance() {
        dbInstance = null;
    }
}
