package com.example.android.pets.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PetDao {
    @Query("select * from pets")
    LiveData<List<PetEntity>> loadAllPets();

    @Query("select * from pets where id == :id")
    PetEntity getPetById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPet(PetEntity pet);

    @Update
    void updatePet(PetEntity pet);

    @Delete
    void deletePet(PetEntity pet);

    @Query("delete from pets")
    void deleteAllPets();
}
