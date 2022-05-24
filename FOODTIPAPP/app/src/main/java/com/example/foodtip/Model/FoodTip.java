package com.example.foodtip.Model;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
        else if(user.getId() != FirebaseAuth.getInstance().getUid().toString()){
            getCurrentUser();
        }
        return user;
    }

    /**
     *
     */
    private void getCurrentUser(){
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(user_id);

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String avatar_uri = task.getResult().get("avatar").toString();
                        user = new UserBuilder()
                                .uid(user_id)
                                .user_name((String) task.getResult().get("user_name"))
                                .acc_name((String) task.getResult().get("acc_name"))
                                .password((String) task.getResult().get("password"))
                                .avatar_uri(avatar_uri)
                                .buildUser();
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
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("user");
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

                    StorageReference storageReference = FirebaseStorage.getInstance()
                            .getReference("avatar")
                            .child(user.getId());
                    storageReference.putBytes(foodTip.BitMapToString(user.getAvatar()))
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Map<String,Object> map = new HashMap<>();
                                            map.put("id",user.getId());
                                            map.put("acc_name",user.getName());
                                            map.put("password",user.getPassword());
                                            map.put("user_name",user.getUser_name());
                                            map.put("avatar",uri.toString());
                                            collectionReference.document(uid).set(map);
                                        }
                                    });
                                }
                            });
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
    public String GuardarRecepta(Recepta recepta, Map<String,Object> map){
        DocumentReference docuRef = FirebaseFirestore.getInstance()
                .collection("recepta")
                .document(recepta.getId());

        docuRef.set(map);

        return docuRef.getPath();
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

    public void UpdatePictures(Activity activity, Recepta recepta, String documentRef){
        DocumentReference docuRef = FirebaseFirestore.getInstance().document(documentRef);

        for(SliderData sliderData: recepta.getImages()){
            ImageDecoder.Source source = ImageDecoder.createSource(activity.getContentResolver(),Uri.parse(sliderData.getImgUri()));
            try{
                String pictureID = UUID.randomUUID().toString();
                StorageReference storageRef = this.getIngredientStorageReference(recepta).child("images")
                        .child(pictureID);
                storageRef.putBytes(this.BitMapToString(ImageDecoder.decodeBitmap(source)))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        docuRef.update("bitmaps", FieldValue.arrayUnion(uri.toString()));
                                    }
                                });
                            }
                        });
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void UpdateSteps(Recepta recepta, String documentRef){
        DocumentReference documentReference = FirebaseFirestore.getInstance().document(documentRef);
        for(Step step:recepta.getSteps()){
            if(step.getImages() != null){
                String picture_id = UUID.randomUUID().toString();
                StorageReference storageRef = this.getIngredientStorageReference(recepta).child("images")
                        .child(picture_id);
                storageRef.putBytes(this.BitMapToString(step.getImages())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Map<String,String> step_map = new HashMap<>();
                                step_map.put("title",step.getTitle());
                                step_map.put("text",step.getText());
                                step_map.put("uri",uri.toString());
                                documentReference.update("steps",FieldValue.arrayUnion(step_map));
                            }
                        });
                    }
                });

            }else{
                Map<String,String> step_map = new HashMap<>();
                step_map.put("title",step.getTitle());
                step_map.put("text",step.getText());
                step_map.put("uri",null);
                documentReference.update("steps",FieldValue.arrayUnion(step_map));
            }
        }
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
                                    Recepta recepta = new ReceptaBuilder()
                                            .id(document.getId())
                                            .description((String)task.getResult().get("description"))
                                            .title((String)task.getResult().getString("title"))
                                            .ingredients(foodTip.StringArray_To_IngredientArray((ArrayList<String>) task.getResult().get("ingredient")))
                                            .steps(foodTip.MapsArray_To_StepsArray((ArrayList<HashMap<String, Object>>) task.getResult().get("steps")))
                                            .images(foodTip.StringArray_To_SliderDataArray((ArrayList<String>) task.getResult().get("bitmaps")))
                                            .buildRecepta();
                                    viewModel.add_recepta(recepta);
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

    private ArrayList<Ingredient> StringArray_To_IngredientArray(ArrayList<String> input){
        ArrayList<Ingredient> output = new ArrayList<>();
        for(String str:input){
            output.add(new Ingredient(str));
        }
        return output;
    }

    private ArrayList<Step> MapsArray_To_StepsArray(ArrayList<HashMap<String,Object>> input) {
        ArrayList<Step> output = new ArrayList<>();
        for (Map<String,Object> map:input){
            Bitmap bitmap = null;
            try{
                bitmap = BitmapFactory.decodeStream((InputStream) new URL((String) map.get("uri")).getContent());
            } catch (Exception e){
            }
            Step step = new Step((String) map.get("title"),(String) map.get("text"), bitmap);
            step.setUrl((String) map.get("uri"));
            output.add(step);
        }
        return output;
    }
}
