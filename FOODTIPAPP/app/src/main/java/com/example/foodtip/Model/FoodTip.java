package com.example.foodtip.Model;

import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.R;
import com.example.foodtip.View.Register_View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class FoodTip {
    /**
     * register 1 user
     * @return True if created successfully otherwise False
     */
    public static FirebaseUser CreatNewUser(@NonNull AppCompatActivity activity, String name, String password, String repeat_password, String user_name){
        if(!password.equals(repeat_password)) return null;
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    User user = new UserBuilder(activity)
                            .uid(uid)
                            .user_name(user_name)
                            .acc_name(name)
                            .password(password)
                            .buildUser();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("users").child(user.getId()).setValue(getDocument_User(user));
                }else{
                    Toast.makeText(activity,"Authentication failed.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    private static Map<String,Object> getDocument_User(User user){
        Map<String, Object> entrada = new HashMap<>();
        entrada.put("id",user.getId());
        entrada.put("acc_name",user.getName());
        entrada.put("password",user.getPassword());
        entrada.put("user_name",user.getUser_name());

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("avatar")
                .child(user.getId());
        storageReference.putBytes(user.BitMapToString(user.getAvatar()));
        entrada.put("avatar", storageReference.toString());
        return entrada;
    }
}
