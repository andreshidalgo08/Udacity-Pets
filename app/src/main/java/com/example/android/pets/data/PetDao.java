package com.example.android.pets.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PetDao {
    @Query("SELECT * FROM pets")
    List<PetEntity> loadAllPets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPet(PetEntity pet);
}
