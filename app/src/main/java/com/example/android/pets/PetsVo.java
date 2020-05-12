package com.example.android.pets;

import java.io.Serializable;

public class PetsVo implements Serializable {
    public int id;
    public String name;
    public String raza;
    public int genero;
    public int peso;

    public PetsVo(int id, String name, String raza, int genero, int peso) {
        this.id = id;
        this.name = name;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
    }

    public PetsVo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
