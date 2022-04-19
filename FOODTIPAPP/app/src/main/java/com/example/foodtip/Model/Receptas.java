package com.example.foodtip.Model;

import java.util.Vector;

public class Receptas {
    private Vector<Recepta> receptas;

    public Receptas(Vector<Recepta> receptas){
        this.receptas = receptas;
    }
    public Vector<Recepta> getReceptas() {
        return receptas;
    }

    public void setReceptas(Vector<Recepta> receptas) {
        this.receptas = receptas;
    }
}
