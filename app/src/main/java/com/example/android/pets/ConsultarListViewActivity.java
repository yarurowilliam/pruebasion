package com.example.android.pets;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.icu.lang.UProperty;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class ConsultarListViewActivity extends AppCompatActivity  implements Serializable {

    public static class Pet implements Serializable {
        public int id;
        public String name;
        public String raza;
        public int genero;
        public int peso;

        public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
        public void setRaza(String raza){
            this.raza = raza;
        }

        public String getRaza(){
            return raza;
        }
        public void setGenero(int genero){
            this.genero = genero;
        }

        public int getGenero(){
            return genero;
        }
        public void setPeso(int peso){
            this.peso = peso;
        }

        public int getPeso(){
            return peso;
        }



    }
}
