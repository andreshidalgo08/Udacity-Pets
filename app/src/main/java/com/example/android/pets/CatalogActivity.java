/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.AppDatabase;
import com.example.android.pets.data.PetEntity;

import java.util.List;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {
    private String TAG = CatalogActivity.class.getSimpleName();
    private AppDatabase db;
    private TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        displayView = (TextView) findViewById(R.id.text_view_pet);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        db = AppDatabase.getDbInstance(this);
        new GetAllPetsTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetAllPetsTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                new InsertPetTask().execute();
                new GetAllPetsTask().execute();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetAllPetsTask extends AsyncTask<Void, Void, List<PetEntity>> {
        @Override
        protected List<PetEntity> doInBackground(Void... v) {
            List<PetEntity> pets = db.getAllPets();
            Log.d(TAG, "Number of pets: " + pets.size());
            return pets;
        }

        @Override
        protected void onPostExecute(List<PetEntity> pets) {
            try {
                displayView.setText("The pets table contains: " + pets.size());

                for (int i = 0; i < pets.size(); i++) {
                    PetEntity pet = pets.get(i);
                    displayView.append(("\n" + pet.getId() + " - " +
                            pet.getName() + " - " +
                            pet.getBreed() + " - " +
                            pet.getGender() + " - " +
                            pet.getWeight()));
                }
            }
            finally {

            }
        }
    }

    private class InsertPetTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... v) {
            db.insertPet(new PetEntity("pet", "breed", "gender", 100));
            List<PetEntity> pets = db.getAllPets();
            Log.d(TAG, "Number of pets: " + pets.size());
            return 0;
        }
    }
}
