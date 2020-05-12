package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

public class ModifyActivity extends AppCompatActivity {

    EditText et;

    long member_id;

    private PetDbHelper mDbHelper;

    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;
    private int Genero = 0;
    EditText campoId,campoNombre,campoRaza,campoPeso,campoGenero;

    private static final String TAG="SomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //Log.d(TAG,"onCreate:called.");
        setContentView(R.layout.activity_modify);
        campoId = (EditText) findViewById(R.id.id_pet);
        campoNombre = (EditText) findViewById(R.id.edit_pet_name);
        campoRaza = (EditText) findViewById(R.id.edit_pet_breed);
        campoPeso = (EditText) findViewById(R.id.edit_pet_weight);

        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();


        Bundle objetoEnviado = getIntent().getExtras();
        PetsVo pet = null;

        if(objetoEnviado!=null){
            pet=(PetsVo) objetoEnviado.getSerializable("pet");
            member_id = pet.getId();
            campoId.setText(String.valueOf(pet.getId()));
            campoNombre.setText(pet.getName());
            campoRaza.setText(pet.getRaza());
            campoPeso.setText(String.valueOf(pet.getPeso()));
            mGenderSpinner.setSelection(pet.getGenero());
            //Genero = pet.getGenero();
        }

        mDbHelper = new PetDbHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_update:
                ActualizarPet();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                EliminarPet();
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetContract.PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetContract.PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetContract.PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    private void EliminarPet(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};
        db.delete(PetContract.PetEntry.TABLE_NAME, PetContract.PetEntry._ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se elimino!",Toast.LENGTH_LONG).show();
        db.close();
    }
    private void ActualizarPet(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME,campoNombre.getText().toString());
        values.put(PetContract.PetEntry.COLUMN_PET_BREED,campoRaza.getText().toString());
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER,mGender);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT,Integer.parseInt(campoPeso.getText().toString()));
        db.update(PetContract.PetEntry.TABLE_NAME,values, PetContract.PetEntry._ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizo!",Toast.LENGTH_LONG).show();
        db.close();
    }

}
