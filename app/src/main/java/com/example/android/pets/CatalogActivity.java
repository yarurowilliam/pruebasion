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

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements PetsAdaptador.OnNoteListener {

    private PetDbHelper mDbHelper;
    ArrayList<PetsVo> listaPets;
    RecyclerView recyclerPets;
    PetsAdaptador adapter;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        consultarListaMascotas();
       ///displayDatabaseInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        //listaPets = new ArrayList<>();



        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new PetDbHelper(this);
        recyclerPets = (RecyclerView) findViewById(R.id.recyclerPets);
        recyclerPets.setLayoutManager(new LinearLayoutManager(this));
        consultarListaMascotas();
        /*
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Seleccion: "+listaPets.get(recyclerPets.getChildAdapterPosition(view)).getName(),Toast.LENGTH_SHORT).show();

                PetsVo pet = listaPets.get(recyclerPets.getChildAdapterPosition(view));
                Intent intent = new Intent(CatalogActivity.this,ModifyActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("pet",pet);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        */
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
                insertPet();
                consultarListaMascotas();
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                removeAll();
                consultarListaMascotas();
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeAll(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(PetEntry.TABLE_NAME,"",null);
    }

    private void insertPet(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Fury");
        values.put(PetEntry.COLUMN_PET_BREED, "Schnauzer");
        values.put(PetEntry.COLUMN_PET_GENDER, 1);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 8);
        long ID = db.insert(PetEntry.TABLE_NAME, null, values);
        // ID == -1 -> Error, ID == 1 -> Success
    }

    private void consultarListaMascotas(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        listaPets = new ArrayList<>();
        PetsVo mascotasVo= null;
        Cursor cursor = db.rawQuery("select * From "+PetEntry.TABLE_NAME,null);
        while(cursor.moveToNext()){
            mascotasVo = new PetsVo();
            mascotasVo.setId(cursor.getInt(0));
            mascotasVo.setName(cursor.getString(2));
            mascotasVo.setRaza(cursor.getString(1));
            mascotasVo.setGenero(cursor.getInt(3));
            mascotasVo.setPeso(cursor.getInt(4));
            listaPets.add(mascotasVo);
        }
        adapter = new PetsAdaptador(listaPets,this);
        recyclerPets.setAdapter(adapter);
    }

    private static final String TAG="SomeActivity";
    @Override
    public void onNoteClick(int position) {
         Log.d(TAG, "onCreate: called."+position);

        /* Intent intent = new Intent(this,ModifyActivity.class);
         intent.putExtra("some_object","left");
         startActivity(intent);*/
        PetsVo pet = listaPets.get(position);
        Toast.makeText(getApplicationContext(),"UTE SELESIONO ETA BAINA MANITO "+pet.getId(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CatalogActivity.this,ModifyActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("pet",pet);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
