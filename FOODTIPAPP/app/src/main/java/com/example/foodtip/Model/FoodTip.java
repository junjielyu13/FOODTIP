package com.example.foodtip.Model;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.transition.Slide;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.View.MainActivity;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FoodTip {
    private static FoodTip foodTip;
    private User user;
    private final static String TAG = "FOODTIP";
    final static long ONE_MEGABYTE = 1024 * 1024;
    public static vmInterface listener;

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
    }

    /**
     *  Crear un nou usuari
     * @param activity
     * @param name email
     * @param password contrasenya
     * @param user_name user name
     */
    public void CreatNewUser(@NonNull AppCompatActivity activity, String name, String password, String user_name){
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

                    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    FirebaseFirestore.getInstance()
                            .collection("user")
                            .document(user.getId())
                            .set(getDocument_User(user));
                    //databaseReference.child("users").child(user.getId()).setValue(getDocument_User(user));
                    Toast.makeText(activity,"User Created",Toast.LENGTH_SHORT).show();
                    activity.finish();
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

    public Recepta createRecepta(String title, String description, ArrayList<SliderData> images, ArrayList<Ingredient> ingredients, ArrayList<Step> steps){
        return new ReceptaBuilder()
                .title(title)
                .description(description)
                .images(images)
                .ingredients(ingredients)
                .steps(steps)
                .buildRecepta();
    }

    public StorageReference getIngredientStorageReference(Recepta recepta){
        return FirebaseStorage.getInstance()
                .getReference("recepta")
                .child(recepta.getId());
    }
    public void GuardarRecepta(Recepta recepta, Map<String,Object> map){
        FirebaseFirestore.getInstance()
                .collection("recepta")
                .document(recepta.getId())
                .set(map);
    }
    public ArrayList<String> UpdateIngredients(Recepta recepta, String uid){
        //Update ingredient
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        //Guarda ID de ingredient
        ArrayList<String> ingredient_id = new ArrayList<>();

        for(Ingredient ingredient:recepta.getIngredients()){
            Map<String,Object> map = new HashMap<>();
            map.put(recepta.getId(),uid);
            DocumentReference documentReference = firestore.collection("ingredient")
                    .document(ingredient.getNom());

            documentReference.update(map).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    documentReference.set(map);
                }
            });
            ingredient_id.add(ingredient.getNom());
        }
        return ingredient_id;
    }
    public ArrayList<String> UpdatePictures(Activity activity, Recepta recepta, String uid){
        ArrayList<String> pictures = new ArrayList<>();
        for(SliderData sliderData:recepta.getImages()){
            ImageDecoder.Source source = ImageDecoder.createSource(activity.getContentResolver(), Uri.parse(sliderData.getImgUri()));
            try {
                String picture_id = UUID.randomUUID().toString();

                StorageReference storageRef = this.getIngredientStorageReference(recepta).child("images")
                        .child(picture_id);
                storageRef.putBytes(foodTip.BitMapToString(ImageDecoder.decodeBitmap(source)));

                pictures.add(storageRef.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pictures;
    }
    public ArrayList<Map<String,String>> UpdateSteps(Recepta recepta){
        ArrayList<Map<String,String>> steps = new ArrayList<>();
        for (Step step:recepta.getSteps()){
            Map<String,String> step_map = new HashMap<>();
            step_map.put("title",step.getTitle());
            step_map.put("text",step.getText());

            if(step.getImages() == null) {
                step_map.put("bitmapID",null);
            }else{
                String picture_id = UUID.randomUUID().toString();
                StorageReference storageRef = this.getIngredientStorageReference(recepta).child("images")
                        .child(picture_id);
                storageRef.putBytes(this.BitMapToString(step.getImages()));

                step_map.put("bitmapID",storageRef.toString());
            }
            steps.add(step_map);
        }
        return steps;
    }

    /**
     * .addOnSuccessListener(documentSnapshot -> {
     *                                 documentSnapshot.get("bitmaps")
     *                                 for(String str:(ArrayList<String>)documentSnapshot.get("bitmaps")){
     *                                     StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(str);
     *                                     storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
     *                                         @Override
     *                                         public void onSuccess(Uri uri) {
     *                                             viewModel.save_uri(uri);
     *                                         }
     *                                     });
     *                                 }
     *                                 System.out.println("creat");
     *                                 Recepta recepta = new ReceptaBuilder().id(document.getId())
     *                                         .description((String)documentSnapshot.get("description"))
     *                                         .title((String) documentSnapshot.get("title"))
     *                                         .images(viewModel.getSliderData().getValue())
     *                                         .buildRecepta();
     *                                 if(recepta.getImages() == null){
     *                                     System.out.println("error");
     *                                 }
     *                                 viewModel.add_recepta(recepta);
     * });
     * @param viewModel
     */
    public void getReceptaInformation(HomePageViewModel viewModel){
        CollectionReference db = FirebaseFirestore.getInstance().collection("recepta");
        db.get().addOnCompleteListener(task->{
            if (task.isSuccessful()){
                for(QueryDocumentSnapshot document : task.getResult()){
                    db.document(document.getId())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()){
                                        ArrayList<SliderData> images = new ArrayList<SliderData>();
                                        for (String str : (ArrayList<String>)task.getResult().get("bitmaps")){
                                            FirebaseStorage.getInstance().getReferenceFromUrl(str).getDownloadUrl().addOnCompleteListener(v ->{
                                                System.out.println(v.getResult());
                                            });
                                        }
                                        Recepta recepta = new ReceptaBuilder()
                                                .id(document.getId())
                                                .description((String)task.getResult().get("description"))
                                                .title((String)task.getResult().getString("title"))
                                                .buildRecepta();
                                        viewModel.add_recepta(recepta);
                                    }
                                    //.images(StringArray_To_SliderDataArray((ArrayList<String>) task.getResult().get("bitmaps")))

                                }
                            });
                }
            }
        });

    }
    private ArrayList<SliderData> StringArray_To_SliderDataArray(ArrayList<String> input){
        ArrayList<SliderData> output = new ArrayList<>();
        for(String str:input){
            output.add(new SliderData(str));
        }
        return output;
    }

}
