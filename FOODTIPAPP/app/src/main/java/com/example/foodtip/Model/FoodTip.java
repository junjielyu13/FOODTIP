package com.example.foodtip.Model;


import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.View.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class FoodTip {
    /**
     *  Crear un nou usuari
     * @param activity
     * @param name email
     * @param password contrasenya
     * @param user_name user name
     */
    public static void CreatNewUser(@NonNull AppCompatActivity activity, String name, String password, String user_name){
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
                    Toast.makeText(activity,"User already exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static void login_event(@NonNull MainActivity activity, String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((task)->{
           if(task.isSuccessful()){
               activity.openHomePage();
           }
           else{
               Toast.makeText(activity,"login fail",Toast.LENGTH_SHORT).show();
           }
        });
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
