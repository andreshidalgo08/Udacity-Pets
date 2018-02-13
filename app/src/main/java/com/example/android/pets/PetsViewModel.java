package com.example.android.pets;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.pets.data.AppDatabase;
import com.example.android.pets.data.PetEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


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
        new Thread() {
            public void run() {
                db.petDao().insertPet(new PetEntity("pet", "breed", "gender", 100));
            }
        }.start();
    }

    public void insertPet(PetEntity pet) {
        new Thread() {
            public void run() {
                db.petDao().insertPet(pet);
            }
        }.start();
    }

    public void updatePet(PetEntity pet) {
        new Thread() {
            public void run() {
                db.petDao().updatePet(pet);
            }
        }.start();
    }

    public PetEntity getPetById(long id) {
        PetEntity editPet = null;
        try {
            editPet = new getPetByIdAsyncTask(db).execute(id).get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        } finally {
            return editPet;
        }
    }

    public void deletePet(PetEntity pet) {
        new Thread() {
            public void run() {
                db.petDao().deletePet(pet);
            }
        }.start();
    }

    public void deleteAllPets() {
        new Thread() {
            public void run() {
                db.petDao().deleteAllPets();
            }
        }.start();
    }

    private static class getPetByIdAsyncTask extends AsyncTask<Long, Void, PetEntity> {
        private AppDatabase db;

        public getPetByIdAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected PetEntity doInBackground(Long... id) {
            return db.petDao().getPetById(id[0]);
        }

        @Override
        protected void onPostExecute(PetEntity petEntity) {
            super.onPostExecute(petEntity);
        }
    }
}
