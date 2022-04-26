package com.example.foodtip.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.View.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FoodTip {
    private static FoodTip foodTip;
    private User user;
    private final static String TAG = "FOODTIP";
    final static long ONE_MEGABYTE = 1024 * 1024;


    public static FoodTip getInstance(){
        if(foodTip == null){
            foodTip = new FoodTip();
        }
        return foodTip;
    }

    public User getUser() {
        if(user == null){
            getCurrentUser();
        }
        return user;
    }

    /**
     *
     */
    public void getCurrentUser(){
        final Bitmap[] bitmap = new Bitmap[1];
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("user")
                .document(FirebaseAuth.getInstance().getUid());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("avatar");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        //Get Bitmap from reference
                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(document.get("avatar").toString());
                        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                bitmap[0] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        //Create user
                        user = new UserBuilder()
                                .acc_name(document.get("acc_name").toString())
                                .user_name(document.get("user_name").toString())
                                .password(document.get("password").toString())
                                .uid(document.get("id").toString())
                                .bitmap(bitmap[0])
                                .buildUser();
                    }else{
                        Log.d(TAG,"No such document");
                    }
                }else{
                    Log.d(TAG,"Get failed with", task.getException());
                }
            }
        });
        if (user == null){
            System.out.println("1111111111111111111111111111");
        }
    }

    /**
     *  Crear un nou usuari
     * @param activity
     * @param username email to log in
     * @param password contrasenya
     * @param nickname name of user after log in
     */
    public void CreatNewUser(@NonNull AppCompatActivity activity, String username, String password, String nickname){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    User user = new UserBuilder(activity)
                            .uid(uid)
                            .user_name(nickname)
                            .acc_name(username)
                            .password(password)
                            .buildUser();

                    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    FirebaseFirestore.getInstance().collection("user").document(user.getId()).set(getDocument_User(user));
                    //databaseReference.child("users").child(user.getId()).setValue(getDocument_User(user));
                }else{
                    Toast.makeText(activity,"User already exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login_event(@NonNull MainActivity activity, String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((task)->{
           if(task.isSuccessful()){
               activity.openHomePage();
               Toast.makeText(activity,"login successful",Toast.LENGTH_SHORT).show();
           }
           else{
               Toast.makeText(activity,"login fail",Toast.LENGTH_SHORT).show();
           }
        });
    }
    private Map<String,Object> getDocument_User(User user){
        Map<String, Object> entrada = new HashMap<>();
        entrada.put("id",user.getId());
        entrada.put("acc_name",user.getName());
        entrada.put("password",user.getPassword());
        entrada.put("user_name",user.getUser_name());

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("avatar")
                .child(user.getId());
        //storageReference.putBytes(user.BitMapToString(user.getAvatar()));
        storageReference.putBytes(this.BitMapToString(user.getAvatar()));
        entrada.put("avatar", storageReference.toString());
        return entrada;
    }
    public byte[] BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return b;
    }
}
