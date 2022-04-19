package com.example.foodtip.Model;

import android.media.Image;

import java.util.UUID;


public class User {
    private final String id;
    private String name;
    private String password;
    private String user_name;
    private Image avatar; //not sure
    private MisReceptas receptas;
    private MisHistoria historias;
    private MisFavoritos favoritos;

    public User(String name, String password, String user_name, Image avatar, MisReceptas receptas, MisHistoria historias, MisFavoritos favoritos) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.name = name;
        this.password = password;
        this.user_name = user_name;
        this.avatar = avatar;
        this.receptas = receptas;
        this.historias = historias;
        this.favoritos = favoritos;
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

    public void setAvatar(Image avatar) {
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

    public Image getAvatar() {
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
}
