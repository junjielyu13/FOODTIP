package com.example.foodtip.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class DadaBaseAdpter {
    private static DadaBaseAdpter baseAdpter;

    private final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private DadaBaseAdpter() {}

    public static DadaBaseAdpter getInstance(){
        if(baseAdpter == null){
            DadaBaseAdpter baseAdpter = new DadaBaseAdpter();
        }
        return baseAdpter;
    }

    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
