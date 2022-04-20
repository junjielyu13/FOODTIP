package com.example.foodtip.Model;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class User {
    private final String id;
    private String name;
    private String password;
    private String user_name;
    private Bitmap avatar; //not sure
    private MisReceptas recptas;
    private MisHistoria historias;
    private MisFavoritos favoritos;

    public User(String id, String name, String password, String user_name, Bitmap avatar) {
        //UUID uuid = UUID.randomUUID();
        //this.id = uuid.toString();
        this.id = id;
        this.name = name;
        this.password = password;
        this.user_name = user_name;
        this.avatar = avatar;
    }

    //Setter


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void setRecptas(MisReceptas recptas) {
        this.recptas = recptas;
    }

    public void setHistorias(MisHistoria historias) {
        this.historias = historias;
    }

    public void setFavoritos(MisFavoritos favoritos) {
        this.favoritos = favoritos;
    }

    //Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public MisReceptas getRecptas() {
        return recptas;
    }

    public MisHistoria getHistorias() {
        return historias;
    }

    public MisFavoritos getFavoritos() {
        return favoritos;
    }

    public byte[] BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return b;
    }
}
