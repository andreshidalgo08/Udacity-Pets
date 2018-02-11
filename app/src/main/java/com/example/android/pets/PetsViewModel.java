package com.example.android.pets;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.pets.data.AppDatabase;
import com.example.android.pets.data.PetEntity;

import java.util.List;


public class PetsViewModel extends AndroidViewModel {

    private String TAG = PetsViewModel.class.getSimpleName();
    public final LiveData<List<PetEntity>> pets;
    private AppDatabase db;

    public PetsViewModel(@NonNull Application application) {
        super(application);

        db = AppDatabase.getDbInstance(this.getApplication());

        pets = db.petDao().loadAllPets();
    }

    public void insertDummyPet() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.petDao().insertPet(new PetEntity("pet", "breed", "gender", 100));
                return null;
            }
        }.execute();
    };

    public void insertPet(PetEntity pet) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.petDao().insertPet(pet);
                return null;
            }
        }.execute();
    };
}
